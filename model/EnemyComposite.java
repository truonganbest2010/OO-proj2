package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import view.GameBoard;

public class EnemyComposite extends GameElement {

    public static final int NROWS = 2;
    public static final int NCOLS = 10;
    public static final int ENEMY_SIZE = 20;
    public static final int UNIT_MOVE = 5;
    
    /** proj2 implementation */
    public static final int UNIT_MOVE_DOWN = 20;

    private ArrayList<ArrayList<GameElement>> rows;
    private ArrayList<GameElement> bombs;
    private boolean movingToRight = true;
    private Random random = new Random();

    private int totalEnemies = NROWS*NCOLS;
    public int enemiesAlive = 0;
    public int enemiesKilled;
    public boolean gameOver = false;
    
    public EnemyComposite(){
        rows = new ArrayList<>();
        bombs = new ArrayList<>();

        for (int r = 0; r < NROWS; r++) {
            var oneRow = new ArrayList<GameElement>();
            rows.add(oneRow);
            for (int c = 0; c < NCOLS; c++) {
                oneRow.add(
                    new Enemy(
                        c * ENEMY_SIZE * 2,
                        r * ENEMY_SIZE * 2,
                        ENEMY_SIZE,
                        Color.yellow,
                        true)
                    );
            }
        }
    }

    @Override
    public void render(Graphics2D g2) {
        // render enemy array
        for (var r: rows) {
            for (var e: r) {
                e.render(g2);
            }
        }

        // render enemy bombs
        for (var b: bombs) {
            b.render(g2);
        }
    }

    @Override
    public void animate() {
        int dx = UNIT_MOVE;
        int dy = UNIT_MOVE_DOWN;

        if (movingToRight) {
            if (rightEnd() >= GameBoard.WIDTH) {
                dx -= dx;
                movingToRight = false;
                // move down dy unit when hitting rightEnd
                for (var row: rows) {
                    for (var e: row) {
                        e.y += dy;
                    }
                }
            }
        } else {
            dx = -dx;
            if (leftEnd() <= 0) {
                dx = -dx;
                movingToRight = true;
                // move down dy unit when hitting leftEnd
                for (var row: rows) {
                    for (var e: row) {
                        e.y += dy;
                    }
                }
            }
        }

        // update x loc
        for (var row: rows) {
            for (var e: row) {
                e.x += dx;
            }
        }
        
        // animate bombs
        for (var b: bombs) {
            b.animate();
        }
    }

    private int rightEnd() {
        int xEnd = -100;
        for (var row: rows) {
            if (row.size() == 0) continue;
            int x = row.get(row.size()-1).x + ENEMY_SIZE;
            if (x > xEnd) xEnd=x;
        }
        return xEnd;
    }

    private int leftEnd() {
        int xEnd = 9000;
        for (var row: rows) {
            if (row.size() == 0) continue;
            int x = row.get(0).x;
            if (x < xEnd) xEnd=x;
        }
        return xEnd;
    }

    public void dropBombs() {
        for (var row: rows) {
            for (var e: row) {
                if (random.nextFloat() < 0.1F) {
                    bombs.add(new Bomb(e.x, e.y));
                }
            }
        }
    }

    public void removeBombsOutOfBound() {
        var remove = new ArrayList<GameElement>();
        for (var b: bombs) {
            if (b.y >= GameBoard.HEIGHT) {
                remove.add(b);
            }
        }
        bombs.removeAll(remove);
    }

    public void processCollision(Shooter shooter) {

        // bullet vs enemies
        var removeBullets = new ArrayList<GameElement>();
        for (int i=0; i<rows.size(); i++) {
            if (i == 0) enemiesAlive = 0;
            var row = rows.get(i);
            var removeEnemies = new ArrayList<GameElement>();
            for (int j=0; j<row.size(); j++) {
                var enemy = row.get(j);
                for (var bullet: shooter.getWeapons()) {
                    if (enemy.collideWith(bullet)) {
                        removeBullets.add(bullet);
                        removeEnemies.add(enemy);
                    }
                }
                enemiesAlive++;
            }
            row.removeAll(removeEnemies);
        }
        shooter.getWeapons().removeAll(removeBullets);
        enemiesKilled = totalEnemies-enemiesAlive;
        if (enemiesAlive == 0) gameOver=true;

        // bullet vs bombs
        var removeBombs = new ArrayList<GameElement>();
        removeBullets.clear();
        for (var b: bombs) {
            for (var bullet: shooter.getWeapons()) {
                if (b.collideWith(bullet)) {
                    removeBombs.add(b);
                    removeBullets.add(bullet);
                }
            }
        }
        shooter.getWeapons().removeAll(removeBullets);
        bombs.removeAll(removeBombs);
    }

    
}