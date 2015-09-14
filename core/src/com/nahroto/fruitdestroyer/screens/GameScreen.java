package com.nahroto.fruitdestroyer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.nahroto.fruitdestroyer.Application;
import com.nahroto.fruitdestroyer.Constants;

public class GameScreen implements Screen
{
    private final Application APP;
    private Sprite bg;
    private Sprite player;

    public GameScreen(final Application APP)
    {
        this.APP = APP;
    }

    @Override
    public void show()
    {
        APP.camera.setToOrtho(false, Constants.V_WIDTH, Constants.V_HEIGHT);
        APP.camera.update();
        bg = new Sprite(new Texture("backgrounds/yellow.png"));
        player = new 
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // UPDATE

        // RENDER
        APP.batch.setProjectionMatrix(APP.camera.combined);
        APP.batch.begin();
        APP.batch.draw(bg, 0, 0);
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
