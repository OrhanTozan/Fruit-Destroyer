package com.nahroto.fruitdestroyer.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.nahroto.fruitdestroyer.Application;
import com.nahroto.fruitdestroyer.huds.MenuHud;

public class LoadingScreen implements Screen
{
    private final Application APP;

    private long currentTime;

    private TextureAtlas menuScreenAtlas;

    public LoadingScreen(final Application APP)
    {
        this.APP = APP;
    }

    @Override
    public void show()
    {
        System.out.println("loadingscreen");
        currentTime = System.currentTimeMillis();

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
        if (APP.assets.update() && System.currentTimeMillis() - currentTime > 5000)
        {
            menuScreenAtlas = APP.assets.get("atlases/menuscreen.pack", TextureAtlas.class);
            APP.setScreen(new MenuScreen(APP, new MenuHud(APP, APP.viewport, APP.batch, menuScreenAtlas.findRegion("playbutton-up"), menuScreenAtlas.findRegion("playbutton-down")), APP.assets.get("music/epictheme.wav", Music.class)));
        }
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
