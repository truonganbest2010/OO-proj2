package model.laserStrategyPattern;

import java.awt.Graphics2D;

import model.Laser;

public class LaserRenderFireIntoSpace implements LaserRenderStrategy {

    private Laser laser;

    public LaserRenderFireIntoSpace (Laser laser) {
        this.laser = laser;
    }


    @Override
    public void renderAlgorithm(Graphics2D g2) {
        // TODO Auto-generated method stub
        g2.setColor(laser.color);
        
        if (laser.filled) {
            g2.fillRect(laser.x, laser.y, laser.width, laser.height);
        }
        else {
            g2.drawRect(laser.x, laser.y, laser.width, laser.height);
        }
    }
    
}