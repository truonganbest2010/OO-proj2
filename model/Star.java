package model;

import java.awt.Graphics2D;
import java.util.Random;
import java.awt.*;

public class Star extends GameElement {

    public static final int UNIT_MOVE = 2;

    public Star(int x, int y, int width, int height) {
        super(x, y, width, height);
        
        Random r = new Random();
        int rand = r.nextInt(3);
        if (rand == 0) {
            color = Color.GRAY;
        } else if (rand == 1) {
            color = Color.DARK_GRAY;
        } else 
            color = Color.LIGHT_GRAY;

    }

    @Override
    public void render(Graphics2D g2) {

        g2.setColor(color);
        g2.fillOval(x, y, width, height);
    
    }

    @Override
    public void animate() { }
    
}