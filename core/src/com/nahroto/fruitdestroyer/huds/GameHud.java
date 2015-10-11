package com.nahroto.fruitdestroyer.huds;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nahroto.fruitdestroyer.Constants;

public class GameHud extends Hud
{
    private ImageButton reloadButton;
    private Image bulletIcon;

    public GameHud(Viewport viewport, SpriteBatch batch, TextureRegion reloadButtonUp, TextureRegion reloadButtonDown, TextureRegion bulletIcon)
    {
        super(viewport, batch);

        reloadButton = new ImageButton(new TextureRegionDrawable(reloadButtonUp), new TextureRegionDrawable(reloadButtonDown));
        this.bulletIcon = new Image(bulletIcon);

        reloadButton.setPosition(Constants.V_WIDTH - 20, 20, Align.bottomRight);
        this.bulletIcon.setPosition(20, 20);

        actors.add(reloadButton);
        actors.add(this.bulletIcon);
        addAllActorsToStage();
    }
}
