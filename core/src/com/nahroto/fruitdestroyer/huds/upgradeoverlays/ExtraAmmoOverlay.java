package com.nahroto.fruitdestroyer.huds.upgradeoverlays;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.utils.Align;
import com.nahroto.fruitdestroyer.Application;
import com.nahroto.fruitdestroyer.entities.Bullet;
import com.nahroto.fruitdestroyer.entities.Player;
import com.nahroto.fruitdestroyer.huds.BuyHud;

public class ExtraAmmoOverlay extends UpgradeOverlay
{
    public static final int UPGRADE_STEP = 5;

    public static Integer currentValue;
    public static Integer nextValue;

    public ExtraAmmoOverlay(final Application APP, ImageButton button, TextureAtlas gameScreenAtlas, BuyHud buyHud, Player player)
    {
        super(APP, "The magsize of your weapon.\nUpgrade is useful when raided\nby big groups of enemies!", 3, button, gameScreenAtlas, buyHud, player);

        currentValue = new Integer(Bullet.START_MAGSIZE);
        nextValue = new Integer(currentValue + UPGRADE_STEP);

        currentValueLabel.setText(currentValue.toString());
        nextValueLabel.setText(nextValue.toString());
        setTitleText("AMMO");
    }

    @Override
    protected void upgrade(Player player)
    {
        currentValue = nextValue;
        nextValue += UPGRADE_STEP;
        currentValueLabel.setText(currentValue.toString());
        nextValueLabel.setText(nextValue.toString());

        Bullet.getWeapon().setMagSize(currentValue);
    }

    @Override
    public void reset()
    {
        currentValue = Bullet.START_MAGSIZE;
        nextValue = currentValue + UPGRADE_STEP;
        currentValueLabel.setText(currentValue.toString());
        nextValueLabel.setText(nextValue.toString());
    }

    @Override
    public void setPosition(float x, float y, int align)
    {
        super.setPosition(x, y, align);
        title.setPosition(background.getX(Align.center) - (titleFont.getWidth("AMMO") / 2), background.getY(Align.top) - 160);
        currentValueLabel.setPosition(background.getX() + 125 - (currentValueFont.getWidth(currentValue.toString()) / 2), background.getY() + 370);
        nextValueLabel.setPosition(background.getX() + 475 - (nextValueFont.getWidth(nextValue.toString()) / 2), background.getY() + 370);
    }
}
