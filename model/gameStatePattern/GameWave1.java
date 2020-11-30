package model.gameStatePattern;

import model.EnemyComposite;

public class GameWave1 implements GameState {

    private EnemyComposite enemyComposite;

    public GameWave1(EnemyComposite enemyComposite) {
        this.enemyComposite = enemyComposite;
        enemyComposite.enemyFormation(1);
    }

    @Override
    public void nextState(EnemyComposite context) {
        
        context.setState(new GameWave2(enemyComposite));
    }
    
}