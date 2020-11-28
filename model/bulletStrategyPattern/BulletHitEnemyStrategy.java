package model.bulletStrategyPattern;

import model.Bullet;
import model.EnemyComposite;

public class BulletHitEnemyStrategy implements BulletMoveStrategy {

    private Bullet bullet;

    public static final int MAX_MOVE = 15;
    private int count;
    private boolean moveRight;

    public BulletHitEnemyStrategy(Bullet bullet) {
        this.bullet = bullet;
        count = 1;
    }

    @Override
    public void moveAlgorithm() {
        
        if (count < MAX_MOVE) {
            if (moveRight) {
                bullet.x += EnemyComposite.UNIT_MOVE;
            }
            else {
                bullet.x -= EnemyComposite.UNIT_MOVE;
            }
            bullet.y -= 0.2;
            count++;
        } else {
            bullet.y = -10;
        }

    }

    public void setMoveRight(boolean moveRight) {
        this.moveRight = moveRight;
    }
    
}