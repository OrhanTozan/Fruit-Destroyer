package com.nahroto.fruitdestroyer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.nahroto.fruitdestroyer.Application;
import com.nahroto.fruitdestroyer.Constants;
import com.nahroto.fruitdestroyer.Player;

public class GameScreen implements Screen
{
    private final Application APP;

    private TextureAtlas objectsAtlas;

    private Texture bg;
    private Player player;

    public GameScreen(final Application APP)
    {
        this.APP = APP;
    }

    @Override
    public void show()
    {
        APP.camera.setToOrtho(false, Constants.V_WIDTH, Constants.V_HEIGHT);
        APP.camera.update();

        objectsAtlas = APP.assets.get("atlases/objects.pack", TextureAtlas.class);

        bg = APP.assets.get("images/backgrounds/yellow.png", Texture.class);

        player = new Player(objectsAtlas.createSprite("player"));
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // System.out.println(Gdx.graphics.getFramesPerSecond());

        // UPDATE
        player.update(APP);

        // RENDER
        APP.batch.setProjectionMatrix(APP.camera.combined);
        APP.batch.begin();

        // RENDER BACKGROUND
        APP.batch.draw(bg, 0, 0, Constants.V_WIDTH, Constants.V_HEIGHT);

        //RENDER PLAYER
        player.render(APP.batch);

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
