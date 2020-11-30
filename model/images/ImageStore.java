package model.images;

import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;

import model.Bomb;
import model.BonusLightning;
import model.Bullet;
import model.EnemyComposite;
import model.ShooterElement;
import view.GameBoard;

public class ImageStore {
    // Enemy
    public static BufferedImage enemy_white, enemy_yellow; 
    // Shooter Components
    public static BufferedImage sc1, sc2, sc3, sc4; 
    // Explosion Animation
    public static BufferedImage explode, e1, e2, e3, e4, e5; 

    public static BufferedImage bullet;
    public static BufferedImage bomb;
    public static BufferedImage lightning;

    public static BufferedImage bonus;

    // Icon
    public static BufferedImage health_icon, lightning_icon, target_icon, bullet_icon;


    static {
        enemy_white = readImage("model/images/enemy/enemy_white.png", EnemyComposite.ENEMY_SIZE, EnemyComposite.ENEMY_SIZE);
        enemy_yellow = readImage("model/images/enemy/enemy_yellow.png", EnemyComposite.ENEMY_SIZE, EnemyComposite.ENEMY_SIZE);

        bullet = readImage("model/images/bullet.png", Bullet.WIDTH, Bullet.WIDTH);
        bomb = readImage("model/images/bomb.png", Bomb.SIZE, Bomb.SIZE*2);

        bonus = readImage("model/images/bonus.png", BonusLightning.WIDTH, BonusLightning.WIDTH);

        explode = readImage("model/images/explode.png", 25, 25);
        e1 = readImage("model/images/explosion/e1.png", 25, 25);
        e2 = readImage("model/images/explosion/e2.png", 25, 25);
        e3 = readImage("model/images/explosion/e3.png", 25, 25);
        e4 = readImage("model/images/explosion/e4.png", 25, 25);
        e5 = readImage("model/images/explosion/e5.png", 25, 25);

        lightning = readImage("model/images/lightning.png", 30, GameBoard.HEIGHT-ShooterElement.SIZE);

        lightning_icon = readImage("model/images/icon/lightning_icon.png", 15, 15);
        health_icon = readImage("model/images/icon/health_icon.png", 15, 15);
        target_icon = readImage("model/images/icon/target_icon.png", 15, 15);
        bullet_icon = readImage("model/images/icon/bullet_icon.png", 12, 18);

        sc1 = readImage("model/images/components/sc1.png", 20, 20);
        sc2 = readImage("model/images/components/sc2.png", 20, 20);
        sc3 = readImage("model/images/components/sc3.png", 20, 20);
        sc4 = readImage("model/images/components/sc4.png", 20, 20);

    }

    public static BufferedImage readImage(String path, int width, int height) {
        try {
            BufferedImage originalImg = ImageIO.read(new File(path));
            Image tmp = originalImg.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage resizedImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = resizedImg.createGraphics();
            g2.drawImage(tmp, 0, 0, null);
            g2.dispose();
            return resizedImg;
        } catch (Exception e) {
                System.out.println("Load error");
        }
        return null;
    }
}