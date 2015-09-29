package com.nahroto.fruitdestroyer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.nahroto.fruitdestroyer.Application;
import com.nahroto.fruitdestroyer.CollisionHandler;
import com.nahroto.fruitdestroyer.Constants;
import com.nahroto.fruitdestroyer.Font;
import com.nahroto.fruitdestroyer.Input;
import com.nahroto.fruitdestroyer.InputHandler;
import com.nahroto.fruitdestroyer.entities.Bullet;
import com.nahroto.fruitdestroyer.entities.Player;
import com.nahroto.fruitdestroyer.entities.enemies.Enemy;
import com.nahroto.fruitdestroyer.entities.enemies.Orange;

public class LoadingScreen2 implements Screen
{
    private final byte WAIT_TIME = 3;

    private final Application APP;

    private TextureAtlas gameScreenAtlas;

    private Texture bg;
    private Player player;

    private Font font;
    private long currentTime;
    private InputMultiplexer inputMultiplexer;

    private InputHandler inputHandler;


    public LoadingScreen2(final Application APP)
    {
        this.APP = APP;
    }

    @Override
    public void show()
    {
        inputMultiplexer = new InputMultiplexer();
        currentTime = System.currentTimeMillis();

        APP.camera.setToOrtho(false, Constants.V_WIDTH, Constants.V_HEIGHT);
        APP.camera.update();

        // INIT ATLAS
        gameScreenAtlas = APP.assets.get("atlases/gamescreen.pack", TextureAtlas.class);

        // INIT BG
        bg = APP.assets.get("backgrounds/gray.png", Texture.class);

        // INIT PLAYER
        player = new Player(gameScreenAtlas.createSprite("player"), gameScreenAtlas.createSprite("flash"), APP);

        // INIT BULLETS
        for (int i = 0; i < Bullet.COUNT; i++)
        {
            Bullet.totalBullets.add(new Bullet(gameScreenAtlas.createSprite("bullet")));
        }

        // INIT ORANGES
        for (int i = 0; i < Orange.COUNT; i++)
        {
            Enemy.totalEnemies.add(new Orange(gameScreenAtlas.createSprite("orange"), gameScreenAtlas.createSprite("red-bar"), gameScreenAtlas.createSprite("green-bar"), 17, 10, 63, 60));
        }

        // INIT INPUT-HANDLER
        inputHandler = new InputHandler(APP, player);

        // INIT COLLISION HANDLER

        // INIT LOADING FONT
        font = new Font("fonts/trompus.otf", 90, Color.WHITE, true);


    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // UPDATE
        APP.camera.update();
        if (System.currentTimeMillis() - currentTime > WAIT_TIME * 1000)
            APP.setScreen(new GameScreen(APP, bg, player, inputMultiplexer, inputHandler, new Input(), new CollisionHandler()));

        // RENDER
        APP.batch.setProjectionMatrix(APP.camera.combined);
        APP.batch.begin();
        font.render(APP.batch, "Loading", Constants.V_WIDTH / 2, Constants.V_HEIGHT / 2, true);
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
