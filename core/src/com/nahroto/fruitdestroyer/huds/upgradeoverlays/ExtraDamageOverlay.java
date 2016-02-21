package com.nahroto.fruitdestroyer.huds.upgradeoverlays;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.utils.Align;
import com.nahroto.fruitdestroyer.Application;
import com.nahroto.fruitdestroyer.entities.Bullet;
import com.nahroto.fruitdestroyer.entities.Player;
import com.nahroto.fruitdestroyer.huds.BuyHud;

public class ExtraDamageOverlay extends UpgradeOverlay
{
    public static final int UPGRADE_STEP = 5;

    public static Integer currentValue;
    public static Integer nextValue;

    public ExtraDamageOverlay(final Application APP, ImageButton button, TextureAtlas gameScreenAtlas, BuyHud buyHud, Player player)
    {
        super(APP, "Damage of your bullets!\nUpgrade this so your bullets will\nhurt the fruit more!", 20, button, gameScreenAtlas, buyHud, player);

        currentValue = new Integer(Bullet.START_DAMAGE);
        nextValue = new Integer(Bullet.START_DAMAGE + UPGRADE_STEP);

        currentValueLabel.setText(damageToText(currentValue));
        nextValueLabel.setText(damageToText(nextValue));
        setTitleText("DAMAGE");
    }

    @Override
    protected void upgrade(Player player)
    {
        currentValue = nextValue;
        nextValue += UPGRADE_STEP;
        currentValueLabel.setText(damageToText(currentValue));
        nextValueLabel.setText(damageToText(nextValue));

        Bullet.getWeapon().setDamage(currentValue);

        if (currentValue == 20)
            maxValue = true;
    }

    public String damageToText(int damage)
    {
        if (damage > 20)
            return "N/A";
        return damage + "0%";
    }

    @Override
    public void reset()
    {
        currentValue = new Integer(Bullet.START_DAMAGE);
        nextValue = new Integer(Bullet.START_DAMAGE + UPGRADE_STEP);

        currentValueLabel.setText(damageToText(currentValue));
        nextValueLabel.setText(damageToText(nextValue));
        maxValue = false;
    }

    @Override
    public void setPosition(float x, float y, int align)
    {
        super.setPosition(x, y, align);
        title.setPosition(background.getX(Align.center) - (titleFont.getWidth("DAMAGE") / 2), background.getY(Align.top) - 160);
        currentValueLabel.setPosition(background.getX() + 125 - (currentValueFont.getWidth(damageToText(currentValue)) / 2), background.getY() + 370);
        nextValueLabel.setPosition(background.getX() + 475 - (nextValueFont.getWidth(damageToText(nextValue)) / 2), background.getY() + 370);
    }
}
