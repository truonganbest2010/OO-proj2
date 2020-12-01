package model.enemyCompositeObserverPattern;

import java.awt.Color;

import controller.TimerListener;
import view.GameBoard;
import view.GameOverDraw;
import view.TextDraw;

public class EnemyCompositeObserver implements Observer {

    private GameBoard gameBoard;

    public EnemyCompositeObserver(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }


    @Override
    public void enemiesGotShot() {
        // set score
        gameBoard.setScore(gameBoard.getScore()+TimerListener.SCORE_UP);
    }

    @Override
    public void enemiesReachBottom() {
        // TODO Auto-generated method stub
        gameBoard.setGameOver(true);
        // gameBoard.getCanvas().getGameElements().clear();
        gameBoard.getCanvas().getGameElements().add(new GameOverDraw(0, 0, gameBoard.getCanvas().getWidth(), gameBoard.getCanvas().getHeight(), new Color(255, 51, 51, 96)));
        gameBoard.getEnemyComposite().getText().add(new TextDraw("YOU LOST!", gameBoard.getCanvas().getWidth()/2 - 120, -200, Color.RED, 50)); // gameBoard.getCanvas().getHeight()/2 - 70
        showScore();
    }

    @Override
    public void enemiesAllGone() {
        // TODO Auto-generated method stub
        gameBoard.setGameOver(true);
        // gameBoard.getCanvas().getGameElements().clear();
        gameBoard.getCanvas().getGameElements().add(new GameOverDraw(0, 0, gameBoard.getCanvas().getWidth(), gameBoard.getCanvas().getHeight(), new Color(0, 255, 250, 96)));
        gameBoard.getEnemyComposite().getText().add(new TextDraw("YOU WON!", gameBoard.getCanvas().getWidth()/2 - 120, -200, Color.GREEN, 50)); // gameBoard.getCanvas().getHeight()/2 - 70
        showScore();
    }

    @Override
    public void enemiesDestroyedShooter() {
        // TODO Auto-generated method stub
        gameBoard.setGameOver(true);
        // gameBoard.getCanvas().getGameElements().clear();
        gameBoard.getCanvas().getGameElements().add(new GameOverDraw(0, 0, gameBoard.getCanvas().getWidth(), gameBoard.getCanvas().getHeight(), new Color(255, 51, 51, 96)));
        gameBoard.getEnemyComposite().getText().add(new TextDraw("YOU LOST!", gameBoard.getCanvas().getWidth()/2 - 120, -200, Color.RED, 50)); // gameBoard.getCanvas().getHeight()/2 - 70
        showScore();
    }

    private void showScore() {
        gameBoard.getEnemyComposite().getText().add(new TextDraw("Your Score:", gameBoard.getCanvas().getWidth()/2 - 100, -130, Color.WHITE, 30));
        gameBoard.getEnemyComposite().getText().add(new TextDraw(""+gameBoard.getScore(), gameBoard.getCanvas().getWidth()/2 - 50,  -80, Color.WHITE, 50)); // gameBoard.getCanvas().getHeight()/2 - 70
    }
    
}