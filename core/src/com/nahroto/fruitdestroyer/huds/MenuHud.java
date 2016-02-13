package com.nahroto.fruitdestroyer.huds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
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
import com.nahroto.fruitdestroyer.screens.LoadingScreen2;
import com.nahroto.fruitdestroyer.screens.MenuScreen;

public class MenuHud extends Hud
{
    private ImageButton playButton;
    private Image title;

    public MenuHud(final Application APP, Viewport viewport, SpriteBatch batch, TextureRegion title, TextureRegion playButtonDrawableUp, TextureRegion playButtonDrawableDown, final Texture bg)
    {
        super(viewport, batch);

        this.title = new Image(title);
        this.title.setPosition(Constants.V_WIDTH / 2, Constants.V_HEIGHT / 2 + 400, Align.center);

        playButton = new ImageButton(new TextureRegionDrawable(playButtonDrawableUp), new TextureRegionDrawable(playButtonDrawableDown));
        playButton.setPosition(Constants.V_WIDTH / 2, Constants.V_HEIGHT / 2 + 100, Align.center);
        playButton.addListener(new ClickListener()
        {
            public void clicked(InputEvent event, float x, float y)
            {
                if (Debug.INFO)
                    Logger.log("BUTTON PRESSED");
                if (APP.getScreen().getClass() == MenuScreen.class)
                {
                    if(Debug.INFO)
                        Logger.log("BUTTON ACTIVATED");
                    APP.setScreen(APP.loadingScreen2);
                }
            }
        });

        actors.add(this.title);
        actors.add(playButton);

        addAllActors();
    }
}
