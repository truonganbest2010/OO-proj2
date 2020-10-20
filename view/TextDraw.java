package view;

import java.awt.*;

import model.GameElement;

public class TextDraw extends GameElement {

    private String text;
    private int size;

    public TextDraw(String text, int x, int y, Color color, int size) {
        super(x, y, color, false, 0, 0);
        this.text = text;
        this.size = size;
    }

    @Override
    public void render(Graphics2D g2) {
        // TODO Auto-generated method stub
        g2.setColor(color);
        g2.setFont(new Font("Courier", Font.BOLD, size));
        g2.drawString(text, x, y);
    }

    @Override
    public void animate() {
        // TODO Auto-generated method stub

    }
    
}