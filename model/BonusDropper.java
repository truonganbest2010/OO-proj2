package model;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import view.GameBoard;

public class BonusDropper extends GameElement {

    private GameBoard gameBoard;
    private ArrayList<GameElement> bonus;

    public BonusDropper(GameBoard gameBoard) {
        this.gameBoard = gameBoard;

        bonus = new ArrayList<>();
    }


    @Override
    public void render(Graphics2D g2) {    
        // render bonus
        for (var bn : bonus) {
            bn.render(g2);
        }
    }

    @Override
    public void animate() {
        for (var bn: bonus) {
            bn.animate();
        }
    }
    
    public void dropBonus() {
        if (bonus.size() < 1) {
            Random x = new Random();
            bonus.add(new BonusLightning(x.nextInt(GameBoard.WIDTH), 0));
        }
    }

    public void removeBonusOutOfLowerBound() {
        var remove = new ArrayList<>();
        for (var bn: bonus) {
            if (bn.y >= GameBoard.HEIGHT) {
                remove.add(bn);
                gameBoard.getTimerListener().setBonus_frameCounter(0);
            }
        }
        bonus.removeAll(remove);
    }

}