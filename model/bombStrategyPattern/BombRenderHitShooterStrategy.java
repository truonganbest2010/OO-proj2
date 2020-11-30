package model.bombStrategyPattern;

import java.awt.Graphics2D;

import model.Bomb;
import model.images.ImageStore;

public class BombRenderHitShooterStrategy implements BombRenderStrategy {

    private Bomb bomb;
    public static final int MAX_GIF = 20;
    private int count;
    
    public BombRenderHitShooterStrategy(Bomb bomb) {
        this.bomb = bomb;
    }

    @Override
    public void renderAlgorithm(Graphics2D g2) {

        if (count <= MAX_GIF/5){
            g2.drawImage(ImageStore.e1, null, bomb.x, bomb.y);
        }
        else if (count <= MAX_GIF*2/5){
            g2.drawImage(ImageStore.e2, null, bomb.x, bomb.y);
        }
        else if (count <= MAX_GIF*3/5){
            g2.drawImage(ImageStore.e3, null, bomb.x, bomb.y);
        }
        else if (count <= MAX_GIF*4/5){
            g2.drawImage(ImageStore.e4, null, bomb.x, bomb.y);
        }
        else if (count >= MAX_GIF){
            g2.drawImage(ImageStore.e5, null, bomb.x, bomb.y);
        }
        count++;
    }
    
}