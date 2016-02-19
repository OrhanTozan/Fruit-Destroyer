package com.nahroto.fruitdestroyer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.nahroto.fruitdestroyer.Application;
import com.nahroto.fruitdestroyer.Debug;
import com.nahroto.fruitdestroyer.Logger;
import com.nahroto.fruitdestroyer.WaveGenerator;
import com.nahroto.fruitdestroyer.entities.Corpse;
import com.nahroto.fruitdestroyer.entities.enemies.Watermelon;
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
import com.nahroto.fruitdestroyer.helpers.GameResetter;
import com.nahroto.fruitdestroyer.huds.BuyHud;
import com.nahroto.fruitdestroyer.huds.GameHud;

public class LoadingScreen2 implements Screen
{
    private final byte WAIT_TIME = 3;

    private final Application APP;

    private Array<Enemy> totalEnemies;
    private Array<Enemy> currentEnemies;
    private Array<Orange> totalOranges;
    private Array<Ananas> totalAnanases;
    private Array<Watermelon> totalWatermelons;

    private Array<Corpse> totalCorpses;
    private Array<Corpse> currentCorpses;
    private Array<Corpse> totalOrangeCorpses;
    private Array<Corpse> totalAnanasCorpses;
    private Array<Corpse> totalWatermelonCorpses;

    private Array<Bullet> totalBullets;
    private Array<Bullet> currentBullets;

    private Array<Explosion> totalExplosions;
    private Array<Explosion> currentExplosions;

    private TextureAtlas gameScreenAtlas;
    private TextureAtlas explosionsAtlas;

    private Texture bg;
    private Texture blackShaderTexture;

    private Player player;

    private long currentTime;
    private InputMultiplexer inputMultiplexer;

    private CollisionHandler collisionHandler;

    private InputHandler inputHandler;
    private Font ammoStatus;
    private Music actionMusic;

    private GameScreen gameScreen;
    private DeadScreen deadScreen;

    private GameResetter gameResetter;

    private Font font;

    private Sound waveSFX;
    private WaveGenerator waveGenerator;

    private GameHud gameHud;
    private BuyHud buyHud;

