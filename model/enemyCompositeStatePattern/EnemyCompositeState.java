package model.enemyCompositeStatePattern;

import model.EnemyComposite;

public interface EnemyCompositeState {
    
    void nextWave(EnemyComposite context);
}