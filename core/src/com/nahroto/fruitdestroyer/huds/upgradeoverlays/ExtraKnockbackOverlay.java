package com.nahroto.fruitdestroyer.huds.upgradeoverlays;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.utils.Align;
import com.nahroto.fruitdestroyer.entities.Bullet;
import com.nahroto.fruitdestroyer.entities.Player;
import com.nahroto.fruitdestroyer.huds.BuyHud;

public class ExtraKnockbackOverlay extends UpgradeOverlay
{
    public static final int UPGRADE_STEP = 50;

    public static Integer currentValue;
    public static Integer nextValue;

    public ExtraKnockbackOverlay(ImageButton button, TextureAtlas gameScreenAtlas, BuyHud buyHud, Player player)
    {
        super("The knockbackpower of your\nbullets. The higher, the farther\nthe enemies will be knocked\nback when hit!", 1, button, gameScreenAtlas, buyHud, player);

        currentValue = new Integer(Bullet.START_KNOCKBACKPOWER);
        nextValue = new Integer(currentValue + UPGRADE_STEP);

        currentValueLabel.setText(currentValue.toString());
        nextValueLabel.setText(nextValue.toString());
        setTitleText("KNOCKBACK POWER");
    }

    @Override
    protected void upgrade(Player player)
    {
        currentValue = nextValue;
        nextValue += UPGRADE_STEP;
        currentValueLabel.setText(currentValue.toString());
        nextValueLabel.setText(nextValue.toString());

        Bullet.getWeapon().setKnockbackPower(currentValue);
    }

    @Override
    public void reset()
    {
        currentValue = Bullet.START_KNOCKBACKPOWER;
        nextValue = currentValue + UPGRADE_STEP;
        currentValueLabel.setText(currentValue.toString());
        nextValueLabel.setText(nextValue.toString());
    }

    @Override
    public void setPosition(float x, float y, int align)
    {
        super.setPosition(x, y, align);
        title.setPosition(background.getX(Align.center) - (titleFont.getWidth("KNOCKBACK POWER") / 2), background.getY(Align.top) - 160);
        currentValueLabel.setPosition(background.getX() + 125 - (currentValueFont.getWidth(currentValue.toString()) / 2), background.getY() + 370);
        nextValueLabel.setPosition(background.getX() + 475 - (nextValueFont.getWidth(nextValue.toString()) / 2), background.getY() + 370);
    }
}
