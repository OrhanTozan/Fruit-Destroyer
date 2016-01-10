package com.nahroto.fruitdestroyer.huds.upgradeoverlays;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.nahroto.fruitdestroyer.entities.Bullet;
import com.nahroto.fruitdestroyer.huds.BuyHud;

public class ExtraAmmoOverlay extends UpgradeOverlay
{
    private static final int UPGRADE_STEP = 10;

    private Integer currentValue;
    private Integer nextValue;

    public ExtraAmmoOverlay(ImageButton button, TextureAtlas gameScreenAtlas, BuyHud buyHud)
    {
        super("The magsize of your weapon.\nThe higher, the more bullets you can fire before the need to reload!\nUpgrade cost: 1 point", 1, button, gameScreenAtlas, buyHud);

        currentValue = new Integer(Bullet.START_MAGSIZE);
        nextValue = new Integer(currentValue + UPGRADE_STEP);

        currentValueLabel.setText(currentValue.toString());
        nextValueLabel.setText(nextValue.toString());
    }

    @Override
    protected void upgrade()
    {
        currentValue = nextValue;
        nextValue += UPGRADE_STEP;
        currentValueLabel.setText(currentValue.toString());
        nextValueLabel.setText(nextValue.toString());
    }
}
