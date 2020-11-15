package model.observerPattern;

public interface Observer {
    
    void enemyHitBullet();
    void enemyReachBottom();
    void enemyAllDestroyed();
    void allComponentsDestroyed();
}