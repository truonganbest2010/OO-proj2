package view;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

import model.GameElement;

public class MyCanvas extends JPanel {
    
    private GameBoard gameBoard;
    private ArrayList<GameElement> gameElements = new ArrayList<>();

    public MyCanvas(GameBoard gameBoard, int width, int height) {
        this.gameBoard = gameBoard;
        setBackground(Color.black);
        setPreferredSize(new Dimension(width, height));
        
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        for (var e: gameElements) {
            e.render(g2);
        }
    }

    public ArrayList<GameElement> getGameElements() {
        return gameElements;
    }

    public void removeObjectOutOfLowerBound() {
        var remove = new ArrayList<>();
        for (var e: gameElements) {
            if (e.y > GameBoard.HEIGHT+50) {
                remove.add(e);
            }
        }
        gameElements.removeAll(remove);

        // System.out.println(gameElements.size());
    }


    
}