package com.nahroto.fruitdestroyer.huds;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nahroto.fruitdestroyer.Constants;
import com.nahroto.fruitdestroyer.Font;
import com.nahroto.fruitdestroyer.WaveGenerator;
import com.nahroto.fruitdestroyer.helpers.GameResetter;

public class DeadHud extends Hud
{
    private ImageButton retryButton;
    private Font waveFont;
    private Label waveLabel;

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
                removeAllActors();

                // RESET THE GAME
                gameResetter.newGame();
            }
        });

        waveFont = new Font("fonts/trompus.otf", 130, Color.WHITE, Color.BLACK, 3, true);
        waveLabel = new Label("Wave " + WaveGenerator.wave.toString(), new Label.LabelStyle(waveFont.getFont(), Color.WHITE));
        waveLabel.setPosition(retryButton.getX(Align.center) - waveFont.getWidth("Wave " + WaveGenerator.wave.toString()) / 2, 1000);


        actors.add(retryButton);
        actors.add(waveLabel);
    }

    @Override
    public void update(float delta)
    {
        super.update(delta);
        waveLabel.setText("Wave " + WaveGenerator.wave.toString());
        waveLabel.setPosition(retryButton.getX(Align.center) - waveFont.getWidth("Wave " + WaveGenerator.wave.toString()) / 2, 1000);
    }
}
