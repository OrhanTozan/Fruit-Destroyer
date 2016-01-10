package com.nahroto.fruitdestroyer.huds.upgradeoverlays;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.utils.Align;
import com.nahroto.fruitdestroyer.Logger;
import com.nahroto.fruitdestroyer.entities.Player;
import com.nahroto.fruitdestroyer.huds.BuyHud;

public class ExtraReloadSpeedOverlay extends UpgradeOverlay
{
    private static final int UPGRADE_STEP = -25;

    private String currentSpeedStatus;
    private String nextSpeedStatus;

    private Integer currentPercentage;
    private Integer nextPercentage;

    public ExtraReloadSpeedOverlay(ImageButton button, TextureAtlas gameScreenAtlas, BuyHud buyHud)
    {
        super("Your reloading speed. The higher, the faster you will reload your weapon!\nUpgrade cost: 4 points", 4, button, gameScreenAtlas, buyHud);

        currentPercentage = new Integer(100);
        nextPercentage = new Integer(currentPercentage + UPGRADE_STEP);

        currentSpeedStatus = percentageToStatus(currentPercentage);
        nextSpeedStatus = percentageToStatus(nextPercentage);

        currentValueLabel.setText(currentSpeedStatus);
        nextValueLabel.setText(nextSpeedStatus);
    }

    @Override
    protected void upgrade()
    {
        currentPercentage = nextPercentage;
        nextPercentage += UPGRADE_STEP;
        currentSpeedStatus = percentageToStatus(currentPercentage);
        nextSpeedStatus = percentageToStatus(nextPercentage);
        currentValueLabel.setText(currentSpeedStatus);
        nextValueLabel.setText(nextSpeedStatus);
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
                return "max speed";

            default:
                return "error";
        }
    }

    @Override
    public void setPosition(float x, float y, int align)
    {
        super.setPosition(x, y, align);
        currentValueLabel.setPosition(background.getX() + 110, background.getY() + 270, Align.top);
        nextValueLabel.setPosition(background.getX() + 465, background.getY() + 270, Align.top);
    }
}
