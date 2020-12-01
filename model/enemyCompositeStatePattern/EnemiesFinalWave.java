package model.enemyCompositeStatePattern;

import model.EnemyComposite;
import view.GameBoard;
import view.TextDraw;

import java.awt.*;

public class EnemiesFinalWave implements EnemyCompositeState {

    public EnemiesFinalWave(EnemyComposite enemyComposite) {
        enemyComposite.enemyFormation(3);
        enemyComposite.getText().add(new TextDraw("Final   Wave", GameBoard.WIDTH/3, -100, Color.LIGHT_GRAY, 30));

    }

    @Override
    public void nextWave(EnemyComposite context) {
        context.setState(null);

    }
    

}