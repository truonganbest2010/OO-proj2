package model.gameStatePattern;

import java.awt.Color;

import model.EnemyComposite;
import view.GameBoard;
import view.TextDraw;

public class GameWave1 implements GameState {

    private EnemyComposite enemyComposite;

    public GameWave1(EnemyComposite enemyComposite) {
        this.enemyComposite = enemyComposite;
        enemyComposite.enemyFormation(1);
        enemyComposite.getText().add(new TextDraw("First   Wave", GameBoard.WIDTH/3, -100, Color.LIGHT_GRAY, 30));
    }

    @Override
    public void nextState(EnemyComposite context) {
        
        context.setState(new GameWave2(enemyComposite));
    }
    
}