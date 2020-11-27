package controller;

import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;

import model.Bullet;
import model.*;
import model.Shooter;
import view.GameBoard;

import model.laserStrategyPattern.*;


public class TimerListener implements ActionListener {

    public static final int SCORE_UP = 10;

    public enum EVENT_TYPE {
        KEY_RIGHT, KEY_LEFT, KEY_SPACE, KEY_L
    }

    private GameBoard gameBoard;
    private LinkedList<EVENT_TYPE> eventQueue;
    private final int BOMB_DROP_FREQ = 50;
    
    private int frameCounter = 0;

    public TimerListener(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        eventQueue = new LinkedList<>();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ++frameCounter;
        // System.out.println(frameCounter);
        if (!gameBoard.isGameOver()) {
            update();
            processEventQueue();
            processCollision();
    
        } else {
            gameBoard.getStartBtn().setText("New Game");
            gameBoard.getPauseBtn().setEnabled(false);
            gameBoard.getStartBtn().setEnabled(true);
            gameBoard.getTimer().stop();
        }

        gameBoard.getCanvas().repaint();
    }

    private void processEventQueue() {

        while (!eventQueue.isEmpty()) {
            var e = eventQueue.getFirst();
            eventQueue.removeFirst();
            Shooter shooter = gameBoard.getShooter();
            if (shooter == null) return;
            // System.out.println(e);
            switch (e) {
                case KEY_LEFT:
                    shooter.moveLeft();
                    break;
                case KEY_RIGHT:
                    shooter.moveRight();
                    break;
                case KEY_SPACE:
                    if (shooter.canFireBullets())
                        shooter.getWeapons().add(new Bullet(shooter.x, shooter.y));
                    break;
                case KEY_L:
                    if (shooter.canFireLaser()) {
                        // shooter.setLaserCount(shooter.getLaserCount()-1);
                        Laser laser = new Laser(shooter.x, 1);
                        shooter.getLaserGun().add(laser);
                        laser.setFireStrategy(new LaserFireIntoSpace(laser));
                        laser.setRenderStrategy(new LaserRenderFireIntoSpace(laser));
                    }
                    break;
                }
        }

        if (frameCounter == BOMB_DROP_FREQ) {
            gameBoard.getEnemyComposite().dropBombs();
            frameCounter = 0;
        }
    }

    private void processCollision() {
        var shooter = gameBoard.getShooter();
        var enemyComposite = gameBoard.getEnemyComposite();
        shooter.removeOutOfBound();
        enemyComposite.removeBombsOutOfBound();
        enemyComposite.processCollision(shooter);
        
    }


    private void update() {
        for (var e: gameBoard.getCanvas().getGameElements()) {
            e.animate();
        }
    }

    public LinkedList<EVENT_TYPE> getEventQueue() {
        return eventQueue;
    }

}