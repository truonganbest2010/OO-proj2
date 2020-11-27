package model.laserStrategyPattern;

import java.awt.Graphics2D;
import java.awt.*;

import model.Laser;

public class LaserRenderFireAtEnemy implements LaserRenderStrategy {

    private Laser laser;

    public LaserRenderFireAtEnemy (Laser laser) {
        this.laser = laser;
    }


    @Override
    public void renderAlgorithm(Graphics2D g2) {
        // TODO Auto-generated method stub
        g2.setColor(Color.red);
        
        if (laser.filled) {
            g2.fillRect(laser.x, laser.y, laser.width, laser.height);
        }
        else {
            g2.drawRect(laser.x, laser.y, laser.width, laser.height);
        }
    }
    
}