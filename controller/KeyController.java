package controller;

import java.awt.event.*;

import view.GameBoard;

public class KeyController implements KeyListener {

    private GameBoard gameBoard;

    public KeyController(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                gameBoard.getTimerListener().getEventQueue().add(TimerListener.EVENT_TYPE.KEY_LEFT);
                break;
            case KeyEvent.VK_RIGHT:
                gameBoard.getTimerListener().getEventQueue().add(TimerListener.EVENT_TYPE.KEY_RIGHT);
                break;
            case KeyEvent.VK_SPACE:
                gameBoard.getTimerListener().getEventQueue().add(TimerListener.EVENT_TYPE.KEY_SPACE);
                break;
            case KeyEvent.VK_L:
                gameBoard.getTimerListener().getEventQueue().add(TimerListener.EVENT_TYPE.KEY_L);
        }

    }

    @Override
    public void keyReleased(KeyEvent e) { }

    @Override
    public void keyTyped(KeyEvent e) { }
}