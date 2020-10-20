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
        // TODO Auto-generated method stub
        int keyCode = e.getKeyCode();
        // var eventQueue = ;

        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                // System.out.println("l");
                gameBoard.getTimerListener().getEventQueue().add(TimerListener.EVENT_TYPE.KEY_LEFT);
                break;
            case KeyEvent.VK_RIGHT:
                // System.out.println("r");
                gameBoard.getTimerListener().getEventQueue().add(TimerListener.EVENT_TYPE.KEY_RIGHT);
                break;
            case KeyEvent.VK_SPACE:
                // System.out.println("s");
                gameBoard.getTimerListener().getEventQueue().add(TimerListener.EVENT_TYPE.KEY_SPACE);
                break;
        }

        // for (var c: eventQueue) {
        //     System.out.println(c);
        // }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }
    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }
}