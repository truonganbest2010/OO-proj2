package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import model.observerPattern.Observer;
import model.observerPattern.Subject;
import model.bulletStrategyPattern.BulletHitEnemyStrategy;
import model.bulletStrategyPattern.BulletRenderHitEnemyStrategy;
import model.gameStatePattern.GameState;
import model.gameStatePattern.GameWave1;
import model.images.ImageStore;
import view.GameBoard;

public class EnemyComposite extends GameElement implements Subject {

    public static final int NROWS = 5;
    public static final int NCOLS = 12;
    public static final int ENEMY_SIZE = 25;
    public static final int UNIT_MOVE = 2;

    public enum EVENT {
        GOT_SHOT, REACH_BOTTOM, ALL_E_DESTROYED, ALL_C_DESTROYED
    }

    /** proj2 implementation */
    public static final int UNIT_MOVE_DOWN = 20;

    private ArrayList<Observer> observers = new ArrayList<>();

    private ArrayList<ArrayList<GameElement>> rows;
    private ArrayList<GameElement> bombs;

    public boolean movingToRight;
    private Random random = new Random();

    private int enemiesKilled = 0;
    private GameState state;

    public EnemyComposite() {
        rows = new ArrayList<>();
        bombs = new ArrayList<>();

        state = new GameWave1(this);
    }

    public void enemyFormation(int health) {
        /** Enemies Formation */
        for (int r = 0; r < NROWS; r++) {
            var oneRow = new ArrayList<GameElement>();
            rows.add(oneRow);
            for (int c = 0; c < NCOLS; c++) {
                Enemy enemy = new Enemy(c * ENEMY_SIZE * 2, r * ENEMY_SIZE * 2 +20, ENEMY_SIZE, ENEMY_SIZE, health);
                if (r%2 == 0) {
                    if (c%2 == 0) {
                        enemy.setImage(ImageStore.enemy_white);
                    } else enemy.setImage(ImageStore.enemy_yellow);
                } else {
                    if (c%2 != 0) {
                        enemy.setImage(ImageStore.enemy_white);
                    } else enemy.setImage(ImageStore.enemy_yellow);
                }
                oneRow.add(enemy);
            }
        }
    }

    public void goNextState() {
        state.nextState(this);
    }
    public void setState(GameState state) {
        this.state = state;
    }


    @Override
    public void render(Graphics2D g2) {

        // render enemy array
        for (var r : rows) {
            for (var e : r) {
                e.render(g2);
            }
        }

        // render enemy bombs
        for (var b : bombs) {
            b.render(g2);
        }


        g2.setColor(Color.white);
        g2.setFont(new Font("Courier", Font.BOLD, 10));
        g2.drawImage(ImageStore.target_icon, null, 5, 10);
        g2.drawString("" + enemiesKilled, 25, 20);
    }

    @Override
    public void animate() {
        int dx = UNIT_MOVE;
        if (movingToRight) {
            if (rightEnd() >= GameBoard.WIDTH) {
                dx -= dx;
                movingToRight = false;
                // move down dy unit when hitting rightEnd
                moveDown();
            }
        } else {
            dx = -dx;
            if (leftEnd() <= 0) {
                dx = -dx;
                movingToRight = true;
                // move down dy unit when hitting leftEnd
                moveDown();
            }
        }
        // update enemy x loc
        for (var row : rows) {
            for (var e : row) {
                e.x += dx;
            }
        }
        // animate bombs
        for (var b : bombs) {
            b.animate();
        }

    }


    private int rightEnd() {
        int xEnd = -100;
        for (var row : rows) {
            if (row.size() == 0)
                continue;
            int x = row.get(row.size() - 1).x + ENEMY_SIZE;
            if (x > xEnd)
                xEnd = x;
        }
        return xEnd;
    }

    private int leftEnd() {
        int xEnd = 9000;
        for (var row: rows) {
            if (row.size() == 0)
                continue;
            int x = row.get(0).x;
            if (x < xEnd)
                xEnd = x;
        }
        return xEnd;
    }

    private void moveDown() {
        int dy = UNIT_MOVE_DOWN;
        for (var row: rows) {
            for (var e: row) {
                e.y += dy;
            }
        }
    }

    public void dropBombs() {
        for (var row : rows) {
            for (var e : row) {
                if (random.nextFloat() < 0.1F) {
                    bombs.add(new Bomb(e.x, e.y));
                }
            }
        }
    }

