package model.bonusStrategyPattern;

import java.awt.*;
import java.awt.Graphics2D;

import model.BonusDropper;
import model.images.ImageStore;

public class BonusBulletRenderAfterHitShooterStrategy implements BonusRenderStrategy {

    private BonusDropper bonusDropper;

    public BonusBulletRenderAfterHitShooterStrategy (BonusDropper bonusDropper) {
        this.bonusDropper = bonusDropper;
    }


    @Override
    public void renderAlgorithm(Graphics2D g2) {
        // TODO Auto-generated method stub
        g2.setColor(Color.white);
        g2.setFont(new Font("Courier", Font.BOLD, 10));

        for (var bn : bonusDropper.getBonusDropper()) {
            g2.drawImage(ImageStore.bullet_icon, null, bn.x, bn.y);
            g2.drawString("+" + BonusDropper.BONUS_BULLET, bn.x + 20, bn.y + 5);
        }
    }
}