package model.bulletStrategyPattern;

import java.awt.Graphics2D;

import model.Bullet;
import model.images.ImageStore;

public class BulletRenderHitEnemyStrategy implements BulletRenderStrategy {

    private Bullet bullet;
    public static final int MAX_GIF = 15;
    private int count;

    public BulletRenderHitEnemyStrategy(Bullet bullet) {
        this.bullet = bullet;
        count = 1;
    }


    @Override
    public void renderAlgorithm(Graphics2D g2) {
        
        if (count <= MAX_GIF/5){
            g2.drawImage(ImageStore.e1, null, bullet.x, bullet.y);
            // count++;
        }
        else if (count <= MAX_GIF*2/5){
            g2.drawImage(ImageStore.e2, null, bullet.x, bullet.y);
            // count++;
        }
        else if (count <= MAX_GIF*3/5){
            g2.drawImage(ImageStore.e3, null, bullet.x, bullet.y);
            // count++;
        }
        else if (count <= MAX_GIF*4/5){
            g2.drawImage(ImageStore.e4, null, bullet.x, bullet.y);
            // count++;
        }
        else if (count >= MAX_GIF){
            g2.drawImage(ImageStore.e5, null, bullet.x, bullet.y);
        }
        count++;
        
    }
    
}