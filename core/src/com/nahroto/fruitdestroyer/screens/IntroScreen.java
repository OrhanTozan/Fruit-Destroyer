package com.nahroto.fruitdestroyer.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.nahroto.fruitdestroyer.Application;
import com.nahroto.fruitdestroyer.Constants;
import com.nahroto.fruitdestroyer.huds.IntroHud;

public class IntroScreen implements Screen
{
    private final Application APP;

    private IntroHud introHud;
    private Music music;

    public IntroScreen(Application APP)
    {
        this.APP = APP;
        introHud = new IntroHud(APP.viewport, APP.batch);
    }

    @Override
    public void show()
    {
        APP.camera.setToOrtho(false, Constants.V_WIDTH, Constants.V_HEIGHT);
        APP.camera.update();

        music = APP.assets.get("music/epictheme.ogg", Music.class);

        music.setLooping(true);
        music.play();
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        introHud.update(delta);
        APP.camera.update();

        APP.batch.setProjectionMatrix(APP.camera.combined);

        introHud.render();
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
