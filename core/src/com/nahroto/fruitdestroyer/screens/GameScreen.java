package com.nahroto.fruitdestroyer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.nahroto.fruitdestroyer.Application;
import com.nahroto.fruitdestroyer.CollisionHandler;
import com.nahroto.fruitdestroyer.Constants;
import com.nahroto.fruitdestroyer.Font;
import com.nahroto.fruitdestroyer.HealthBar;
import com.nahroto.fruitdestroyer.Input;
import com.nahroto.fruitdestroyer.InputHandler;
import com.nahroto.fruitdestroyer.entities.Bullet;
import com.nahroto.fruitdestroyer.entities.Player;
import com.nahroto.fruitdestroyer.entities.enemies.Enemy;
import com.nahroto.fruitdestroyer.huds.GameHud;

public class GameScreen implements Screen
{
    private final Application APP;

    private TextureRegion bg;
    private Player player;
    private InputMultiplexer inputMultiplexer;
    private InputHandler inputHandler;
    private Input input;
    private CollisionHandler collisionHandler;
    private BitmapFont font;
    private GameHud gameHud;
    private Font ammoStatus;
    private Music actionMusic;
    private Sprite reloadIcon;

    public GameScreen(final Application APP, GameHud gameHud, TextureRegion bg, Player player, InputMultiplexer inputMultiplexer, InputHandler inputHandler, Input input, CollisionHandler collisionHandler, Font ammoStatus, Music actionMusic, Sprite reloadIcon)
    {
        this.APP = APP;
        this.gameHud = gameHud;
        this.bg = bg;
        this.player = player;
        this.inputMultiplexer = inputMultiplexer;
        this.inputHandler = inputHandler;
        this.input = input;
        this.collisionHandler = collisionHandler;
        this.ammoStatus = ammoStatus;
        this.actionMusic = actionMusic;
        this.reloadIcon = reloadIcon;
    }

    @Override
    public void show()
    {
        inputMultiplexer.addProcessor(gameHud.getStage());
        inputMultiplexer.addProcessor(input);
        Gdx.input.setInputProcessor(inputMultiplexer);

        actionMusic.setLooping(true);
        actionMusic.setLooping(true);
        actionMusic.play();
        actionMusic.setLooping(true);

        APP.camera.setToOrtho(false, Constants.V_WIDTH, Constants.V_HEIGHT);
        APP.camera.update();

        for (int i = 0; i < 8; i++)
        {
            Enemy.currentEnemies.add(Enemy.totalEnemies.get(i));
            Enemy.currentEnemies.get(i).setPosition(Constants.getRandomPosition(i, Enemy.currentEnemies.get(i).getSprite().getWidth(), Enemy.currentEnemies.get(i).getSprite().getHeight()));
            Enemy.currentEnemies.get(i).calculateRotation();
            Enemy.currentEnemies.get(i).calculateVelocity();
        }

        font = new BitmapFont();
        font.setColor(Color.WHITE);

        reloadIcon.setPosition(110, 20);
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //System.out.println(Gdx.graphics.getFramesPerSecond());

        // HANDLE INPUT
        inputHandler.update();

        // --- UPDATE ---

        // UPDATE PLAYER
        player.update();

        // UPDATE ENEMIES
        for (int i = 0; i < Enemy.currentEnemies.size; i++)
            Enemy.currentEnemies.get(i).update(delta);

        // UPDATE BULLETS
        for (int i = 0; i < Bullet.currentBullets.size; i++)
        {
            Bullet.currentBullets.get(i).update(delta);
            if (Bullet.currentBullets.get(i).isOutOfScreen)
            {
                Bullet.currentBullets.get(i).isUsed = false;
                Bullet.currentBullets.get(i).isOutOfScreen = false;
                Bullet.currentBullets.removeIndex(i);
                // System.out.println("bullet removed");
            }
        }

        // UPDATE COLLISION
        collisionHandler.update(APP, player, this);

        // UPDATE HEALTHBAR
        for (int i = 0; i < Enemy.currentEnemies.size; i++)
        {
            Enemy.currentEnemies.get(i).getHealthBar().getRed().setPosition((Enemy.currentEnemies.get(i).getBounds().getX() + (Enemy.currentEnemies.get(i).getBounds().getWidth() / 2)) - (Enemy.currentEnemies.get(i).getHealthBar().getRed().getWidth() / 2), Enemy.currentEnemies.get(i).getSprite().getY() - HealthBar.Y_OFFSET);
            Enemy.currentEnemies.get(i).getHealthBar().update(Enemy.currentEnemies.get(i).getHealth());

            if (Enemy.currentEnemies.get(i).getHealth() <= 0)
                Enemy.currentEnemies.get(i).revive(i);
        }

        // DO NOT SHOW RELOAD BUTTON WHEN AMMO IS FULL OR ALREADY RELOADING
        if (player.ammo == 30 || player.isReloading())
            gameHud.getActors().get(0).remove();
        else
            gameHud.addAllActorsToStage();


        // UPDATE GAMEHUD
        gameHud.update(delta);

        // IF RELOADING ROTATE RELOAD ICON
        if (player.isReloading())
            reloadIcon.rotate(-5);
        else
            reloadIcon.setRotation(0);

        // UPDATE CAMERA
        APP.camera.update();

        // UPDATE
        APP.batch.setProjectionMatrix(APP.camera.combined);

        // --- RENDER ---

        APP.batch.begin();

        // RENDER BACKGROUND
        APP.batch.draw(bg, 0, 0, Constants.V_WIDTH, Constants.V_HEIGHT);

        // RENDER ENEMIES
        for (Enemy enemy : Enemy.currentEnemies)
            enemy.render(APP.batch);

        // RENDER PLAYER
        player.render(APP.batch);

        // RENDER BULLETS
        for (Bullet bullet : Bullet.currentBullets)
            bullet.render(APP.batch);

        // RENDER HEALTHBAR
        for (Enemy enemy : Enemy.currentEnemies)
            enemy.getHealthBar().render(APP.batch);

        if (!player.isReloading())
            ammoStatus.render(APP.batch, player.ammo.toString(), 110, 20 + ammoStatus.getHeight(player.ammo.toString()), false);
        else
            reloadIcon.draw(APP.batch);

        // SHOW FPS
        font.draw(APP.batch, Gdx.graphics.getFramesPerSecond() + " ", 50, 1250);


        APP.batch.end();

        gameHud.render();
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
        actionMusic.stop();
    }

    @Override
    public void dispose()
    {

    }
}
