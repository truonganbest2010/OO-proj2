package model.gameStatePattern;

import model.EnemyComposite;

public interface GameState {
    
    void nextState(EnemyComposite context);
}