package model.observerPattern;

public interface Observer {
    
    void enemiesGotShot();
    void enemiesReachBottom();
    void enemiesAllGone();
    void enemiesDestroyedComponents();
}