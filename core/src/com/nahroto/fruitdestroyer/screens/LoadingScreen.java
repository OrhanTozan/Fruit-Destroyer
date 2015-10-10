package com.nahroto.fruitdestroyer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.nahroto.fruitdestroyer.Application;
import com.nahroto.fruitdestroyer.Constants;
import com.nahroto.fruitdestroyer.Font;
import com.nahroto.fruitdestroyer.huds.MenuHud;

public class LoadingScreen extends AbstractLoadingScreen implements Screen
{
    private final Application APP;

    private long currentTime;

    private TextureAtlas menuScreenAtlas;

    public LoadingScreen(final Application APP)
    {
        super();
        this.APP = APP;
    }

    @Override
    public void show()
    {
        currentTime = System.currentTimeMillis();
        APP.camera.setToOrtho(false, Constants.V_WIDTH, Constants.V_HEIGHT);

        // LOAD SCREEN ATLASES
        APP.assets.load("atlases/menuscreen.pack", TextureAtlas.class);
        APP.assets.load("atlases/gamescreen.pack", TextureAtlas.class);

        // LOAD BACKGROUND TEXTURES
        APP.assets.load("backgrounds/gray.png", Texture.class);
        APP.assets.load("backgrounds/red.png", Texture.class);
        APP.assets.load("backgrounds/yellow.png", Texture.class);
        APP.assets.load("backgrounds/blue.png", Texture.class);
        APP.assets.load("backgrounds/green.png", Texture.class);

        // LOAD SOUNDS
        APP.assets.load("sounds/shot.wav", Sound.class);

        // LOAD MUSIC
        APP.assets.load("music/epictheme.wav", Music.class);
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (APP.assets.update() && System.currentTimeMillis() - currentTime > 3000)
        {
            menuScreenAtlas = APP.assets.get("atlases/menuscreen.pack", TextureAtlas.class);
            APP.setScreen(new MenuScreen(APP, new MenuHud(APP, APP.viewport, APP.batch, menuScreenAtlas.findRegion("playbutton-up"), menuScreenAtlas.findRegion("playbutton-down")), APP.assets.get("music/epictheme.wav", Music.class)));
        }

        APP.batch.setProjectionMatrix(APP.camera.combined);
        APP.batch.begin();
        font.render(APP.batch, "Loading", Constants.V_WIDTH / 2, Constants.V_HEIGHT / 2, true);
        APP.batch.end();
    }

    @Override
    public void resize(int width, int height)
    {

    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void hide()
    {

    }

    @Override
    public void dispose()
    {

    }
}
