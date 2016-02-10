package com.nahroto.fruitdestroyer.huds.upgradeoverlays;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.utils.Align;
import com.nahroto.fruitdestroyer.entities.Bullet;
import com.nahroto.fruitdestroyer.entities.Player;
import com.nahroto.fruitdestroyer.huds.BuyHud;

public class ExtraAccuracyOverlay extends UpgradeOverlay
{
    public static final float UPGRADE_STEP = 0.5f;

    public static Float currentValue;
    public static Float nextValue;

    public ExtraAccuracyOverlay(ImageButton button, TextureAtlas gameScreenAtlas, BuyHud buyHud, Player player)
    {
        super("Reduces your weapon recoil.\nIncrease this stat for\nimproved shooting accuracy!", 1, button, gameScreenAtlas, buyHud, player);

        currentValue = new Float(10 / Bullet.START_RECOIL);
        nextValue = new Float(currentValue + UPGRADE_STEP);

        currentValueLabel.setText(currentValue.toString());
        nextValueLabel.setText(nextValue.toString());
        setTitleText("ACCURACY");
    }

    @Override
    protected void upgrade(Player player)
    {
        currentValue = nextValue;
        nextValue += UPGRADE_STEP;
        currentValueLabel.setText(currentValue.toString());
        nextValueLabel.setText(nextValue.toString());

        Bullet.getWeapon().setRecoil(10 / currentValue);
    }

    @Override
    public void reset()
    {
        currentValue = (10 / Bullet.START_RECOIL);
        nextValue = currentValue + UPGRADE_STEP;
        currentValueLabel.setText(currentValue.toString());
        nextValueLabel.setText(nextValue.toString());
    }

    @Override
    public void setPosition(float x, float y, int align)
    {
        super.setPosition(x, y, align);
        title.setPosition(background.getX(Align.center) - (titleFont.getWidth("ACCURACY") / 2), background.getY(Align.top) - 160);
        currentValueLabel.setPosition(background.getX() + 125 - (currentValueFont.getWidth(currentValue.toString()) / 2), background.getY() + 370);
        nextValueLabel.setPosition(background.getX() + 475 - (nextValueFont.getWidth(nextValue.toString()) / 2), background.getY() + 370);
    }
}
