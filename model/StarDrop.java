package model;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import view.GameBoard;

public class StarDrop extends GameElement {

    public static final int UNIT_MOVE = 2;
    public static final int ASTEROID_SIZE_RANGE = 5;
    private ArrayList<GameElement> starsBackground;

    public StarDrop() {
        starsBackground = new ArrayList<>();

        for (int i = 1; i < GameBoard.HEIGHT; i+= 2) {
            Random r = new Random();
            int size = r.nextInt(ASTEROID_SIZE_RANGE);
            starsBackground.add(new Star(r.nextInt(GameBoard.WIDTH-1), i, size, size));
        }
    }

    @Override
    public void render(Graphics2D g2) {
        // TODO Auto-generated method stub
        for (var s: starsBackground) {
            s.render(g2);
        }
    }

    @Override
    public void animate() {
        // TODO Auto-generated method stub
        for (var s: starsBackground) {
            s.y += UNIT_MOVE;
        }
    }
    
    public void dropStars() {
        Random x = new Random();
        Random r = new Random();
        int size = r.nextInt(ASTEROID_SIZE_RANGE);
        starsBackground.add(new Star(x.nextInt(GameBoard.WIDTH-1), 0, size, size));
    }

    public void removeStarsOutOfBound() {
        var remove = new ArrayList<GameElement>();
        for (var s: starsBackground) {
            if (s.y > GameBoard.HEIGHT) {
                remove.add(s);
            }
        }
        starsBackground.removeAll(remove);
    }
}