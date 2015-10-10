package com.nahroto.fruitdestroyer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.nahroto.fruitdestroyer.Application;
import com.nahroto.fruitdestroyer.Constants;
import com.nahroto.fruitdestroyer.huds.MenuHud;

public class MenuScreen implements Screen
{

    private final Application APP;
    private Music epicTheme;

    private MenuHud menuHud;

    public MenuScreen(final Application APP, MenuHud menuHud, Music epicTheme)
    {
        this.APP = APP;
        this.menuHud = menuHud;
        this.epicTheme = epicTheme;
    }

    @Override
    public void show()
    {
        epicTheme.setLooping(true);
        epicTheme.play();
        APP.camera.setToOrtho(false, Constants.V_WIDTH, Constants.V_HEIGHT);
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // --- UPDATE ---

        // UPDATE HUD
        menuHud.update(delta);


        APP.camera.update();

        // --- RENDER ---

        APP.batch.setProjectionMatrix(APP.camera.combined);

        // RENDER HUD
        menuHud.render();
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
        epicTheme.stop();
    }

    @Override
    public void dispose()
    {

    }
}
