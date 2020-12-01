package controller;

import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;

import model.Bullet;
import model.*;
import model.Shooter;
import model.bulletStrategyPattern.*;
import view.GameBoard;
import view.TextDraw;
import model.images.ImageStore;


public class TimerListener implements ActionListener {

    public static final int SCORE_UP = 10;

    public enum EVENT_TYPE {
        KEY_RIGHT, KEY_LEFT, KEY_SPACE, KEY_L
    }

    private GameBoard gameBoard;
    private LinkedList<EVENT_TYPE> eventQueue;

    private final int BOMB_DROP_FREQ = 50;
    private final int BONUS_DROP_FREQ = 150;
    private final int STAR_FALL_FREQ = 2;

    private int bomb_frameCounter = 0;
    private int bonus_frameCounter = 0;
    private int star_frameCounter = 0;

    public TimerListener(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        eventQueue = new LinkedList<>();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ++bomb_frameCounter;
        ++bonus_frameCounter;
        ++star_frameCounter;
        
        if (!gameBoard.isGameOver()) {
            update();
            processEventQueue();
            processCollision();
    
        } else {
            update();
            gameBoard.getStartBtn().setText("New Game");
            gameBoard.getPauseBtn().setEnabled(false);
            gameBoard.getStartBtn().setEnabled(true);
            bomb_frameCounter = 0;
            bonus_frameCounter = 0;
            if (gameBoard.getEnemyComposite().getText().size() < 1) {
                gameBoard.getEnemyComposite().getText().add(new TextDraw("Click <New Game> to Start", GameBoard.WIDTH/3 -50, 0, Color.white, 30));
            }
            // gameBoard.getTimer().stop();
        }

        starDrop();
        removeObjectOutOfBound();
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
            gameBoard.getBonusDropper().dropBonus();
            bonus_frameCounter = 0;
        }
        
    }

    private void starDrop() {
        if (star_frameCounter == STAR_FALL_FREQ) {
            gameBoard.getStarDrop().dropStars();
            star_frameCounter = 0;
        }
    }

    private void processCollision() {
        var shooter = gameBoard.getShooter();
        var enemyComposite = gameBoard.getEnemyComposite();
        var bonusDropper = gameBoard.getBonusDropper();
        enemyComposite.processCollisionWithEnemy(shooter);
        bonusDropper.processCollisionWithBonus(shooter);
    }


    private void removeObjectOutOfBound() {
        gameBoard.getShooter().removeOutOfUpperBound();
        gameBoard.getEnemyComposite().removeOutOfLowerBound();
        gameBoard.getBonusDropper().removeBonusOutOfLowerBound();
        gameBoard.getStarDrop().removeStarsOutOfBound();
        gameBoard.getCanvas().removeObjectOutOfLowerBound();
    }


    private void update() {
        for (var e: gameBoard.getCanvas().getGameElements()) {
            e.animate();
        }
    }

    public LinkedList<EVENT_TYPE> getEventQueue() {
        return eventQueue;
    }
    
    public void setBonus_frameCounter(int bonus_frameCounter) {
        this.bonus_frameCounter = bonus_frameCounter;
    }

}