    public LoadingScreen2(final Application APP, final Font font, Texture bg)
    {
        this.APP = APP;
        this.font = font;
        this.bg = bg;

        inputMultiplexer = new InputMultiplexer();

        // INIT GAMESCREEN ATLAS
        gameScreenAtlas = APP.assets.get("atlases/gamescreen.pack", TextureAtlas.class);

        // INIT EXPLOSIONS
        explosionsAtlas = APP.assets.get("atlases/explosion.pack", TextureAtlas.class);

        totalEnemies = new Array<Enemy>();
        currentEnemies = new Array<Enemy>();
        totalOranges = new Array<Orange>();
        totalAnanases = new Array<Ananas>();
        totalWatermelons = new Array<Watermelon>();

        totalCorpses = new Array<Corpse>();
        currentCorpses = new Array<Corpse>();
        totalOrangeCorpses = new Array<Corpse>();
        totalAnanasCorpses = new Array<Corpse>();
        totalWatermelonCorpses = new Array<Corpse>();

        totalBullets = new Array<Bullet>();
        currentBullets = new Array<Bullet>();

        totalExplosions = new Array<Explosion>();
        currentExplosions = new Array<Explosion>();

        // INIT EXPLOSION STUFF
        for (int i = 0; i < 5; i++)
            totalExplosions.add(new Explosion(explosionsAtlas, APP.assets.get("sounds/explosion.ogg", Sound.class)));

        // INIT BULLETS
        for (int i = 0; i < 30; i++)
            totalBullets.add(new Bullet(gameScreenAtlas.createSprite("bullet3")));

        // INIT ORANGES
        for (int i = 0; i < 30; i++)
            totalOranges.add(new Orange(APP, gameScreenAtlas.findRegion("orange"), gameScreenAtlas.findRegion("orange-hit"), gameScreenAtlas.createSprite("red-bar"), gameScreenAtlas.createSprite("green-bar")));

        // INIT ANANASES
        for (int i = 0; i < 30; i++)
            totalAnanases.add(new Ananas(APP, gameScreenAtlas.findRegion("ananas"), gameScreenAtlas.findRegion("ananas-hit"), gameScreenAtlas.createSprite("red-bar"), gameScreenAtlas.createSprite("green-bar")));

        // INIT WATERMELONS
        for (int i = 0; i < 30; i++)
            totalWatermelons.add(new Watermelon(APP, gameScreenAtlas.findRegion("watermelon"), gameScreenAtlas.findRegion("watermelon-hit"), gameScreenAtlas.createSprite("red-bar"), gameScreenAtlas.createSprite("green-bar")));

        for (int i = 0; i < 30; i++)
            totalOrangeCorpses.add(new Corpse(gameScreenAtlas.findRegion("orange-dead")));

        for (int i = 0; i < 30; i ++)
            totalAnanasCorpses.add(new Corpse(gameScreenAtlas.findRegion("ananas-dead")));

        for (int i = 0; i < 30; i ++)
            totalWatermelonCorpses.add(new Corpse(gameScreenAtlas.findRegion("watermelon-dead")));

        totalEnemies.addAll(totalOranges);
        totalEnemies.addAll(totalAnanases);
        totalEnemies.addAll(totalWatermelons);

        totalCorpses.addAll(totalOrangeCorpses);
        totalCorpses.addAll(totalAnanasCorpses);
        totalCorpses.addAll(totalWatermelonCorpses);

        // INIT PLAYER
        player = new Player(gameScreenAtlas.createSprite("player"), gameScreenAtlas.createSprite("flash2"), totalBullets, currentBullets, APP);

        // INIT INPUT-HANDLER
        inputHandler = new InputHandler(player);

        ammoStatus = new Font(APP, "trompus.otf", "fonts/trompus.otf", 100, Color.WHITE, Color.BLACK, 3, true);

        actionMusic = APP.assets.get("music/action.ogg", Music.class);

        blackShaderTexture = new Texture("backgrounds/blackShader.png");

        gameResetter = new GameResetter(APP, player, currentEnemies, currentBullets, currentCorpses, totalEnemies, totalBullets, totalCorpses);

        deadScreen = new DeadScreen(APP, gameScreenAtlas, gameResetter, bg, inputMultiplexer);

        collisionHandler = new CollisionHandler(deadScreen, currentEnemies, currentBullets);
        waveSFX = APP.assets.get("sounds/victory.ogg", Sound.class);
        waveGenerator = new WaveGenerator(totalEnemies, currentEnemies, totalOranges, totalAnanases, totalWatermelons);
        gameHud = new GameHud(player, APP.viewport, waveGenerator, inputMultiplexer, APP, APP.batch, gameScreenAtlas.findRegion("reload-up"), gameScreenAtlas.findRegion("reload-down"), gameScreenAtlas.findRegion("bullet-icon"), gameScreenAtlas.findRegion("volumeButton"), actionMusic);
        buyHud = new BuyHud(APP, APP.viewport, APP.batch, gameHud, waveGenerator, gameScreenAtlas, blackShaderTexture, inputMultiplexer, player);
        gameHud.init(buyHud);
        gameScreen = new GameScreen(APP, waveGenerator, totalEnemies, currentEnemies, totalOranges, totalAnanases, currentCorpses, totalOrangeCorpses, totalAnanasCorpses, totalWatermelonCorpses, totalBullets, currentBullets, totalExplosions, currentExplosions, gameHud, buyHud, bg, player, inputMultiplexer, new Font(APP, "trompus14.otf", "fonts/trompus.otf", 50, Color.WHITE, Color.BLACK, 3, true), ammoStatus, inputHandler, new Input(), collisionHandler, actionMusic, gameScreenAtlas.createSprite("reload-icon"), waveSFX, gameScreenAtlas.createSprite("accuracy-icon"));

        gameResetter.setGameScreen(gameScreen);
        gameResetter.setGameHud(gameHud);
        gameResetter.setOverlays(buyHud.getExtraAmmoOverlay(), buyHud.getExtraDamageOverlay(), buyHud.getExtraReloadSpeedOverlay(), buyHud.getExtraKnockbackOverlay(), buyHud.getMarksmanOverlay(), buyHud.getInstakillOverlay(), buyHud.getBombOverlay());
        gameResetter.setWaveQueues(waveGenerator.getQueues());
    }

    @Override
    public void show()
    {
        if (Debug.INFO)
            Logger.log("LoadingScreen2");

        APP.camera.setToOrtho(false, Constants.V_WIDTH, Constants.V_HEIGHT);
        APP.camera.update();

        if (APP.assets.update())
            APP.setScreen(gameScreen);
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // UPDATE
        APP.camera.update();

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
