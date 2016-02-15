package com.nahroto.fruitdestroyer.huds.upgradeoverlays;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.utils.Align;
import com.nahroto.fruitdestroyer.Application;
import com.nahroto.fruitdestroyer.entities.Player;
import com.nahroto.fruitdestroyer.huds.BuyHud;

public class ExtraReloadSpeedOverlay extends UpgradeOverlay
{
    public static final int UPGRADE_STEP = -25;

    private String currentSpeedStatus;
    private String nextSpeedStatus;

    public static Integer currentPercentage;
    public static Integer nextPercentage;

    public ExtraReloadSpeedOverlay(final Application APP, ImageButton button, TextureAtlas gameScreenAtlas, BuyHud buyHud, Player player)
    {
        super(APP, "Reloading speed of your\nweapon. Increase this stat so\nyou can reload faster!", 6, button, gameScreenAtlas, buyHud, player);

        currentPercentage = new Integer(100);
        nextPercentage = new Integer(currentPercentage + UPGRADE_STEP);

        currentSpeedStatus = percentageToStatus(currentPercentage);
        nextSpeedStatus = percentageToStatus(nextPercentage);

        currentValueLabel.setText(currentSpeedStatus);
        nextValueLabel.setText(nextSpeedStatus);
        setTitleText("RELOAD SPEED");
    }

    @Override
    protected void upgrade(Player player)
    {
        currentPercentage = nextPercentage;
        nextPercentage += UPGRADE_STEP;
        currentSpeedStatus = percentageToStatus(currentPercentage);
        nextSpeedStatus = percentageToStatus(nextPercentage);
        currentValueLabel.setText(currentSpeedStatus);
        nextValueLabel.setText(nextSpeedStatus);

        player.setReloadingConfig(currentPercentage);

        if (currentPercentage == 25)
            maxValue = true;
    }

    @Override
    public void reset()
    {
        currentPercentage = 100;
        nextPercentage = currentPercentage + UPGRADE_STEP;
        currentSpeedStatus = percentageToStatus(currentPercentage);
        nextSpeedStatus = percentageToStatus(nextPercentage);
        currentValueLabel.setText(currentSpeedStatus);
        nextValueLabel.setText(nextSpeedStatus);
        maxValue = false;
    }

    private String percentageToStatus(int percentage)
    {
        switch (percentage)
        {
            case 100:
                return "normal";

            case 75:
                return "faster";

            case 50:
                return "pretty fast";

            case 25:
                return "MAX speed";

            default:
                return "";
        }
    }

    @Override
    public void setPosition(float x, float y, int align)
    {
        super.setPosition(x, y, align);
        title.setPosition(background.getX(Align.center) - (titleFont.getWidth("RELOAD SPEED") / 2), background.getY(Align.top) - 160);
        currentValueLabel.setPosition(background.getX() + 125 - (currentValueFont.getWidth(currentSpeedStatus) / 2), background.getY() + 370);
        nextValueLabel.setPosition(background.getX() + 475 - (nextValueFont.getWidth(nextSpeedStatus) / 2), background.getY() + 370);
    }
}
