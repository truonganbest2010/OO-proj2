package model.bombStrategyPattern;

import java.awt.Graphics2D;

import model.Bomb;
import model.images.ImageStore;

public class BombRenderFallForwardStrategy implements BombRenderStrategy {

    private Bomb bomb;

    public BombRenderFallForwardStrategy(Bomb bomb) {
        this.bomb = bomb;
    }

    @Override
    public void renderAlgorithm(Graphics2D g2) {
        // TODO Auto-generated method stub
        g2.drawImage(ImageStore.bomb, null, bomb.x, bomb.y);

    }
    
}