package model.bulletStrategyPattern;

import model.Bullet;

public class BulletMoveForwardStrategy implements BulletMoveStrategy {

    private Bullet bullet;

    public BulletMoveForwardStrategy(Bullet bullet) {
        this.bullet = bullet;
    }

    @Override
    public void moveAlgorithm() {

        bullet.y -= Bullet.UNIT_MOVE;

    }
    
}