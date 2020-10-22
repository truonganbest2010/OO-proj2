package model;

import java.awt.*;

public class BottomLine extends GameElement {

    public BottomLine(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void render(Graphics2D g2) { 
        // g2.setColor(Color.white);
        // g2.drawRect(x, y, width, height);
    }

    @Override
    public void animate() { }
    
}