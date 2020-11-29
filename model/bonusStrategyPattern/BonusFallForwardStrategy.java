package model.bonusStrategyPattern;

import model.BonusDropper;

public class BonusFallForwardStrategy implements BonusMoveStrategy {

    private BonusDropper bonusDropper;

    public BonusFallForwardStrategy(BonusDropper bonusDropper) {
        this.bonusDropper = bonusDropper;
    }


    @Override
    public void moveAlgorithm() {
        
        for (var bn: bonusDropper.getBonusDropper()) {
            bn.y += BonusDropper.UNIT_MOVE;
        }

    }
    
}