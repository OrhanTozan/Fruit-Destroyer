package com.nahroto.fruitdestroyer.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.nahroto.fruitdestroyer.Application;

public class LoadingScreen implements Screen
{
    private final Application APP;

    public LoadingScreen(final Application APP)
    {
        this.APP = APP;
    }

    @Override
    public void show()
    {

        // LOAD OBJECTS ATLAS
        APP.assets.load("atlases/objects.pack", TextureAtlas.class);

        // LOAD BACKGROUND TEXTURES
        APP.assets.load("images/backgrounds/red.png", Texture.class);
        APP.assets.load("images/backgrounds/yellow.png", Texture.class);
        APP.assets.load("images/backgrounds/blue.png", Texture.class);
        APP.assets.load("images/backgrounds/green.png", Texture.class);
    }

    @Override
    public void render(float delta)
    {
        if (APP.assets.update())
            APP.setScreen(new GameScreen(APP));
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
