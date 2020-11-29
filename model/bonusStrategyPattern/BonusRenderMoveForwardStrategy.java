package model.bonusStrategyPattern;

import java.awt.Graphics2D;

import model.BonusDropper;

public class BonusRenderMoveForwardStrategy implements BonusRenderStrategy {

    private BonusDropper bonusDropper;

    public BonusRenderMoveForwardStrategy (BonusDropper bonusDropper) {
        this.bonusDropper = bonusDropper;
    }

    @Override
    public void renderAlgorithm(Graphics2D g2) {
        
        for (var bn: bonusDropper.getBonusDropper()) {
            bn.render(g2);
        }

    }
    
}