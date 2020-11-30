package model.bombStrategyPattern;

import model.Bomb;
import model.ShooterElement;
import view.GameBoard;

public class BombHitShooterStrategy implements BombMoveStrategy {

    private Bomb bomb;
    public static final int MAX_MOVE = 20;
    private int count;

    public BombHitShooterStrategy(Bomb bomb) {
        this.bomb = bomb;
        count = 0;
        bomb.y = GameBoard.HEIGHT - ShooterElement.SIZE*4;
    }

    @Override
    public void moveAlgorithm() {
        // TODO Auto-generated method stub

        if (count < MAX_MOVE) {
            bomb.y -= 0.2;
            count++;
        } else {
            bomb.y += 1000;
        }
    }
    
}