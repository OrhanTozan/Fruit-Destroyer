package com.nahroto.fruitdestroyer.huds;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nahroto.fruitdestroyer.Application;
import com.nahroto.fruitdestroyer.Constants;
import com.nahroto.fruitdestroyer.Debug;
import com.nahroto.fruitdestroyer.Font;
import com.nahroto.fruitdestroyer.Logger;
import com.nahroto.fruitdestroyer.WaveGenerator;
import com.nahroto.fruitdestroyer.screens.LoadingScreen2;
import com.nahroto.fruitdestroyer.screens.MenuScreen;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class MenuHud extends Hud
{
    private final Application APP;

    private ImageButton playButton;
    private ImageButton highScoresButton;
    private Image title;

    private Font waveFont;
    private Font bigWaveFont;

    private Label waveLabel;
    private Label text;

    public MenuHud(final Application APP, Viewport viewport, SpriteBatch batch, TextureRegion title, TextureRegion playButtonDrawableUp, TextureRegion playButtonDrawableDown, TextureRegion scoreButtonDrawableUp, TextureRegion scoreButtonDrawableDown)
    {
        super(viewport, batch);

        this.title = new Image(title);
        this.title.setPosition(Constants.V_WIDTH / 2, Constants.V_HEIGHT / 2 + 400, Align.center);
        this.APP = APP;

        playButton = new ImageButton(new TextureRegionDrawable(playButtonDrawableUp), new TextureRegionDrawable(playButtonDrawableDown));
        highScoresButton = new ImageButton(new TextureRegionDrawable(scoreButtonDrawableUp), new TextureRegionDrawable(scoreButtonDrawableDown));
        playButton.setPosition(Constants.V_WIDTH / 2, Constants.V_HEIGHT / 2 + 100, Align.center);
        highScoresButton.setPosition(Constants.V_WIDTH / 2, Constants.V_HEIGHT / 2 - 100, Align.center);
        playButton.addListener(new ClickListener()
        {
            public void clicked(InputEvent event, float x, float y)
            {
                if (Debug.INFO)
                    Logger.log("BUTTON PRESSED");
                if (APP.getScreen().getClass() == MenuScreen.class)
                {
                    if (Debug.INFO)
                        Logger.log("BUTTON ACTIVATED");
                    APP.setScreen(APP.loadingScreen2);
                }
            }
        });
        highScoresButton.addListener(new ClickListener()
        {
            public void clicked(InputEvent event, float x, float y)
            {

                if (!APP.playServices.isSignedIn())
                {
                    APP.playServices.signIn();
                    APP.playServices.showScore();
                }
                else
                    APP.playServices.showScore();
            }
        });
        waveFont = new Font(APP, "trompus87.otf", "fonts/trompus.otf", 60, Color.WHITE, Color.BLACK, 3, true);
        bigWaveFont = new Font(APP, "trompus409.otf", "fonts/trompus.otf", 120, Color.WHITE, Color.BLACK, 3, true);

        text = new Label("Highest wave record", new Label.LabelStyle(waveFont.getFont(), Color.WHITE));
        waveLabel = new Label(WaveGenerator.wave.toString() + "!", new Label.LabelStyle(bigWaveFont.getFont(), Color.WHITE));

        text.setPosition(Constants.V_WIDTH / 2 - waveFont.getWidth("Highest wave record") / 2, 1000);
        waveLabel.setPosition(Constants.V_WIDTH / 2 - bigWaveFont.getWidth("Wave " + APP.prefs.getInteger("highestScore", 1) + "!") / 2, 1000 - bigWaveFont.getHeight("Wave " + WaveGenerator.wave.toString() + "!") - 40);

        this.title.setOrigin(Align.center);
        this.title.setScale(10f, 10f);

        actors.add(this.title);
        actors.add(playButton);
        actors.add(highScoresButton);

        addAllActors();
    }

    public void show()
    {
        this.title.addAction(scaleTo(1f, 1f, 0.7f, Interpolation.pow2In));
        if (APP.prefs.getInteger("highScore", 1) > 1)
        {
            actors.add(text);
            actors.add(waveLabel);
            addAllActors();
        }
    }

    @Override
    public void update(float delta)
    {
        super.update(delta);

        waveLabel.setText("Wave " + APP.prefs.getInteger("highScore", 1) + "!");

        text.setPosition(Constants.V_WIDTH / 2 - waveFont.getWidth("Highest wave record") / 2, 260);
        waveLabel.setPosition(Constants.V_WIDTH / 2 - bigWaveFont.getWidth("Wave " + APP.prefs.getInteger("highScore", 1) + "!") / 2, 250 - bigWaveFont.getHeight("Wave " + APP.prefs.getInteger("highScore", 1) + "!") - 30);
    }
}
