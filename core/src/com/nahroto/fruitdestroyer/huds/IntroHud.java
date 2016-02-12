package com.nahroto.fruitdestroyer.huds;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nahroto.fruitdestroyer.Constants;
import com.nahroto.fruitdestroyer.Font;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class IntroHud extends Hud
{
    private Font titleFont;

    private Label title;

    public IntroHud(Viewport viewport, SpriteBatch batch)
    {
        super(viewport, batch);

        titleFont = new Font("fonts/trompus.otf", 100, Color.WHITE, true);
        title = new Label("NahroTo", new Label.LabelStyle(titleFont.getFont(), Color.WHITE));

        title.setPosition(1300, Constants.V_HEIGHT / 2, Align.left);

        actors.add(title);

        addAllActors();
    }

    public void start()
    {
        title.addAction(sequence(
                moveToAligned(Constants.V_WIDTH / 2, Constants.V_HEIGHT / 2, Align.center, 1f, Interpolation.pow2Out),
                delay(1f),
                moveToAligned(-20, Constants.V_HEIGHT / 2, Align.right, 1f, Interpolation.pow2In)
        ));
    }
}
