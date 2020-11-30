package model;

import java.awt.*;

public class Enemy extends GameElement {

    private int health;

    public Enemy(int x, int y, int width, int height, int health) {
        super(x, y, width, height);
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public void render(Graphics2D g2) {

        g2.drawImage(getImage(), null, x, y);
    }

    @Override
	public void animate() {
        // composite group of enemies
        
    }
    
}