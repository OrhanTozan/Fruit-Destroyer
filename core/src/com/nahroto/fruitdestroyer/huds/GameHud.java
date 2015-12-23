package com.nahroto.fruitdestroyer.huds;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nahroto.fruitdestroyer.Constants;
import com.nahroto.fruitdestroyer.entities.Player;

public class GameHud extends Hud
{
    private ImageButton reloadButton;
    private ImageButton soundButton;
    private Image bulletIcon;

    public GameHud(final Player player, Viewport viewport, SpriteBatch batch, TextureRegion reloadButtonUp, TextureRegion reloadButtonDown, TextureRegion bulletIconTexture, TextureRegion soundButtonTexture, final Music actionMusic)
    {
        super(viewport, batch);

        // INIT ACTORS
        reloadButton = new ImageButton(new TextureRegionDrawable(reloadButtonUp), new TextureRegionDrawable(reloadButtonDown));
        soundButton = new ImageButton(new TextureRegionDrawable(soundButtonTexture));
        bulletIcon = new Image(bulletIconTexture);

        // SET POSITIONS
        reloadButton.setPosition(Constants.V_WIDTH - 20, 20, Align.bottomRight);
        soundButton.setPosition(10, Constants.V_HEIGHT - 10, Align.topLeft);
        bulletIcon.setPosition(20, 17);

        reloadButton.addListener(new ClickListener()
        {
            public void clicked(InputEvent event, float x, float y)
            {
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

        addAllActorsToStage();
    }
}
