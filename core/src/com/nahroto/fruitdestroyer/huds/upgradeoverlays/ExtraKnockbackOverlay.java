package com.nahroto.fruitdestroyer.huds.upgradeoverlays;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.utils.Align;
import com.nahroto.fruitdestroyer.entities.Bullet;
import com.nahroto.fruitdestroyer.huds.BuyHud;

public class ExtraKnockbackOverlay extends UpgradeOverlay
{
    private static final int UPGRADE_STEP = 10;

    private Integer currentValue;
    private Integer nextValue;

    public ExtraKnockbackOverlay(ImageButton button, TextureAtlas gameScreenAtlas, BuyHud buyHud)
    {
        super("The knockbackpower of your\nbullets. The higher, the farther\nthe enemies will be knocked\nback when hit!", 1, button, gameScreenAtlas, buyHud);

        currentValue = new Integer(Bullet.START_KNOCKBACKPOWER);
        nextValue = new Integer(currentValue + UPGRADE_STEP);

        currentValueLabel.setText(currentValue.toString());
        nextValueLabel.setText(nextValue.toString());
        setTitleText("KNOCKBACK POWER");
    }

    @Override
    protected void upgrade()
    {
        currentValue = nextValue;
        nextValue += UPGRADE_STEP;
        currentValueLabel.setText(currentValue.toString());
        nextValueLabel.setText(nextValue.toString());
    }

    @Override
    public void setPosition(float x, float y, int align)
    {
        super.setPosition(x, y, align);
        title.setPosition(background.getX(Align.center) - (titleFont.getWidth("KNOCKBACK POWER") / 2), background.getY(Align.top) - 160);
    }
}
