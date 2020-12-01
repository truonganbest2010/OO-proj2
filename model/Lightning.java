package model;

import java.awt.Color;
import java.awt.Graphics2D;

import view.GameBoard;

public class Lightning extends GameElement {

    public static final int WIDTH = EnemyComposite.ENEMY_SIZE;
    public static final int UNIT_MOVE = 1;

    public Lightning(int x, int y) {
        super(x, y, Color.white, true, WIDTH, GameBoard.HEIGHT-ShooterElement.SIZE);
    
    }


    @Override
    public void render(Graphics2D g2) {
        
        g2.drawImage(getImage(), null, x, y);
        
    }

    @Override
    public void animate() {
        
        this.y -= UNIT_MOVE;
        
    }
    
}