package model;

import java.awt.*;

public class ShooterElement extends GameElement {

    public static final int SIZE = 20;

    public ShooterElement(int x, int y, Color color, boolean filled) {
        super(x, y, color, filled, SIZE, SIZE);
    }

    @Override
    public void render(Graphics2D g2) {
        // TODO Auto-generated method stub
        // g2.setColor(color);
        // if (super.filled) {
        //     g2.fillRect(x, y, width, height);
        // }
        // else {
        //     g2.drawRect(x, y, width, height);
        // }
        g2.drawImage(getImage(), null, x, y);

    }

    @Override
    public void animate() { }
    
}