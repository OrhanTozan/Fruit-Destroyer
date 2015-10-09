package com.nahroto.fruitdestroyer.huds;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MenuHud extends Hud
{
    ImageButton playButton;

    public MenuHud(Viewport viewport, SpriteBatch batch, TextureRegionDrawable drawableUp, TextureRegionDrawable drawableDown)
    {
        super(viewport, batch);
        playButton = new ImageButton(drawableUp, drawableDown);

        actors.add(playButton);
        addAllActorsToStage();
    }
}
