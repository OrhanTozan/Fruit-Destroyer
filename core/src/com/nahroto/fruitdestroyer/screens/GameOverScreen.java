package com.nahroto.fruitdestroyer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.nahroto.fruitdestroyer.Application;
import com.nahroto.fruitdestroyer.Constants;
import com.nahroto.fruitdestroyer.Font;

public class GameOverScreen implements Screen
{
    private final Application APP;
    private Font font;

    public GameOverScreen(final Application APP)
    {
        this.APP = APP;
    }

    @Override
    public void show()
    {
        APP.camera.setToOrtho(false, Constants.V_WIDTH, Constants.V_HEIGHT);
        APP.camera.update();

        font = new Font("fonts/trompus.otf", 90, Color.WHITE, Color.BLACK, 3, true);
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // --- UPDATE ---
        APP.camera.update();

        // --- RENDER ---
        APP.batch.setProjectionMatrix(APP.camera.combined);
        APP.batch.begin();

        font.render(APP.batch, "Game Over", Constants.V_WIDTH / 2, Constants.V_HEIGHT / 2, true);

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
