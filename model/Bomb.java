package model;

import java.awt.*;

import model.bombStrategyPattern.BombFallForwardStrategy;
import model.bombStrategyPattern.BombMoveStrategy;
import model.bombStrategyPattern.BombRenderFallForwardStrategy;
import model.bombStrategyPattern.BombRenderStrategy;

public class Bomb extends GameElement {

    public static final int SIZE = 12;
    public static final int UNIT_MOVE = 5;
    private BombMoveStrategy moveStrategy;
    private BombRenderStrategy renderStrategy;

    public Bomb(int x, int y) {
        super(x, y, Color.green, true, SIZE, SIZE*2);
        moveStrategy = new BombFallForwardStrategy(this);
        renderStrategy = new BombRenderFallForwardStrategy(this);
    }

    public void setMoveStrategy(BombMoveStrategy moveStrategy) {
        this.moveStrategy = moveStrategy;
    }
    public void setRenderStrategy(BombRenderStrategy renderStrategy) {
        this.renderStrategy = renderStrategy;
    }


    @Override
    public void render(Graphics2D g2) {
        // g2.setColor(color);
        // if (filled) {
        //     g2.fillOval(x, y, width, height);
        // }
        // else  {
        //     g2.drawOval(x, y, width, height);
        // }
        renderStrategy.renderAlgorithm(g2);
    }

    @Override
    public void animate() {
        // super.y += UNIT_MOVE;
        moveStrategy.moveAlgorithm();
    }
    
}