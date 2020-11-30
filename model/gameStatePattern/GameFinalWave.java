package model.gameStatePattern;

import model.EnemyComposite;
import view.GameBoard;
import view.TextDraw;

import java.awt.*;

public class GameFinalWave implements GameState {

    public GameFinalWave(EnemyComposite enemyComposite) {
        enemyComposite.enemyFormation(3);
        enemyComposite.getText().add(new TextDraw("Final   Wave", GameBoard.WIDTH/3, 0, Color.LIGHT_GRAY, 30));

    }

    @Override
    public void nextState(EnemyComposite context) {
        context.setState(null);

    }
    

}