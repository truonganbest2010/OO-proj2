package model.laserStrategyPattern;

import model.Laser;

public class LaserFireIntoSpace implements LaserFireStrategy {

    private Laser laser;
    
    public LaserFireIntoSpace(Laser laser) {
        this.laser = laser;
    }

    @Override
    public void fireAlgorithm() {
        // TODO Auto-generated method stub

        laser.y -= Laser.UNIT_MOVE;


    }
}