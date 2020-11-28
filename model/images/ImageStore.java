package model.images;

import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;

import model.ShooterElement;
import view.GameBoard;

public class ImageStore {
    
    public static BufferedImage enemy;
    public static BufferedImage shooterComponent1, shooterComponent2, shooterComponent3, shooterComponent4;
    public static BufferedImage bullet, explode;
    public static BufferedImage bomb;
    public static BufferedImage lightning;
    public static BufferedImage lightning_icon;



    static {
        enemy = readImage("model/images/enemy.png", 20, 20);
        bullet = readImage("model/images/bullet.png", 7, 7);
        explode = readImage("model/images/explode.png", 20, 20);
        lightning = readImage("model/images/lightning.png", 30, GameBoard.HEIGHT-ShooterElement.SIZE);
        lightning_icon = readImage("model/images/lightning_icon.png", 15, 15);


        shooterComponent1 = readImage("model/images/components/c1.png", 20, 20);
        shooterComponent2 = readImage("model/images/components/c2.png", 20, 20);
        shooterComponent3 = readImage("model/images/components/c3.png", 20, 20);
        shooterComponent4 = readImage("model/images/components/c4.png", 20, 20);


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