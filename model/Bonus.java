package model;

import java.awt.Graphics2D;
import java.awt.*;

public class Bonus extends GameElement {

    public static final int WIDTH = 7;
    public static final int UNIT_MOVE = 1;

    public Bonus(int x, int y) {
        super(x, y, Color.blue, true, WIDTH, WIDTH*2);
    }



    @Override
    public void render(Graphics2D g2) {
        
        g2.setColor(color);
        
        if (filled) {
            g2.fillOval(x, y, width, height);
        }
        else {
            g2.drawOval(x, y, width, height);
        }

    }

    @Override
    public void animate() {
         
        super.y += UNIT_MOVE;
    }
    
}