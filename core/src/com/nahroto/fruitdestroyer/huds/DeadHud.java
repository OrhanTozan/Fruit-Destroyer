package com.nahroto.fruitdestroyer.huds;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nahroto.fruitdestroyer.Constants;
import com.nahroto.fruitdestroyer.helpers.GameResetter;

public class DeadHud extends Hud
{
    private ImageButton retryButton;

    public DeadHud(Viewport viewport, SpriteBatch batch, TextureRegion retryButtonUp, TextureRegion retryButtonDown, final GameResetter gameResetter)
    {
        super(viewport, batch);

        // CONFIG RETRY-BUTTON
        retryButton = new ImageButton(new TextureRegionDrawable(retryButtonUp), new TextureRegionDrawable(retryButtonDown));
        retryButton.setPosition(Constants.V_WIDTH / 2, Constants.V_HEIGHT / 2, Align.center);
        retryButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                // REMOVE THIS HUDS ACTORS
                removeAllActorsFromStage();

                // RESET THE GAME
                gameResetter.newGame();
            }
        });

        actors.add(retryButton);
    }
}
