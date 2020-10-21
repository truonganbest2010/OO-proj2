package controller;

import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;

import model.Bullet;
import model.Shooter;
import view.GameBoard;
import view.GameOverDraw;
import view.TextDraw;


public class TimerListener implements ActionListener {

    private static final int SCORE_UP = 10;

    public enum EVENT_TYPE {
        KEY_RIGHT, KEY_LEFT, KEY_SPACE
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
        update();
        processEventQueue();
        processCollision();
        setScore();
        checkGameOver();
        gameBoard.getCanvas().repaint();
    }

    private void processEventQueue() {
        while (!eventQueue.isEmpty()) {
            var e = eventQueue.getFirst();
            eventQueue.removeFirst();
            Shooter shooter = gameBoard.getShooter();
            if (shooter == null) return;
            
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
        shooter.removeBulletsOutOfBound();
        enemyComposite.removeBombsOutOfBound();
        enemyComposite.processCollision(shooter);
        
    }

    private void setScore() {
        var shooter = gameBoard.getShooter();
        var enemyComposite = gameBoard.getEnemyComposite();
        // set score
        int score;
        int enemiesKilled;
        score = gameBoard.getScore();
        enemiesKilled = enemyComposite.enemiesKilled;
        score = enemiesKilled * SCORE_UP;
        gameBoard.setScore(score);
        gameBoard.getScoreLabel().setText("" + score);
    }

    private void checkGameOver() {
        var shooter = gameBoard.getShooter();
        var enemyComposite = gameBoard.getEnemyComposite();

        if (enemyComposite.gameOver) {
            gameBoard.getTimer().stop();
            gameBoard.getCanvas().getGameElements().clear();
            gameBoard.getCanvas().getGameElements().add(new GameOverDraw(0, 0, gameBoard.getCanvas().getWidth(), gameBoard.getCanvas().getHeight(), new Color(0, 200, 255, 96)));
            gameBoard.getCanvas().getGameElements().add(new TextDraw("Your Score:", gameBoard.getCanvas().getWidth()/2 - 100, gameBoard.getCanvas().getHeight()/2 - 50, Color.red, 30));
            gameBoard.getCanvas().getGameElements().add(new TextDraw(""+gameBoard.getScore(), gameBoard.getCanvas().getWidth()/2 - 50, gameBoard.getCanvas().getHeight()/2, Color.red, 50));
        }

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