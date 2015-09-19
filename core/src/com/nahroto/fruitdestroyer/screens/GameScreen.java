package com.nahroto.fruitdestroyer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.nahroto.fruitdestroyer.Application;
import com.nahroto.fruitdestroyer.Constants;
import com.nahroto.fruitdestroyer.Input;
import com.nahroto.fruitdestroyer.InputHandler;
import com.nahroto.fruitdestroyer.entities.Bullet;
import com.nahroto.fruitdestroyer.entities.Player;

public class GameScreen implements Screen
{
    private final Application APP;

    private Texture bg;
    private Player player;
    private InputMultiplexer inputMultiplexer;
    private InputHandler inputHandler;
    BitmapFont font;

    public GameScreen(final Application APP, Texture bg, Player player, InputMultiplexer inputMultiplexer, InputHandler inputHandler)
    {
        this.APP = APP;
        this.bg = bg;
        this.player = player;
        this.inputMultiplexer = inputMultiplexer;
        this.inputHandler = inputHandler;
    }

    @Override
    public void show()
    {
        inputMultiplexer.addProcessor(new Input());
        Gdx.input.setInputProcessor(inputMultiplexer);
        APP.camera.setToOrtho(false, Constants.V_WIDTH, Constants.V_HEIGHT);
        APP.camera.update();
        font = new BitmapFont();
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // System.out.println(Gdx.graphics.getFramesPerSecond());

        // HANDLE INPUT
        inputHandler.update();


        // UPDATE

        // UPDATE PLAYER
        player.update();

        // UPDATE BULLETS
        for (int i = 0; i < Bullet.currentBullets.size; i++)
        {
            Bullet.currentBullets.get(i).update(delta);
            if (Bullet.currentBullets.get(i).isOutOfScreen)
            {
                Bullet.currentBullets.get(i).isUsed = false;
                Bullet.currentBullets.get(i).isOutOfScreen = false;
                Bullet.currentBullets.removeIndex(i);
                System.out.println("bullet removed");
            }
        }

        APP.camera.update();

        // RENDER
        APP.batch.setProjectionMatrix(APP.camera.combined);
        APP.batch.begin();

        // RENDER BACKGROUND
        APP.batch.draw(bg, 0, 0, Constants.V_WIDTH, Constants.V_HEIGHT);

        //RENDER PLAYER
        player.render(APP.batch);

        // RENDER BULLETS
        for (int i = 0; i < Bullet.currentBullets.size; i++)
        {
            Bullet.currentBullets.get(i).render(APP.batch);
        }
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
