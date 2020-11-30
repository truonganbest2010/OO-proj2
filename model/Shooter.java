package model;

import java.awt.*;
import java.util.ArrayList;

import model.images.ImageStore;
import view.GameBoard;

public class Shooter extends GameElement {

    public static final int UNIT_MOVE = 10;

    private int lightningShoot = 1;
    private int bulletShoot = 5;

    private ArrayList<GameElement> components = new ArrayList<>();
    private ArrayList<Bullet> weapons = new ArrayList<>();
    private ArrayList<Lightning> lightningGun = new ArrayList<>();
    private ArrayList<GameElement> bottomLine = new ArrayList<>();

    public Shooter(int x, int y) {
        super(x, y, 0, 0);

        var size = ShooterElement.SIZE;
        var s1 = new ShooterElement(x-size, y-size, Color.white, false);
         s1.setImage(ImageStore.sc1);
        var s2 = new ShooterElement(x, y-size, Color.white, false);
         s2.setImage(ImageStore.sc2);
        var s3 = new ShooterElement(x-size, y, Color.white, false);
         s3.setImage(ImageStore.sc3);
        var s4 = new ShooterElement(x, y, Color.white, false);
         s4.setImage(ImageStore.sc4);
        components.add(s1);
        components.add(s2);
        components.add(s3);
        components.add(s4);

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
        return weapons.size() < bulletShoot;
    }
    public boolean canFireLightning() {
        return lightningShoot > 0 && lightningGun.size() < 1;
    }

    public int getLightningShoot() {
        return lightningShoot;
    }
    public void setlightningShoot(int lightningShoot) {
        this.lightningShoot = lightningShoot;
    }
    public int getBulletShoot() {
        return bulletShoot;
    }
    public void setBulletShoot(int bulletShoot) {
        this.bulletShoot = bulletShoot;
    }

    

    public void removeOutOfUpperBound() {
        var remove = new ArrayList<GameElement>();
        for (var w: weapons) {
            if (w.y < 0) remove.add(w);
        }
        weapons.removeAll(remove);

        for (var lg: lightningGun) {
            if (lg.y < 0) remove.add(lg);
        }
        lightningGun.removeAll(remove);
    }

    public ArrayList<Bullet> getWeapons() {
        return weapons;
    }
    public ArrayList<Lightning> getLightningGun() {
        return lightningGun;
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
        for (var lg: lightningGun) {
            lg.render(g2);
        }


        /** Shooter Info */

        g2.setColor(Color.white);
        g2.setFont(new Font("Courier", Font.BOLD, 10));

        g2.drawString("" + bulletShoot, 25, GameBoard.HEIGHT - 50);
        g2.drawImage(ImageStore.bullet_icon, null, 5, GameBoard.HEIGHT - 70);
        g2.drawString("" + components.size(), 25, GameBoard.HEIGHT - 30);
        g2.drawImage(ImageStore.health_icon, null, 5, GameBoard.HEIGHT - 40);
        g2.drawString("" + lightningShoot, 25, GameBoard.HEIGHT - 10);
        g2.drawImage(ImageStore.lightning_icon, null, 5, GameBoard.HEIGHT - 20);

    }

    @Override
    public void animate() {

        for (var w: weapons) {
            w.animate();
        }
        for  (var lg: lightningGun) {
            lg.animate();
        }

    }
    
}