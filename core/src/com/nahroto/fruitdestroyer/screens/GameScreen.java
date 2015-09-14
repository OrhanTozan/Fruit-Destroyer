package com.nahroto.fruitdestroyer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.nahroto.fruitdestroyer.Application;
import com.nahroto.fruitdestroyer.Constants;

public class GameScreen implements Screen
{
    private final Application APP;
    private BitmapFont font;

    public GameScreen(final Application APP)
    {
        this.APP = APP;
    }

    @Override
    public void show()
    {
        APP.camera.setToOrtho(false, Constants.V_WIDTH, Constants.V_HEIGHT);
        APP.camera.update();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator()
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
