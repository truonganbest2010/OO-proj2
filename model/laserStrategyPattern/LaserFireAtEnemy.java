package model.laserStrategyPattern;

import model.Laser;

public class LaserFireAtEnemy implements LaserFireStrategy {

    private Laser laser;

    public LaserFireAtEnemy(Laser laser) {
        this.laser = laser;
    }


    @Override
    public void fireAlgorithm() {
        // TODO Auto-generated method stub
        laser.y -= Laser.UNIT_MOVE;
    }
    
}