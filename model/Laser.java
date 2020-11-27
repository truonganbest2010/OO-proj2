package model;

import java.awt.Color;
import java.awt.Graphics2D;

import model.laserStrategyPattern.LaserFireIntoSpace;
import model.laserStrategyPattern.LaserFireStrategy;
import model.laserStrategyPattern.LaserRenderFireIntoSpace;
import model.laserStrategyPattern.LaserRenderStrategy;
import view.GameBoard;

public class Laser extends GameElement {

    public static final int WIDTH = 3;
    public static final int UNIT_MOVE = 1;

    private LaserFireStrategy fireStrategy;
    private LaserRenderStrategy renderStrategy;

    public Laser(int x, int y) {
        super(x, y, Color.white, true, WIDTH, GameBoard.HEIGHT-ShooterElement.SIZE);
    
        fireStrategy = new LaserFireIntoSpace(this);
        renderStrategy = new LaserRenderFireIntoSpace(this);
    }

    public void setFireStrategy(LaserFireStrategy fireStrategy) {
        this.fireStrategy = fireStrategy;
    }
    public void setRenderStrategy(LaserRenderStrategy renderStrategy) {
        this.renderStrategy = renderStrategy;
    }
    

    @Override
    public void render(Graphics2D g2) {

        this.renderStrategy.renderAlgorithm(g2);
        
    }

    @Override
    public void animate() {
        
        this.fireStrategy.fireAlgorithm();
        
    }
    
}