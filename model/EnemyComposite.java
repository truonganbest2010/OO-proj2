package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import model.enemyCompositeObserverPattern.Observer;
import model.enemyCompositeObserverPattern.Subject;
import model.bombStrategyPattern.BombHitShooterStrategy;
import model.bombStrategyPattern.BombRenderHitShooterStrategy;
import model.bulletStrategyPattern.BulletHitEnemyStrategy;
import model.bulletStrategyPattern.BulletRenderHitEnemyStrategy;
import model.enemyCompositeStatePattern.EnemiesWave1;
import model.enemyCompositeStatePattern.EnemyCompositeState;
import model.images.ImageStore;
import view.GameBoard;

public class EnemyComposite extends GameElement implements Subject {

    public static final int ENEMY_SIZE = 25;
    public static final int UNIT_MOVE = 2;

    public enum EVENT {
        GOT_SHOT, REACH_BOTTOM, GOT_ALL_DESTROYED, DESTROYED_SHOOTER
    }

    /** proj2 implementation */
    public static final int UNIT_MOVE_DOWN = 20;

    private ArrayList<Observer> observers = new ArrayList<>();

    private ArrayList<ArrayList<Enemy>> rows;
    private ArrayList<Bomb> bombs;
    private ArrayList<GameElement> text;

    public boolean movingToRight;
    private Random random = new Random();

    private int enemiesKilled = 0;
    private EnemyCompositeState state;

    public EnemyComposite() {
        rows = new ArrayList<>();
        bombs = new ArrayList<>();
        text = new ArrayList<>();

        state = new EnemiesWave1(this);
    }

    public void enemyFormation(int health, int nrows, int ncols) {
        /** Enemies Formation */
        for (int r = 0; r < nrows; r++) {
            var oneRow = new ArrayList<Enemy>();
            rows.add(oneRow);
            for (int c = 0; c < ncols; c++) {
                Enemy enemy = new Enemy(c * ENEMY_SIZE * 2, r * ENEMY_SIZE * 2 -1200, ENEMY_SIZE, ENEMY_SIZE, health);
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

    public void toNextWave() {
        state.nextWave(this);
    }
    public void setState(EnemyCompositeState state) {
        this.state = state;
    }

    public ArrayList<GameElement> getText() {
        return text;
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

        // render Text
        for (var t: text) {
            t.render(g2);
        }


        g2.setColor(Color.white);
        g2.setFont(new Font("Courier", Font.BOLD, 10));
        g2.drawImage(ImageStore.target_icon, null, 5, 10);
        g2.drawString("" + enemiesKilled, 25, 20);
    }

    @Override
    public void animate() {
        int dx = UNIT_MOVE;

        if (state != null) {
            if (rows.get(0).get(0).y > 0) {
                    
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
            }
            else {
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

        // animate text
            for (var t: text) {
                t.animate();
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

        remove = new ArrayList<GameElement>();
        for (var t : text) {
            if (t.y >= GameBoard.HEIGHT+100) {
                remove.add(t);
            }
        }
        text.removeAll(remove);


    }


    public void processCollisionWithEnemy(Shooter shooter) {
        // bullet vs enemies
        var removeRow = new ArrayList<>();
        var removeBullet = new ArrayList<>();
        for (var row: rows) {
            var removeEnemies = new ArrayList<GameElement>();
            for (var enemy: row) {
                for (var bullet: shooter.getWeapons()) {
                    if (enemy.collideWith(bullet) && removeEnemies.size() < 1) {
                        if (enemy.getHealth() > 1) {
                            enemy.setHealth(enemy.getHealth()-1);
                            removeBullet.add(bullet);
                        }
                        else {
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
                }

                for (var laser: shooter.getLightningGun()) {
                    if (enemy.collideWith(laser)) {
                        removeEnemies.add(enemy);
                        enemiesKilled+= removeEnemies.size();
                        notifyObservers(EVENT.GOT_SHOT);
                    }
                }

                // check if enemies reach bottom line
                if (enemy.collideWith(shooter.getBottomLine())) {
                    notifyObservers(EVENT.REACH_BOTTOM);
                }
            }
            shooter.getWeapons().removeAll(removeBullet);

            row.removeAll(removeEnemies);
            if (row.size() == 0) {
                removeRow.add(row);
            }
        }
        rows.removeAll(removeRow);

        // System.out.println(rows.size());
        if (rows.size() == 0) {  
            if (state != null) {
                toNextWave();
            } else notifyObservers(EVENT.GOT_ALL_DESTROYED);
        }

        // bullet vs bombs
        var removeBombs = new ArrayList<GameElement>();
        for (var b : bombs) {
            for (var bullet : shooter.getWeapons()) {
                if (b.collideWith(bullet) && removeBombs.size() < 1) {
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
                    shooter.setBulletShoot(shooter.getBulletShoot()-1);
                    b.setMoveStrategy(new BombHitShooterStrategy(b));
                    b.setRenderStrategy(new BombRenderHitShooterStrategy(b));
                }
            }
        }
        shooter.getComponents().removeAll(removeComponents);
        removeBombs.clear();

        if (shooter.getComponents().size() == 0) {
            notifyObservers(EVENT.DESTROYED_SHOOTER);
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
            case GOT_ALL_DESTROYED:
                for (var o: observers) {
                    o.enemiesAllGone();
                }
                break;
            case DESTROYED_SHOOTER:
                for (var o: observers) {
                    o.enemiesDestroyedShooter();
                }
        }


    }

    
}