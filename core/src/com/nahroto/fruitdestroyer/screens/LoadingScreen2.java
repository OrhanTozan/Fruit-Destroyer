package com.nahroto.fruitdestroyer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.nahroto.fruitdestroyer.Application;
import com.nahroto.fruitdestroyer.helpers.CollisionHandler;
import com.nahroto.fruitdestroyer.Constants;
import com.nahroto.fruitdestroyer.Explosion;
import com.nahroto.fruitdestroyer.Font;
import com.nahroto.fruitdestroyer.Input;
import com.nahroto.fruitdestroyer.InputHandler;
import com.nahroto.fruitdestroyer.entities.Bullet;
import com.nahroto.fruitdestroyer.entities.Player;
import com.nahroto.fruitdestroyer.entities.enemies.Ananas;
import com.nahroto.fruitdestroyer.entities.enemies.Enemy;
import com.nahroto.fruitdestroyer.entities.enemies.Orange;
import com.nahroto.fruitdestroyer.huds.DeadHud;
import com.nahroto.fruitdestroyer.huds.GameHud;

public class LoadingScreen2 implements Screen
{
    private final byte WAIT_TIME = 3;

    private final Application APP;

    private TextureAtlas gameScreenAtlas;
    private TextureAtlas explosionsAtlas;

    private TextureRegion bg;
    private Player player;

    private long currentTime;
    private InputMultiplexer inputMultiplexer;

    private InputHandler inputHandler;
    private Font ammoStatus;
    private Music actionMusic;

    private GameScreen gameScreen;

    private Font font;

    public LoadingScreen2(final Application APP, final Font font)
    {
        this.APP = APP;
        this.font = font;
    }

    @Override
    public void show()
    {
        Constants.STATUS = Constants.Status.LOADINGSCREEN2;

        //System.out.println("loadingscreen2");
        inputMultiplexer = new InputMultiplexer();
        currentTime = System.currentTimeMillis();

        APP.camera.setToOrtho(false, Constants.V_WIDTH, Constants.V_HEIGHT);
        APP.camera.update();

        // INIT GAMESCREEN ATLAS
        gameScreenAtlas = APP.assets.get("atlases/gamescreen.pack", TextureAtlas.class);

        // INIT EXPLOSIONS
        explosionsAtlas = APP.assets.get("atlases/explosions.pack", TextureAtlas.class);


        // INIT EXPLOSION STUFF
        for (int i = 0; i < 2; i++)
            Explosion.totalExplosions.add(new Explosion(explosionsAtlas, APP.assets.get("sounds/explosion.ogg", Sound.class)));

        // INIT BG
        bg = gameScreenAtlas.findRegion("map");

        // INIT PLAYER
        player = new Player(gameScreenAtlas.createSprite("player"), gameScreenAtlas.createSprite("flash"), APP);

        // INIT BULLETS
        for (int i = 0; i < Bullet.magSize; i++)
            Bullet.totalBullets.add(new Bullet(gameScreenAtlas.createSprite("bullet")));

        // INIT ORANGES
        for (int i = 0; i < Orange.COUNT; i++)
            Enemy.totalEnemies.add(new Orange(APP, gameScreenAtlas.findRegion("orange"), gameScreenAtlas.findRegion("orange-hit"), gameScreenAtlas.createSprite("red-bar"), gameScreenAtlas.createSprite("green-bar")));

        // INIT ANANASES
        for (int i = 0; i < Ananas.COUNT; i++)
            Enemy.totalEnemies.add(new Ananas(APP, gameScreenAtlas.findRegion("ananas"), gameScreenAtlas.findRegion("ananas-hit"), gameScreenAtlas.createSprite("red-bar"), gameScreenAtlas.createSprite("green-bar")));

        // INIT INPUT-HANDLER
        inputHandler = new InputHandler(APP, player);

        ammoStatus = new Font("fonts/trompus.otf", 100, Color.WHITE, Color.BLACK, 3, true);

        actionMusic = APP.assets.get("music/action.ogg", Music.class);

        gameScreen = new GameScreen(APP, new GameHud(player, APP.viewport, APP.batch, gameScreenAtlas.findRegion("reload-up"), gameScreenAtlas.findRegion("reload-down"), gameScreenAtlas.findRegion("bullet-icon")), new DeadHud(APP.viewport, APP.batch, gameScreenAtlas.findRegion("retry-button-up"), gameScreenAtlas.findRegion("retry-button-down"), actionMusic), bg, player, inputMultiplexer, inputHandler, new Input(), new CollisionHandler(), ammoStatus, actionMusic, gameScreenAtlas.createSprite("reload-icon"), new Integer(1), APP.assets.get("sounds/victory.ogg", Sound.class));

    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // UPDATE
        APP.camera.update();
        if (System.currentTimeMillis() - currentTime > WAIT_TIME * 1000)
            APP.setScreen(gameScreen);

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
