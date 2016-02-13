package com.nahroto.fruitdestroyer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.nahroto.fruitdestroyer.Application;
import com.nahroto.fruitdestroyer.Debug;
import com.nahroto.fruitdestroyer.Logger;
import com.nahroto.fruitdestroyer.helpers.GameResetter;
import com.nahroto.fruitdestroyer.huds.DeadHud;

public class DeadScreen implements Screen
{
    private final Application APP;
    private DeadHud deadHud;
    private Texture bg;
    private InputMultiplexer inputMultiplexer;
    private long startTime;

    private boolean adShown;

    public DeadScreen(final Application APP, TextureAtlas gameScreenAtlas, GameResetter gameResetter, Texture bg, InputMultiplexer inputMultiplexer)
    {
        this.APP = APP;
        this.bg = bg;
        this.inputMultiplexer = inputMultiplexer;
        deadHud = new DeadHud(APP, APP.viewport, APP.batch, gameScreenAtlas, gameResetter);
    }

    @Override
    public void show()
    {
        if (Debug.SCREEN_INFO)
            Logger.log("DeadScreen");
        adShown = false;
        deadHud.onShow();
        deadHud.addAllActors();
        inputMultiplexer.clear();
        inputMultiplexer.addProcessor(deadHud.getStage());

        startTime = System.currentTimeMillis();
        deadHud.disableInput();
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (!adShown && System.currentTimeMillis() - startTime >= 1000)
        {
            adShown = true;
            APP.showAd();
            deadHud.enableInput();
        }

        deadHud.update(delta);

        APP.camera.update();

        // RENDER
        APP.batch.setProjectionMatrix(APP.camera.combined);
        APP.batch.begin();
        APP.batch.draw(bg, -80, -80);
        APP.batch.end();

        deadHud.render();



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
