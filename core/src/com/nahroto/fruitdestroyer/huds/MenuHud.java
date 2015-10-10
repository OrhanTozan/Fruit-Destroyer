package com.nahroto.fruitdestroyer.huds;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nahroto.fruitdestroyer.Application;
import com.nahroto.fruitdestroyer.Constants;
import com.nahroto.fruitdestroyer.screens.LoadingScreen2;

public class MenuHud extends Hud
{
    private ImageButton playButton;

    public MenuHud(final Application APP, Viewport viewport, SpriteBatch batch, TextureRegion playButtonDrawableUp, TextureRegion playButtonDrawableDown)
    {
        super(viewport, batch);

        playButton = new ImageButton(new TextureRegionDrawable(playButtonDrawableUp), new TextureRegionDrawable(playButtonDrawableDown));
        playButton.setPosition(Constants.V_WIDTH / 2, Constants.V_HEIGHT / 2 + 200, Align.center);
        System.out.println(playButton.getWidth());
        playButton.addListener(new ClickListener()
        {
            public void clicked(InputEvent event, float x, float y)
            {
                APP.setScreen(new LoadingScreen2(APP));
            }
        });
        actors.add(playButton);

        addAllActorsToStage();
    }
}
