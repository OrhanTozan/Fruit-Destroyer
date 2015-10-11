package com.nahroto.fruitdestroyer.huds;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameHud extends Hud
{
    private ImageButton reloadButton;

    public GameHud(Viewport viewport, SpriteBatch batch, TextureRegion reloadButtonUp, TextureRegion reloadButtonDown)
    {
        super(viewport, batch);
        
        reloadButton = new ImageButton(new TextureRegionDrawable(reloadButtonUp), new TextureRegionDrawable(reloadButtonDown));

        actors.add(reloadButton);
        addAllActorsToStage();
    }
}