    public void removeOutOfLowerBound() {
        var remove = new ArrayList<GameElement>();
        for (var b : bombs) {
            if (b.y >= GameBoard.HEIGHT) {
                remove.add(b);
            }
        }
        bombs.removeAll(remove);
    }


    public void processCollisionWithEnemy(Shooter shooter) {
        // bullet vs enemies
        var removeRow = new ArrayList<>();
        for (var row: rows) {
            var removeEnemies = new ArrayList<GameElement>();
            for (var enemy: row) {
                for (var bullet : shooter.getWeapons()) {
                    if (enemy.collideWith(bullet) && removeEnemies.size() < 1) {
                        removeEnemies.add(enemy);
                        enemiesKilled++;
                        notifyObservers(EVENT.GOT_SHOT);
                        
                        /** Enemy explodes */ 
                        BulletHitEnemyStrategy bhes = new BulletHitEnemyStrategy(bullet);
                        bhes.setMoveRight(movingToRight);
                        bullet.setMoveStrategy(bhes);
                        bullet.setRenderStrategy(new BulletRenderHitEnemyStrategy(bullet));
                    }
                }

                for (var laser: shooter.getLightningGun()) {
                    if (enemy.collideWith(laser)) {
                        removeEnemies.add(enemy);
                        enemiesKilled+= removeEnemies.size();
                        notifyObservers(EVENT.GOT_SHOT);
                    }
                }

                // check if enemies reach bottom line
                if (enemy.collideWith(shooter.getBottomLine().get(0))) {
                    notifyObservers(EVENT.REACH_BOTTOM);
                }
            }
            row.removeAll(removeEnemies);
            if (row.size() == 0) {
                removeRow.add(row);
            }
        }
        rows.removeAll(removeRow);

        System.out.println(rows.size());
        if (rows.size() == 0) {  
            if (state != null) {
                goNextState();
            } else notifyObservers(EVENT.ALL_E_DESTROYED);
        }

        // bullet vs bombs
        var removeBombs = new ArrayList<GameElement>();
        for (var b : bombs) {
            for (var bullet : shooter.getWeapons()) {
                if (b.collideWith(bullet)) {
                    removeBombs.add(b);
                    /** Bomb explodes */
                    BulletHitEnemyStrategy bhes = new BulletHitEnemyStrategy(bullet);
                    bhes.setMoveRight(true);
                    bullet.setMoveStrategy(bhes);
                    bullet.setRenderStrategy(new BulletRenderHitEnemyStrategy(bullet));
                }
            }
        }
        bombs.removeAll(removeBombs);

        // bombs vs components
        var removeComponents = new ArrayList<GameElement>();
        for (var component : shooter.getComponents()) {
            for (var b : bombs) {
                if (component.collideWith(b) && removeComponents.size() < 1) {
                        removeComponents.add(component);
                        removeBombs.add(b);
                }
            }
        }
        shooter.getComponents().removeAll(removeComponents);
        bombs.removeAll(removeBombs);
        if (shooter.getComponents().size() == 0) {
            notifyObservers(EVENT.ALL_C_DESTROYED);
        }

        // enemies vs components
        for (var row : rows) {
            for (var e : row) {
                for (var component : shooter.getComponents()) {
                    if (e.collideWith(component)) {
                        removeComponents.add(component);
                    }
                }
            }
        }
        shooter.getComponents().removeAll(removeComponents);

    }

    @Override
    public void addEnemyListener(Observer o) {
        // TODO Auto-generated method stub
        observers.add(o);
    }

    @Override
    public void removeEnemyListener(Observer o) {
        // TODO Auto-generated method stub
        observers.remove(o);
    }

    @Override
    public void notifyObservers(EVENT event) {
        // TODO Auto-generated method stub
        switch (event) {
            case GOT_SHOT:
                for (var o: observers) {
                    o.enemiesGotShot();
                }
                break;
            case REACH_BOTTOM:
                for (var o: observers) {
                    o.enemiesReachBottom();
                }
                break;
            case ALL_E_DESTROYED:
                for (var o: observers) {
                    o.enemiesAllGone();
                }
                break;
            case ALL_C_DESTROYED:
                for (var o: observers) {
                    o.enemiesDestroyedComponents();
                }
        }


    }

    
}