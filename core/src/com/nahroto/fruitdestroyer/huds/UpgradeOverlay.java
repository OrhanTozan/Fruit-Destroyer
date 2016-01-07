package com.nahroto.fruitdestroyer.huds;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.nahroto.fruitdestroyer.Font;

public class UpgradeOverlay
{
    private Array<Actor> actors;
    private Vector2 position;

    private Image background;
    private Label currentValueLabel;
    private Label nextValueLabel;
    private ImageButton button;
    private Label descriptionLabel;

    private Integer currentValue;
    private Integer nextValue;

    public UpgradeOverlay(String description, ImageButton button, TextureAtlas gameScreenAtlas)
    {
        actors = new Array<Actor>();
        position = new Vector2();

        background = new Image(gameScreenAtlas.findRegion("upgradeDescBG"));
        this.button = new ImageButton(button.getStyle());

        currentValue = new Integer(0);
        nextValue = new Integer(0);

        currentValueLabel = new Label(currentValue.toString(), new Label.LabelStyle(new Font("fonts/trompus.otf", 35, Color.WHITE, Color.BLACK, 2, true).getFont(), Color.WHITE));
        nextValueLabel = new Label(nextValue.toString(), new Label.LabelStyle(new Font("fonts/trompus.otf", 35, Color.WHITE, Color.BLACK, 2, true).getFont(), Color.WHITE));
        descriptionLabel = new Label(description, new Label.LabelStyle(new Font("fonts/trompus.otf", 35, Color.WHITE, Color.BLACK, 2, true).getFont(), Color.WHITE));

        actors.add(background);
        actors.add(this.button);
        actors.add(currentValueLabel);
        actors.add(nextValueLabel);
        actors.add(descriptionLabel);
    }

    public Array<Actor> getActors()
    {
        return actors;
    }

    public void setPosition(float x, float y, int align)
    {
        position.set(x, y);
        background.setPosition(position.x, position.y, align);
        button.setPosition(background.getX(Align.center), background.getY() + 275, align);
        currentValueLabel.setPosition(background.getX() + 115, background.getY() + 225);
        nextValueLabel.setPosition(background.getX() + 470, background.getY() + 225);
        descriptionLabel.setPosition(background.getX() + 100, background.getY() + 130);
    }
}
