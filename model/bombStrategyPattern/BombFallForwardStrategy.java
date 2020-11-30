package model.bombStrategyPattern;

import model.Bomb;

public class BombFallForwardStrategy implements BombMoveStrategy {
    
    private Bomb bomb;

    public BombFallForwardStrategy(Bomb bomb) {
        this.bomb = bomb;
    }

    @Override
    public void moveAlgorithm() {
        // TODO Auto-generated method stub

        bomb.y += Bomb.UNIT_MOVE;
    }

    
}