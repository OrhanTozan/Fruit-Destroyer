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
import com.nahroto.fruitdestroyer.huds.MenuHud;

public class LoadingScreen extends BasicLoadingScreen implements Screen
{
    private final Application APP;

    private long currentTime;

    private TextureAtlas menuScreenAtlas;
    private Texture bg;

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
        APP.camera.update();

        // INIT BACKGROUND
        bg = new Texture("backgrounds/map.png");
        bg.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

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

        // LOAD MUSIC
        APP.assets.load("music/epictheme.ogg", Music.class);
        APP.assets.load("music/action.ogg", Music.class);
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (APP.assets.update() && System.currentTimeMillis() - currentTime > 3000)
        {
            menuScreenAtlas = APP.assets.get("atlases/menuscreen.pack", TextureAtlas.class);
            APP.setScreen(new MenuScreen(APP, font, bg, new MenuHud(APP, font, APP.viewport, APP.batch, menuScreenAtlas.findRegion("title"), menuScreenAtlas.findRegion("playbutton-up"), menuScreenAtlas.findRegion("playbutton-down"), bg), APP.assets.get("music/epictheme.ogg", Music.class)));
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
