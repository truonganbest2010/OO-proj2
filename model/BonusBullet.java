package model;

import java.awt.Graphics2D;

import model.images.ImageStore;

public class BonusBullet extends GameElement {

    public static final int WIDTH = 30;

    public BonusBullet(int x, int y) {
        super(x, y, WIDTH, WIDTH);

    }


    @Override
    public void render(Graphics2D g2) {
        
        if (y%2 == 0)
            g2.drawImage(ImageStore.bonus_bullet, null, x, y);
    }

    @Override
    public void animate() {
        // bonus dropper

    }
    
}