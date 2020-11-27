package model;

import java.awt.*;
import java.util.ArrayList;

import view.GameBoard;

public class Shooter extends GameElement {

    public static final int UNIT_MOVE = 10;
    public static final int MAX_BULLETS = 5;

    private int laserCount = 10;

    private ArrayList<GameElement> components = new ArrayList<>();
    private ArrayList<GameElement> weapons = new ArrayList<>();
    private ArrayList<Laser> laserGun = new ArrayList<>();
    private ArrayList<GameElement> bottomLine = new ArrayList<>();

    public int totalComponents;

    public Shooter(int x, int y) {
        super(x, y, 0, 0);

        var size = ShooterElement.SIZE;
        var s1 = new ShooterElement(x-size, y-size, Color.white, false);
        var s2 = new ShooterElement(x, y-size, Color.white, false);
        var s3 = new ShooterElement(x-size, y, Color.white, false);
        var s4 = new ShooterElement(x, y, Color.white, false);
        components.add(s1);
        components.add(s2);
        components.add(s3);
        components.add(s4);
        totalComponents = components.size();

        bottomLine.add(new BottomLine(0, GameBoard.HEIGHT-EnemyComposite.ENEMY_SIZE/2, GameBoard.WIDTH, EnemyComposite.ENEMY_SIZE/2));
    }

    public void moveLeft() {
        super.x -= UNIT_MOVE;
        for (var c: components) {
            c.x -= UNIT_MOVE;
        }
    }

    public void moveRight() {
        super.x += UNIT_MOVE;
        for (var c: components) {
            c.x += UNIT_MOVE;
        }
    }

    public boolean canFireBullets() {
        return weapons.size() < MAX_BULLETS;
    }

    public boolean canFireLaser() {
        return laserCount > 0 && laserGun.size() < 1;
    }

    public int getLaserCount() {
        return laserCount;
    }

    public void setLaserCount(int laserCount) {
        this.laserCount = laserCount;
    }

    

    public void removeOutOfBound() {
        var remove = new ArrayList<GameElement>();
        for (var w: weapons) {
            if (w.y < 0) remove.add(w);
        }
        weapons.removeAll(remove);

        for (var lg: laserGun) {
            if (lg.y < 0) remove.add(lg);
        }
        laserGun.removeAll(remove);
    }

    public ArrayList<GameElement> getWeapons() {
        return weapons;
    }
    public ArrayList<Laser> getLaserGun() {
        return laserGun;
    }
    public ArrayList<GameElement> getComponents() {
        return components;
    }
    public ArrayList<GameElement> getBottomLine() {
        return bottomLine;
    }

    @Override
    public void render(Graphics2D g2) {

        for (var c: components) {
            c.render(g2);
        }
        for (var w: weapons) {
            w.render(g2);
        }
        for (var lg: laserGun) {
            lg.render(g2);
        }

    }

    @Override
    public void animate() {

        for (var w: weapons) {
            w.animate();
        }
        for  (var lg: laserGun) {
            lg.animate();
        }

    }
    
}