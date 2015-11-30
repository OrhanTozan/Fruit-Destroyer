package com.nahroto.fruitdestroyer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.nahroto.fruitdestroyer.Application;
import com.nahroto.fruitdestroyer.huds.DeadHud;

public class DeadScreen implements Screen
{
    private final Application APP;
    private DeadHud hud;

    public DeadScreen(final Application APP)
    {
        this.APP = APP;
        hud = new DeadHud(APP.viewport, APP.batch);
    }

    @Override
    public void show()
    {

    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        deadHud.update(delta);
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
