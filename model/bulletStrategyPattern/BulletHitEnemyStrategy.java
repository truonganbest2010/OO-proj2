package model.bulletStrategyPattern;

import model.Bullet;

public class BulletHitEnemyStrategy implements BulletMoveStrategy {

    private Bullet bullet;

    public static final int MAX_MOVE = 5;
    private int count;

    public BulletHitEnemyStrategy(Bullet bullet) {
        this.bullet = bullet;
        count = 1;
    }

    @Override
    public void moveAlgorithm() {
        
        if (count < MAX_MOVE) {
            bullet.y -= 1;
            count++;
        } else {
            bullet.y = -10;
        }

    }
    
}