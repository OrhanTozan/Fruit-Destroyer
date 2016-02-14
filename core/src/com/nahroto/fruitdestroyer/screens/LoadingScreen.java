package com.nahroto.fruitdestroyer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.nahroto.fruitdestroyer.Application;
import com.nahroto.fruitdestroyer.Constants;
import com.nahroto.fruitdestroyer.Debug;
import com.nahroto.fruitdestroyer.Logger;
import com.nahroto.fruitdestroyer.huds.MenuHud;

public class LoadingScreen extends BasicLoadingScreen implements Screen
{
    private final Application APP;

    private long currentTime;

    private TextureAtlas menuScreenAtlas;
    private Texture bg;
    private MenuHud menuHud;

    private boolean assetsLoaded = false;
    private long startTime;

    public LoadingScreen(final Application APP)
    {
        super(APP);
        this.APP = APP;
    }

    @Override
    public void show()
    {
        if (Debug.SCREEN_INFO)
            Logger.log("LoadingScreen");
        currentTime = System.currentTimeMillis();
        APP.camera.setToOrtho(false, Constants.V_WIDTH, Constants.V_HEIGHT);
        APP.camera.update();

        // LOAD BACKGROUND
        APP.assets.load("backgrounds/map.png", Texture.class);

        // LOAD EXPLOSIONS ATLAS
        APP.assets.load("atlases/explosion.pack", TextureAtlas.class);

        // LOAD SCREEN ATLASES
        APP.assets.load("atlases/menuscreen.pack", TextureAtlas.class);
        APP.assets.load("atlases/gamescreen.pack", TextureAtlas.class);

        // LOAD SOUNDS
        APP.assets.load("sounds/shot.wav", Sound.class);
        APP.assets.load("sounds/empty2.wav", Sound.class);
        APP.assets.load("sounds/reload100.wav", Sound.class);
        APP.assets.load("sounds/reload75.wav", Sound.class);
        APP.assets.load("sounds/reload50.wav", Sound.class);
        APP.assets.load("sounds/reload25.wav", Sound.class);
        APP.assets.load("sounds/squish.wav", Sound.class);
        APP.assets.load("sounds/victory.ogg", Sound.class);
        APP.assets.load("sounds/explosion.ogg", Sound.class);
        APP.assets.load("sounds/orchestra.wav", Sound.class);

        // LOAD MUSIC
        APP.assets.load("music/action.ogg", Music.class);
        APP.assets.load("music/epictheme.ogg", Music.class);
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (APP.assets.update() && !assetsLoaded)
        {
            bg = APP.assets.get("backgrounds/map.png", Texture.class);
            bg.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            APP.loadingScreen2 = new LoadingScreen2(APP, font, bg);
            menuScreenAtlas = APP.assets.get("atlases/menuscreen.pack", TextureAtlas.class);
            APP.menuScreen = new MenuScreen(APP, font, bg, new MenuHud(APP, APP.viewport, APP.batch, menuScreenAtlas.findRegion("title"), menuScreenAtlas.findRegion("play-button-up"), menuScreenAtlas.findRegion("play-button-down"), bg), APP.assets.get("music/epictheme.ogg", Music.class));
            assetsLoaded = true;
            startTime = System.currentTimeMillis();
        }

        if (assetsLoaded && System.currentTimeMillis() - startTime >= 5000)
            APP.setScreen(APP.menuScreen);

        APP.batch.setProjectionMatrix(APP.camera.combined);
        APP.batch.begin();
        font.render(APP.batch, "Loading", Constants.V_WIDTH / 2, Constants.V_HEIGHT / 2, true);
        APP.batch.end();
    }

    @Override
    public void resize(int width, int height)
    {
        APP.viewport.update(width, height);
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
