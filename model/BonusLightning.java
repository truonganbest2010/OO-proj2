package model;

import java.awt.Graphics2D;

import model.images.ImageStore;

import java.awt.*;

public class BonusLightning extends GameElement {

    public static final int WIDTH = 25;
    public static final int UNIT_MOVE = 3;

    public BonusLightning(int x, int y) {
        super(x, y, WIDTH, WIDTH);
    }


    @Override
    public void render(Graphics2D g2) {
        
        // g2.setColor(Color.BLUE);
        // g2.fillOval(x, y, width, height);
        if (y%2 == 0)
            g2.drawImage(ImageStore.bonus, null, x, y);
             
    }

    @Override
    public void animate() {
        super.y += UNIT_MOVE;
    }
    
}