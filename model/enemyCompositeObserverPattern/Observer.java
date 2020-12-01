package model.enemyCompositeObserverPattern;

public interface Observer {
    
    void enemiesGotShot();
    void enemiesReachBottom();
    void enemiesAllGone();
    void enemiesDestroyedShooter();
}