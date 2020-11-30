package model.gameStatePattern;

import model.EnemyComposite;

public class GameWave2 implements GameState {

    private EnemyComposite enemyComposite;

    public GameWave2 (EnemyComposite enemyComposite) {
        this.enemyComposite = enemyComposite;
        enemyComposite.enemyFormation(2);
    }

    @Override
    public void nextState(EnemyComposite context) {
        
        context.setState(new GameFinalWave(enemyComposite));
    }
    
}