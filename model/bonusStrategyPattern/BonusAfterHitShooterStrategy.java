package model.bonusStrategyPattern;

import model.BonusDropper;
import model.ShooterElement;
import view.GameBoard;

public class BonusAfterHitShooterStrategy implements BonusMoveStrategy {

    private BonusDropper bonusDropper;
    public static final int MAX_MOVE = 20;
    private int count = 0;


    public BonusAfterHitShooterStrategy(BonusDropper bonusDropper) {
        this.bonusDropper = bonusDropper;
        for (var bn: bonusDropper.getBonusDropper()) {
            bn.y = GameBoard.HEIGHT-ShooterElement.SIZE*4;
        }
    }

    @Override
    public void moveAlgorithm() {

        if (count < MAX_MOVE) {
            for (var bn: bonusDropper.getBonusDropper()) {
                bn.y -= 1;
            }
            count+= 1;
        } else {
            for (var bn: bonusDropper.getBonusDropper()) {
                bn.y += 1000;
            }
        }

    }
    
}