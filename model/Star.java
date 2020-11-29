package model;

import java.awt.Graphics2D;
import java.awt.*;

public class Star extends GameElement {

    public static final int UNIT_MOVE = 2;

    public Star(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void render(Graphics2D g2) {
        // TODO Auto-generated method stub
        g2.setColor(Color.white);
        
        g2.fillOval(x, y, width, height);
        
    }

    @Override
    public void animate() {
        // TODO Auto-generated method stub
        
    }
    
}