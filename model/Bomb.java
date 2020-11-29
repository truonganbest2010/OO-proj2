package model;

import java.awt.*;

import model.images.ImageStore;

public class Bomb extends GameElement {

    public static final int SIZE = 10;
    public static final int UNIT_MOVE = 5;

    public Bomb(int x, int y) {
        super(x, y, Color.green, true, SIZE, SIZE*2);
    }


    @Override
    public void render(Graphics2D g2) {
        // TODO Auto-generated method stub
        // g2.setColor(color);
        // if (filled) {
        //     g2.fillOval(x, y, width, height);
        // }
        // else  {
        //     g2.drawOval(x, y, width, height);
        // }
        g2.drawImage(ImageStore.bomb, null, x, y);
    }

    @Override
    public void animate() {
        // TODO Auto-generated method stub
        super.y += UNIT_MOVE;
    }
    
}