package com.nahroto.fruitdestroyer.huds.upgradeoverlays;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.nahroto.fruitdestroyer.entities.Bullet;
import com.nahroto.fruitdestroyer.huds.BuyHud;

public class ExtraAccuracyOverlay extends UpgradeOverlay
{
    private static final float UPGRADE_STEP = 1f;

    private Float currentValue;
    private Float nextValue;

    public ExtraAccuracyOverlay(ImageButton button, TextureAtlas gameScreenAtlas, BuyHud buyHud)
    {
        super("Your shooting accuracy. The higher, the more accurate!\nUpgrade cost: 1 point", 1, button, gameScreenAtlas, buyHud);

        currentValue = new Float(1 / Bullet.START_RECOIL);
        nextValue = new Float(currentValue + UPGRADE_STEP);

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
