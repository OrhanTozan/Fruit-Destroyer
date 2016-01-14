package com.nahroto.fruitdestroyer.huds.upgradeoverlays;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.nahroto.fruitdestroyer.Font;
import com.nahroto.fruitdestroyer.Logger;
import com.nahroto.fruitdestroyer.entities.Player;
import com.nahroto.fruitdestroyer.huds.BuyHud;

public abstract class UpgradeOverlay
{
    protected int upgradeCost;

    protected Array<Actor> actors;
    protected Vector2 position;

    protected Image background;
    protected Label title;
    protected Label currentValueLabel;
    protected Label nextValueLabel;
    protected ImageButton button;
    protected Label descriptionLabel;
    protected ImageButton exitButton;

    protected Font titleFont;
    protected Font currentValueFont;
    protected Font nextValueFont;

    public UpgradeOverlay(String description, final Integer upgradeCost, final ImageButton button, TextureAtlas gameScreenAtlas, final BuyHud buyHud, final Player player)
    {
        actors = new Array<Actor>();
        position = new Vector2();

        this.upgradeCost = upgradeCost;

        background = new Image(gameScreenAtlas.findRegion("upgradeDescBG"));
        this.button = new ImageButton(button.getStyle());

        this.button.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                if (buyHud.getPoints() >= upgradeCost)
                {
                    upgrade(player);
                    buyHud.reducePoints(upgradeCost);
                }
            }
        });


        exitButton = new ImageButton(new TextureRegionDrawable(gameScreenAtlas.findRegion("exitButton")));
        exitButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                buyHud.emptyActorList();
                buyHud.removeAllActors();
                buyHud.getStage().clear();
                buyHud.addBuyOverlayActors();
            }
        });

        titleFont = new Font("fonts/trompus.otf", 55, Color.WHITE, Color.BLACK, 4, true);

        title = new Label("nan", new Label.LabelStyle(titleFont.getFont(), Color.WHITE));
        currentValueFont = new Font("fonts/trompus.otf", 35, Color.WHITE, Color.BLACK, 2, true);
        nextValueFont = new Font("fonts/trompus.otf", 35, Color.WHITE, Color.BLACK, 2, true);
        currentValueLabel = new Label("nan", new Label.LabelStyle(currentValueFont.getFont(), Color.WHITE));
        nextValueLabel = new Label("nan", new Label.LabelStyle(nextValueFont.getFont(), Color.WHITE));
        descriptionLabel = new Label(description, new Label.LabelStyle(new Font("fonts/trompus.otf", 30, Color.WHITE, Color.BLACK, 2, true).getFont(), Color.WHITE));

        actors.add(background);
        actors.add(title);
        actors.add(this.button);
        actors.add(currentValueLabel);
        actors.add(nextValueLabel);
        actors.add(descriptionLabel);
        actors.add(exitButton);
    }

    protected abstract void upgrade(Player player);

    protected void setTitleText(String text)
    {
        title.setText(text);
    }

    public Array<Actor> getActors()
    {
        return actors;
    }

    public void setPosition(float x, float y, int align)
    {
        position.set(x, y);
        background.setPosition(position.x, position.y, align);
        button.setPosition(background.getX(Align.center), background.getY() + 415, align);
        currentValueLabel.setPosition(background.getX() + 135, background.getY() + 405, Align.top);
        nextValueLabel.setPosition(background.getX() + 485, background.getY() + 405, Align.top);
        descriptionLabel.setPosition(background.getX() + 60, background.getY() + 305, Align.topLeft);
        exitButton.setPosition(background.getX() + background.getWidth() - 32, background.getY() + background.getHeight() - 32, Align.center);
    }

    public void update()
    {
        Logger.log(currentValueLabel.getX(Align.top) + ", " + currentValueLabel.getY(Align.top));
    }
}
