package com.nahroto.fruitdestroyer.huds.powerupoverlays;

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
import com.nahroto.fruitdestroyer.huds.BuyHud;

public class PowerupOverlay
{
    private Integer cost;
    private Vector2 position;

    private Image background;
    private Image title;
    private Image checkedBox;
    private Image uncheckedBox;
    private ImageButton exitButton;
    private ImageButton powerupButton;
    private Label costLabel;
    private Label descriptionLabel;
    private Array<Actor> actors;

    public PowerupOverlay(String description, Integer cost, ImageButton powerupButton, TextureAtlas gameScreenAtlas, final BuyHud buyHud)
    {
        this.cost = cost;
        this.powerupButton = powerupButton;
        actors = new Array<Actor>();
        position = new Vector2();
        background = new Image(gameScreenAtlas.findRegion("powerupDescBG"));
        checkedBox = new Image(gameScreenAtlas.findRegion("checked"));
        uncheckedBox = new Image(gameScreenAtlas.findRegion("unchecked"));
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
        descriptionLabel = new Label(description, new Label.LabelStyle(new Font("fonts/trompus.otf", 30, Color.WHITE, Color.BLACK, 2, true).getFont(), Color.WHITE));
        if (cost == 1)
            costLabel = new Label("Price: " + cost.toString() + " point", new Label.LabelStyle(new Font("fonts/trompus.otf", 40, Color.WHITE, Color.BLACK, 2, true).getFont(), Color.YELLOW));
        else
            costLabel = new Label("Price: " + cost.toString() + " points", new Label.LabelStyle(new Font("fonts/trompus.otf", 40, Color.WHITE, Color.BLACK, 2, true).getFont(), Color.YELLOW));
        actors.add(background);
        actors.add(uncheckedBox);
        actors.add(costLabel);
        actors.add(descriptionLabel);
        actors.add(costLabel);
        actors.add(powerupButton);
        actors.add(exitButton);
    }

    public void updatePriceLabelColor(int currentPoints)
    {
        if (currentPoints >= cost)
            costLabel.setColor(Color.GREEN);
        else
            costLabel.setColor(Color.RED);
    }

    public void setPosition(float x, float y, int align)
    {
        position.set(x, y);
        background.setPosition(position.x, position.y, align);
        checkedBox.setPosition(background.getX() + 100, background.getY() + 360);
        uncheckedBox.setPosition(background.getX() + 100, background.getY() + 360);
        powerupButton.setPosition(background.getX(Align.center), background.getY() + 415, align);
        costLabel.setPosition(background.getX() + 50, background.getY() + 50);
        descriptionLabel.setPosition(background.getX() + 60, background.getY() + 305, Align.topLeft);
        exitButton.setPosition(background.getX() + background.getWidth() - 32, background.getY() + background.getHeight() - 32, Align.center);
    }

    public Array<Actor> getActors()
    {
        return actors;
    }
}
