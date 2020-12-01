package model.enemyCompositeObserverPattern;

import model.EnemyComposite;

public interface Subject {
    
    void addEnemyListener(Observer o);
    void removeEnemyListener(Observer o);
    void notifyObservers(EnemyComposite.EVENT event);
}