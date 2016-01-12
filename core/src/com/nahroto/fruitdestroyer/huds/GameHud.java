package com.nahroto.fruitdestroyer.huds;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nahroto.fruitdestroyer.Constants;
import com.nahroto.fruitdestroyer.Font;
import com.nahroto.fruitdestroyer.WaveGenerator;
import com.nahroto.fruitdestroyer.entities.Player;


public class GameHud extends Hud
{
    public static boolean reloadingIsAllowed = true;

    private ImageButton reloadButton;
    private ImageButton soundButton;
    private Image bulletIcon;
    private Label waveLabel;

    private Font waveFont;

    public GameHud(final Player player, Viewport viewport, SpriteBatch batch, TextureRegion reloadButtonUp, TextureRegion reloadButtonDown, TextureRegion bulletIconTexture, TextureRegion soundButtonTexture, final Music actionMusic)
    {
        super(viewport, batch);

        // INIT ACTORS
        reloadButton = new ImageButton(new TextureRegionDrawable(reloadButtonUp), new TextureRegionDrawable(reloadButtonDown));
        soundButton = new ImageButton(new TextureRegionDrawable(soundButtonTexture));
        bulletIcon = new Image(bulletIconTexture);

        waveFont = new Font("fonts/trompus.otf", 100, Color.WHITE, Color.BLACK, 3, true);
        waveLabel = new Label("Wave " + WaveGenerator.wave.toString(), new Label.LabelStyle(waveFont.getFont(), Color.WHITE));

        // SET POSITIONS
        reloadButton.setPosition(Constants.V_WIDTH - 20, 20, Align.bottomRight);
        soundButton.setPosition(10, Constants.V_HEIGHT - 10, Align.topLeft);
        bulletIcon.setPosition(20, 17);
        waveLabel.setPosition(Constants.V_WIDTH / 2 - (waveFont.getWidth("Wave " + WaveGenerator.wave.toString()) / 2), Constants.V_HEIGHT - 100);

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

        actors.add(reloadButton);
        actors.add(soundButton);
        actors.add(bulletIcon);
        actors.add(waveLabel);

        addAllActors();
    }
}
