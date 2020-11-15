package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import controller.TimerListener;
import model.observerPattern.Observer;
import model.observerPattern.Subject;
import view.GameBoard;

public class EnemyComposite extends GameElement implements Subject {

    public static final int NROWS = 4;
    public static final int NCOLS = 15;
    public static final int ENEMY_SIZE = 20;
    public static final int UNIT_MOVE = 3;

    public enum EVENT {
        HIT_BULLET, REACH_BOTTOM, ALL_E_DESTROYED, ALL_C_DESTROYED
    }

    /** proj2 implementation */
    public static final int UNIT_MOVE_DOWN = 20;

    private ArrayList<Observer> observers = new ArrayList<>();

    private ArrayList<ArrayList<GameElement>> rows;
    private ArrayList<GameElement> bombs;
    private boolean movingToRight = true;
    private Random random = new Random();

    private int totalEnemies = NROWS * NCOLS;
    public int enemiesAlive = 0;
    public int enemiesKilled = 0;

    public int eKilled;

    public EnemyComposite() {
        rows = new ArrayList<>();
        bombs = new ArrayList<>();

        for (int r = 0; r < NROWS; r++) {
            var oneRow = new ArrayList<GameElement>();
            rows.add(oneRow);
            for (int c = 0; c < NCOLS; c++) {
                oneRow.add(new Enemy(c * ENEMY_SIZE * 2, r * ENEMY_SIZE * 2, ENEMY_SIZE, Color.yellow, true));
            }
        }
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
        g2.setFont(new Font("Courier", Font.BOLD, 30));
        g2.drawString("" + enemiesKilled * TimerListener.SCORE_UP, 20, y + 40);

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
        // update x loc
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
        for (var row : rows) {
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
        for (var row : rows) {
            for (var e : row) {
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

    public void removeBombsOutOfBound() {
        var remove = new ArrayList<GameElement>();
        for (var b : bombs) {
            if (b.y >= GameBoard.HEIGHT) {
                remove.add(b);
            }
        }
        bombs.removeAll(remove);
    }


    public void processCollision(Shooter shooter) {
        // bullet vs enemies
        var removeBullets = new ArrayList<GameElement>();
        for (var row: rows) {
            var removeEnemies = new ArrayList<GameElement>();
            for (var enemy: row) {
                for (var bullet : shooter.getWeapons()) {
                    if (enemy.collideWith(bullet)) {
                        removeBullets.add(bullet);
                        removeEnemies.add(enemy);
                        eKilled = removeEnemies.size();
                        enemiesKilled+= eKilled;
                        notifyObservers(EVENT.HIT_BULLET);
                    }
                }

                // check if enemies reach bottom line
                if (enemy.collideWith(shooter.getBottomLine().get(0))) {
                    notifyObservers(EVENT.REACH_BOTTOM);
                }
            }
            row.removeAll(removeEnemies);
        }
        shooter.getWeapons().removeAll(removeBullets);

        if (enemiesKilled == totalEnemies) {
            notifyObservers(EVENT.ALL_E_DESTROYED);
        }

        // bullet vs bombs
        var removeBombs = new ArrayList<GameElement>();
        removeBullets.clear();
        for (var b : bombs) {
            for (var bullet : shooter.getWeapons()) {
                if (b.collideWith(bullet)) {
                    removeBombs.add(b);
                    removeBullets.add(bullet);
                }
            }
        }
        shooter.getWeapons().removeAll(removeBullets);
        bombs.removeAll(removeBombs);

        // bombs vs components
        var removeComponents = new ArrayList<GameElement>();
        for (var component : shooter.getComponents()) {
            for (var b : bombs) {
                if (component.collideWith(b)) {
                    removeBombs.add(b);
                    removeComponents.add(component);
                    shooter.totalComponents--;
                }
            }
        }
        shooter.getComponents().removeAll(removeComponents);
        bombs.removeAll(removeBombs);
        if (shooter.totalComponents == 0) {
            notifyObservers(EVENT.ALL_C_DESTROYED);
        }

        // enemies vs components
        for (var row : rows) {
            for (var e : row) {
                for (var component : shooter.getComponents()) {
                    if (e.collideWith(component)) {
                        removeComponents.add(component);
                        shooter.totalComponents--;
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
            case HIT_BULLET:
                for (var o: observers) {
                    o.enemyHitBullet();
                }
                break;
            case REACH_BOTTOM:
                for (var o: observers) {
                    o.enemyReachBottom();
                }
                break;
            case ALL_E_DESTROYED:
                for (var o: observers) {
                    o.enemyAllDestroyed();
                }
                break;
            case ALL_C_DESTROYED:
                for (var o: observers) {
                    o.allComponentsDestroyed();
                }
        }


    }

    
}