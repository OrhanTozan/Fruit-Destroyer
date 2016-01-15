package com.nahroto.fruitdestroyer.huds;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nahroto.fruitdestroyer.Application;
import com.nahroto.fruitdestroyer.Constants;
import com.nahroto.fruitdestroyer.Font;
import com.nahroto.fruitdestroyer.Logger;
import com.nahroto.fruitdestroyer.WaveGenerator;
import com.nahroto.fruitdestroyer.entities.Player;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;


public class GameHud extends Hud
{

    public static boolean reloadingIsAllowed = true;
    public static boolean animatingWave;

    private ImageButton reloadButton;
    private ImageButton soundButton;
    private Image bulletIcon;
    private Label waveLabel;
    private Image whiteShader;
    private Image blackShader;

    private Sound orchestra;

    private Font waveFont;

    private Runnable setWhiteShaderVisible;
    private Runnable setWhiteShaderInvisible;
    private Runnable setWaveClearedText;
    private Runnable setWaveStatusText;
    private Runnable showBlackShader;
    private Runnable hideBlackShader;
    private Runnable playOrchestra;

    public GameHud(final Player player, Viewport viewport, Application APP, SpriteBatch batch, TextureRegion reloadButtonUp, TextureRegion reloadButtonDown, TextureRegion bulletIconTexture, TextureRegion soundButtonTexture, final Music actionMusic)
    {
        super(viewport, batch);

        // INIT ACTORS
        reloadButton = new ImageButton(new TextureRegionDrawable(reloadButtonUp), new TextureRegionDrawable(reloadButtonDown));
        soundButton = new ImageButton(new TextureRegionDrawable(soundButtonTexture));
        bulletIcon = new Image(bulletIconTexture);
        whiteShader = new Image(new Texture("backgrounds/whiteShader.png"));

        waveFont = new Font("fonts/trompus.otf", 100, Color.WHITE, Color.BLACK, 3, true);
        waveLabel = new Label("Wave " + WaveGenerator.wave.toString(), new Label.LabelStyle(waveFont.getFont(), Color.WHITE));
        blackShader = new Image(new Texture("backgrounds/blackShader.png"));

        orchestra = APP.assets.get("sounds/orchestra.wav", Sound.class);

        blackShader.setPosition(-80, -80);
        blackShader.addAction(alpha(0f));


        setWhiteShaderVisible = new Runnable()
        {
            @Override
            public void run()
            {
                whiteShader.setVisible(true);
            }
        };

        setWhiteShaderInvisible = new Runnable()
        {
            @Override
            public void run()
            {
                whiteShader.setVisible(false);
            }
        };

        setWaveClearedText = new Runnable()
        {
            @Override
            public void run()
            {
                waveLabel.setText("WAVE " + WaveGenerator.wave.toString() + "\nCLEARED!");
            }
        };

        setWaveStatusText = new Runnable()
        {
            @Override
            public void run()
            {
                waveLabel.setText("Wave " + WaveGenerator.wave.toString());
            }
        };

        showBlackShader = new Runnable()
        {
            @Override
            public void run()
            {
                showBlackShader();
            }
        };

        hideBlackShader = new Runnable()
        {
            @Override
            public void run()
            {
                hideBlackShader();
            }
        };

        playOrchestra = new Runnable()
        {
            @Override
            public void run()
            {
                orchestra.play();
            }
        };

        // SET POSITIONS
        reloadButton.setPosition(Constants.V_WIDTH - 20, 20, Align.bottomRight);
        soundButton.setPosition(10, Constants.V_HEIGHT - 10, Align.topLeft);
        bulletIcon.setPosition(20, 17);
        waveLabel.setPosition(Constants.V_WIDTH / 2 - (waveFont.getWidth("Wave " + WaveGenerator.wave.toString()) / 2), Constants.V_HEIGHT - 100);
        whiteShader.setPosition(-80, -80);

        reloadButton.addListener(new ClickListener()
        {
            public void clicked(InputEvent event, float x, float y)
            {
                if (reloadingIsAllowed)
                    player.reload();
            }
        });

        soundButton.addListener(new ClickListener()
        {
            public void clicked(InputEvent event, float x, float y)
            {
                if (actionMusic.getVolume() == 0)
                    actionMusic.setVolume(1f);
                else
                    actionMusic.setVolume(0f);
            }
        });

        whiteShader.setVisible(false);
        whiteShader.setTouchable(Touchable.disabled);
        blackShader.setTouchable(Touchable.disabled);

        actors.add(reloadButton);
        actors.add(soundButton);
        actors.add(whiteShader);
        actors.add(blackShader);
        actors.add(bulletIcon);
        actors.add(waveLabel);

        addAllActors();
    }

    private void showBlackShader()
    {
        Logger.log("asd");
        blackShader.addAction(alpha(1f, 0.5f));
    }

    private void hideBlackShader()
    {
        blackShader.addAction(alpha(0f, 0.5f));
    }


    private void updateWaveText()
    {
        waveLabel.setText("Wave " + WaveGenerator.wave.toString());
    }

    public void animateWaveLabel()
    {
        Logger.log("activated");
        animatingWave = true;
        waveLabel.addAction(sequence(
                // EASE OUT OF SCREEN AND SHOW BLOACKSHADER
                parallel(moveTo(waveLabel.getX(), Constants.V_HEIGHT + 100, 0.5f), run(showBlackShader)),
                // SET WAVE CLEARED TEXT
                run(setWaveClearedText),
                // TELEPORT TO THE LEFT SIDE OF SCREEN
                moveTo(-waveFont.getWidth("WAVE " + WaveGenerator.wave.toString() + "\nCLEARED!"), Constants.V_HEIGHT / 2 - waveFont.getHeight("WAVE " + WaveGenerator.wave.toString() + "\nCLEARED!") / 2),
                // EASE TO MIDDLE OF SCREEN
                parallel(moveTo(Constants.V_WIDTH / 2 - waveFont.getWidth("WAVE " + WaveGenerator.wave.toString() + "\nCLEARED!") / 2, Constants.V_HEIGHT / 2 - waveFont.getHeight("WAVE " + WaveGenerator.wave.toString() + "\nCLEARED!") / 2, 0.5f), run(playOrchestra))
                ));

        updateWaveText();
    }

    public void animateWhiteShader()
    {
        whiteShader.addAction(sequence(run(setWhiteShaderVisible), alpha(1f), fadeOut(0.5f), run(setWhiteShaderInvisible)
        ));
    }
}
