package model.gameStatePattern;

import model.EnemyComposite;
import view.GameBoard;
import view.TextDraw;
import java.awt.*;

public class GameWave2 implements GameState {

    private EnemyComposite enemyComposite;

    public GameWave2 (EnemyComposite enemyComposite) {
        this.enemyComposite = enemyComposite;
        enemyComposite.enemyFormation(2);
        enemyComposite.getText().add(new TextDraw("Second   Wave", GameBoard.WIDTH/3, 0, Color.LIGHT_GRAY, 30));

    }

    @Override
    public void nextState(EnemyComposite context) {
        
        context.setState(new GameFinalWave(enemyComposite));
    }
    
}