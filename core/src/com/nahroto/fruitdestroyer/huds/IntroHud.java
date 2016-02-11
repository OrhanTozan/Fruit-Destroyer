package com.nahroto.fruitdestroyer.huds;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nahroto.fruitdestroyer.Font;

public class IntroHud extends Hud
{
    private Font titleFont;

    private Label title;

    public IntroHud(Viewport viewport, SpriteBatch batch)
    {
        super(viewport, batch);

        titleFont = new Font("fonts/trompus.otf", 100, Color.WHITE, true);
        title = new Label("NahroTo", new Label.LabelStyle(titleFont.getFont(), Color.WHITE));

        actors.add(title);

        addAllActors();
    }
}
