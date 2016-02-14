package com.nahroto.fruitdestroyer.huds;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nahroto.fruitdestroyer.Application;
import com.nahroto.fruitdestroyer.Constants;
import com.nahroto.fruitdestroyer.Font;
import com.nahroto.fruitdestroyer.WaveGenerator;
import com.nahroto.fruitdestroyer.helpers.GameResetter;

public class DeadHud extends Hud
{
    final Application APP;

    private ImageButton retryButton;
    private ImageButton homeButton;
    private ImageButton highScoreButton;
    private Font waveFont;
    private Font bigWaveFont;
    private Label text;
    private Label waveLabel;
    private Label highScoreLabel;

    public static Integer highScore = new Integer(1);

    public DeadHud(final Application APP, Viewport viewport, SpriteBatch batch, TextureAtlas gameScreenAtlas, final GameResetter gameResetter)
    {
        super(viewport, batch);
        this.APP = APP;

        // CONFIG RETRY-BUTTON
        homeButton = new ImageButton(new TextureRegionDrawable(gameScreenAtlas.findRegion("home-button-up")), new TextureRegionDrawable(gameScreenAtlas.findRegion("home-button-down")));
        retryButton = new ImageButton(new TextureRegionDrawable(gameScreenAtlas.findRegion("retry-button-up")), new TextureRegionDrawable(gameScreenAtlas.findRegion("retry-button-down")));
        highScoreButton = new ImageButton(new TextureRegionDrawable(gameScreenAtlas.findRegion("highscore-button-up")), new TextureRegionDrawable(gameScreenAtlas.findRegion("highscore-button-down")));
        retryButton.setPosition(Constants.V_WIDTH / 2, Constants.V_HEIGHT / 2 - 100, Align.center);
        homeButton.setPosition(Constants.V_WIDTH / 2, Constants.V_HEIGHT / 2 - 485, Align.center);
        highScoreButton.setPosition(Constants.V_WIDTH / 2, Constants.V_HEIGHT / 2 - 300, Align.center);

        retryButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                // REMOVE THIS HUDS ACTORS
                removeAllActors();

                // RESET THE GAME
                gameResetter.newGame();
                APP.setScreen(gameResetter.getGameScreen());
            }
        });

        homeButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                // REMOVE THIS HUDS ACTORS
                removeAllActors();

                gameResetter.newGame();

                // RESET THE GAME
                APP.setScreen(APP.menuScreen);
            }
        });
        highScoreButton.addListener(new ClickListener()
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

        waveFont = new Font(APP, "trompus12.otf", "fonts/trompus.otf", 75, Color.WHITE, Color.BLACK, 3, true);
        bigWaveFont = new Font(APP, "trompus99.otf", "fonts/trompus.otf", 120, Color.WHITE, Color.BLACK, 3, true);

        text = new Label("You made it to", new Label.LabelStyle(waveFont.getFont(), Color.WHITE));
        waveLabel = new Label(WaveGenerator.wave.toString() + "!", new Label.LabelStyle(bigWaveFont.getFont(), Color.WHITE));
        highScoreLabel = new Label("Your record\nWave" + APP.prefs.getInteger("highScore", WaveGenerator.wave), new Label.LabelStyle(waveFont.getFont(), Color.WHITE));

        text.setPosition(Constants.V_WIDTH / 2 - waveFont.getWidth("You made it to") / 2, 1100);
        waveLabel.setPosition(retryButton.getX(Align.center) - bigWaveFont.getWidth("Wave " + WaveGenerator.wave.toString() + "!") / 2, 1100 - bigWaveFont.getHeight("Wave " + WaveGenerator.wave.toString() + "!") - 30);
        highScoreLabel.setPosition(Constants.V_WIDTH / 2 - waveFont.getWidth("Your record\nWave " + highScore.toString()) / 2, 830);

        actors.add(retryButton);
        actors.add(homeButton);
        actors.add(highScoreButton);
        actors.add(text);
        actors.add(waveLabel);
        actors.add(highScoreLabel);
    }

    public void onShow()
    {
        APP.playServices.submitScore(WaveGenerator.wave);
        if (WaveGenerator.wave >= APP.prefs.getInteger("highScore", WaveGenerator.wave))
        {
            APP.prefs.putInteger("highScore", WaveGenerator.wave);
            APP.prefs.flush();
        }
        highScore = APP.prefs.getInteger("highScore", WaveGenerator.wave);
    }

    @Override
    public void update(float delta)
    {
        super.update(delta);

        waveLabel.setText("Wave " + WaveGenerator.wave.toString() + "!");
        highScoreLabel.setText("Your record\n      Wave " + highScore.toString());

        text.setPosition(Constants.V_WIDTH / 2 - waveFont.getWidth("You made it to") / 2, 1100);
        waveLabel.setPosition(retryButton.getX(Align.center) - bigWaveFont.getWidth("Wave " + WaveGenerator.wave.toString() + "!") / 2, 1100 - bigWaveFont.getHeight("Wave " + WaveGenerator.wave.toString() + "!") - 30);
        highScoreLabel.setPosition(Constants.V_WIDTH / 2 - waveFont.getWidth("Your record\nWave " + highScore.toString()) / 2, 830);
    }

    public void disableInput()
    {
        for (Actor actor : actors)
            actor.setTouchable(Touchable.disabled);
    }

    public void enableInput()
    {
        for (Actor actor : actors)
            actor.setTouchable(Touchable.enabled);
    }
}
