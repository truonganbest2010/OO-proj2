package controller;

import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;

import model.Bullet;
import model.*;
import model.Shooter;
import model.bulletStrategyPattern.*;
import view.GameBoard;
import model.images.ImageStore;


public class TimerListener implements ActionListener {

    public static final int SCORE_UP = 10;

    public enum EVENT_TYPE {
        KEY_RIGHT, KEY_LEFT, KEY_SPACE, KEY_L
    }

    private GameBoard gameBoard;
    private LinkedList<EVENT_TYPE> eventQueue;

    private final int BOMB_DROP_FREQ = 50;
    private final int BONUS_DROP_FREQ = 100;

    
    private int bomb_frameCounter = 0;
    private int bonus_frameCounter = 0;

    public TimerListener(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        eventQueue = new LinkedList<>();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ++bomb_frameCounter;
        ++bonus_frameCounter;
        // System.out.println(frameCounter);
        if (!gameBoard.isGameOver()) {
            update();
            processEventQueue();
            processCollision();
    
        } else {
            update();
            gameBoard.getStartBtn().setText("New Game");
            gameBoard.getPauseBtn().setEnabled(false);
            gameBoard.getStartBtn().setEnabled(true);
            // gameBoard.getTimer().stop();
            bomb_frameCounter = 0;
            bonus_frameCounter = 0;

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
                    if (shooter.canFireBullets()) {
                        Bullet bullet = new Bullet(shooter.x, shooter.y);
                        bullet.setImage(ImageStore.bullet);
                        bullet.setMoveStrategy(new BulletMoveForwardStrategy(bullet));
                        bullet.setRenderStrategy(new BulletRenderMoveForwardStrategy(bullet));
                        shooter.getWeapons().add(bullet);
                    }
                    break;
                case KEY_L:
                    if (shooter.canFireLightning()) {
                        shooter.setlightningShoot(shooter.getLightningShoot()-1); // decrease lightning shoots
                        Lightning lightning = new Lightning(shooter.x, 1);
                        lightning.setImage(ImageStore.lightning);
                        shooter.getLightningGun().add(lightning);
                    }
                    break;
                }
        }

        if (bomb_frameCounter == BOMB_DROP_FREQ) {
            gameBoard.getEnemyComposite().dropBombs();
            bomb_frameCounter = 0;
        }
        if (bonus_frameCounter == BONUS_DROP_FREQ) {
            gameBoard.getEnemyComposite().dropBonus();
            bonus_frameCounter = 0;
        }
        
    }

    private void processCollision() {
        var shooter = gameBoard.getShooter();
        var enemyComposite = gameBoard.getEnemyComposite();
        shooter.removeOutOfUpperBound();
        enemyComposite.removeOutOfLowerBound();
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