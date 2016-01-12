package com.nahroto.fruitdestroyer.huds.upgradeoverlays;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.utils.Align;
import com.nahroto.fruitdestroyer.entities.Bullet;
import com.nahroto.fruitdestroyer.entities.Player;
import com.nahroto.fruitdestroyer.huds.BuyHud;
import com.nahroto.fruitdestroyer.weapons.Weapon;

public class ExtraAccuracyOverlay extends UpgradeOverlay
{
    public static final float UPGRADE_STEP = 1f;

    public static Float currentValue;
    public static Float nextValue;

    public ExtraAccuracyOverlay(ImageButton button, TextureAtlas gameScreenAtlas, BuyHud buyHud, Player player)
    {
        super("Reduces your weapon recoil.\nIncrease this stat for\nimproved shooting accuracy!", 1, button, gameScreenAtlas, buyHud, player);

        currentValue = new Float(1 / Bullet.START_RECOIL);
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

        Bullet.getWeapon().setRecoil(1 / currentValue);
    }

    @Override
    public void setPosition(float x, float y, int align)
    {
        super.setPosition(x, y, align);
        title.setPosition(background.getX(Align.center) - (titleFont.getWidth("ACCURACY") / 2), background.getY(Align.top) - 160);
    }
}
