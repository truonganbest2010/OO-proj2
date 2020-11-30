package model.gameStatePattern;

import model.EnemyComposite;

public class GameFinalWave implements GameState {

    public GameFinalWave(EnemyComposite enemyComposite) {
        enemyComposite.enemyFormation(3);

    }

    @Override
    public void nextState(EnemyComposite context) {
        context.setState(null);

    }
    

}