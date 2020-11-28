package model;

import java.awt.*;

import model.bulletStrategyPattern.BulletMoveForwardStrategy;
import model.bulletStrategyPattern.BulletMoveStrategy;
import model.bulletStrategyPattern.BulletRenderMoveForwardStrategy;
import model.bulletStrategyPattern.BulletRenderStrategy;

public class Bullet extends GameElement {

    public static final int WIDTH = 5;
    public static final int UNIT_MOVE = 10;
    private BulletMoveStrategy moveStrategy;
    private BulletRenderStrategy renderStrategy;

    public Bullet(int x, int y) {
        super(x, y, Color.red, true, WIDTH, WIDTH*3);

        moveStrategy = new BulletMoveForwardStrategy(this);
        renderStrategy = new BulletRenderMoveForwardStrategy(this);
    }

    public void setMoveStrategy(BulletMoveStrategy moveStrategy) {
        this.moveStrategy = moveStrategy;
    }
    public void setRenderStrategy(BulletRenderStrategy renderStrategy) {
        this.renderStrategy = renderStrategy;
    }


    @Override
    public void render(Graphics2D g2) {

        // g2.setColor(color);
        
        // if (filled) {
        //     g2.fillRect(x, y, width, height);
        // }
        // else {
        //     g2.drawRect(x, y, width, height);
        // }
        this.renderStrategy.renderAlgorithm(g2);
        
    }

    @Override
    public void animate() {    
        this.moveStrategy.moveAlgorithm();
    }
    
}