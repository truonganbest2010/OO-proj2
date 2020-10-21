package view;

import java.awt.*;

import model.GameElement;

public class GameOverDraw extends GameElement {

    private int dx;
    private int dy;

    public GameOverDraw(int x, int y, int dx, int dy, Color color){
        super(x, y, color, false, 0, 0);
        this.dx = dx;
        this.dy = dy;
    }


    @Override
    public void render(Graphics2D g2) {
        g2.setColor(color);
        g2.fillRect(x, y, dx, dy);
    }

    @Override
    public void animate() { }
    
}