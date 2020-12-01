package model.enemyCompositeStatePattern;

import model.EnemyComposite;
import view.GameBoard;
import view.TextDraw;
import java.awt.*;

public class EnemiesWave2 implements EnemyCompositeState {

    private EnemyComposite enemyComposite;

    public EnemiesWave2 (EnemyComposite enemyComposite) {
        this.enemyComposite = enemyComposite;
        enemyComposite.enemyFormation(2);
        enemyComposite.getText().add(new TextDraw("Second   Wave", GameBoard.WIDTH/3, -100, Color.LIGHT_GRAY, 30));

    }

    @Override
    public void nextWave(EnemyComposite context) {
        
        context.setState(new EnemiesFinalWave(enemyComposite));
    }
    
}