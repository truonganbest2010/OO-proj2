package model.bulletStrategyPattern;

import java.awt.Graphics2D;

import model.Bullet;

public class BulletRenderMoveForwardStrategy implements BulletRenderStrategy {

    private Bullet bullet;

    public BulletRenderMoveForwardStrategy(Bullet bullet) {
        this.bullet = bullet;
    }

    @Override
    public void renderAlgorithm(Graphics2D g2) {
        
        g2.drawImage(bullet.getImage(), null, bullet.x, bullet.y);

    }
    
}