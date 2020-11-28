package model.observerPattern;

import java.awt.Color;

import controller.TimerListener;
import view.GameBoard;
import view.GameOverDraw;
import view.TextDraw;

public class EnemyObserver implements Observer {

    private GameBoard gameBoard;

    public EnemyObserver(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }


    @Override
    public void enemiesGotShot() {
        // TODO Auto-generated method stub
        var enemyComposite = gameBoard.getEnemyComposite();
        // set score
        int score;
        int enemiesKilled;
        enemiesKilled = enemyComposite.enemiesKilled;
        score = enemiesKilled * TimerListener.SCORE_UP;
        gameBoard.setScore(score);
    }

    @Override
    public void enemiesReachBottom() {
        // TODO Auto-generated method stub
        gameBoard.setGameOver(true);
        // gameBoard.getCanvas().getGameElements().clear();
        gameBoard.getCanvas().getGameElements().add(new GameOverDraw(0, 0, gameBoard.getCanvas().getWidth(), gameBoard.getCanvas().getHeight(), new Color(255, 51, 51, 96)));
        gameBoard.getCanvas().getGameElements().add(new TextDraw("YOU LOST!", gameBoard.getCanvas().getWidth()/2 - 120, gameBoard.getCanvas().getHeight()/2 - 70, Color.RED, 50));
        showScore();
    }

    @Override
    public void enemiesAllGone() {
        // TODO Auto-generated method stub
        gameBoard.setGameOver(true);
        // gameBoard.getCanvas().getGameElements().clear();
        gameBoard.getCanvas().getGameElements().add(new GameOverDraw(0, 0, gameBoard.getCanvas().getWidth(), gameBoard.getCanvas().getHeight(), new Color(0, 255, 250, 96)));
        gameBoard.getCanvas().getGameElements().add(new TextDraw("YOU WON!", gameBoard.getCanvas().getWidth()/2 - 120, gameBoard.getCanvas().getHeight()/2 - 70, Color.GREEN, 50));
        showScore();
    }

    @Override
    public void enemiesDestroyedComponents() {
        // TODO Auto-generated method stub
        gameBoard.setGameOver(true);
        // gameBoard.getCanvas().getGameElements().clear();
        gameBoard.getCanvas().getGameElements().add(new GameOverDraw(0, 0, gameBoard.getCanvas().getWidth(), gameBoard.getCanvas().getHeight(), new Color(255, 51, 51, 96)));
        gameBoard.getCanvas().getGameElements().add(new TextDraw("YOU LOST!", gameBoard.getCanvas().getWidth()/2 - 120, gameBoard.getCanvas().getHeight()/2 - 70, Color.RED, 50));
        showScore();
    }

    private void showScore() {
        gameBoard.getCanvas().getGameElements().add(new TextDraw("Your Score:", gameBoard.getCanvas().getWidth()/2 - 100, gameBoard.getCanvas().getHeight()/2, Color.WHITE, 30));
        gameBoard.getCanvas().getGameElements().add(new TextDraw(""+gameBoard.getScore(), gameBoard.getCanvas().getWidth()/2 - 50, gameBoard.getCanvas().getHeight()/2 + 50, Color.WHITE, 50));
    }
    
}