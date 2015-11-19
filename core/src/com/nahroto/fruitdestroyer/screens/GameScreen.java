package com.nahroto.fruitdestroyer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.nahroto.fruitdestroyer.Application;
import com.nahroto.fruitdestroyer.helpers.CollisionHandler;
import com.nahroto.fruitdestroyer.Constants;
import com.nahroto.fruitdestroyer.Explosion;
import com.nahroto.fruitdestroyer.Font;
import com.nahroto.fruitdestroyer.HealthBar;
import com.nahroto.fruitdestroyer.Input;
import com.nahroto.fruitdestroyer.InputHandler;
import com.nahroto.fruitdestroyer.entities.Bullet;
import com.nahroto.fruitdestroyer.entities.Player;
import com.nahroto.fruitdestroyer.entities.enemies.Enemy;
import com.nahroto.fruitdestroyer.huds.DeadHud;
import com.nahroto.fruitdestroyer.huds.GameHud;

public class GameScreen implements Screen
{
    private final Application APP;

    private static final float WAVE_MULTIPLIER = 1.6f;

    private TextureRegion bg;
    private Player player;
    private InputMultiplexer inputMultiplexer;
    private InputHandler inputHandler;
    private Input input;
    private CollisionHandler collisionHandler;
    private BitmapFont font;
    private GameHud gameHud;
    private DeadHud deadHud;
    private Font ammoStatus;
    private Music actionMusic;
    private Sprite reloadIcon;

    private Sound waveSFX;

    public static Integer wave;

    private Array<Explosion> totalExplosions;
    private Array<Explosion> currentExplosions;
    private Array<Sound> explosionSounds;
    private ShapeRenderer shapeRenderer;

    public GameScreen(final Application APP, Array<Explosion> totalExplosions, Array<Explosion> currentExplosions, Array<Sound> explosionSounds, GameHud gameHud, DeadHud deadHud, TextureRegion bg, Player player, InputMultiplexer inputMultiplexer, InputHandler inputHandler, Input input, CollisionHandler collisionHandler, Font ammoStatus, Music actionMusic, Sprite reloadIcon, Integer wave, Sound waveSFX)
    {
        this.APP = APP;
        this.gameHud = gameHud;
        this.deadHud = deadHud;
        this.bg = bg;
        this.player = player;
        this.inputMultiplexer = inputMultiplexer;
        this.inputHandler = inputHandler;
        this.input = input;
        this.collisionHandler = collisionHandler;
        this.ammoStatus = ammoStatus;
        this.actionMusic = actionMusic;
        this.reloadIcon = reloadIcon;
        this.wave = wave;
        this.waveSFX = waveSFX;
        this.totalExplosions = totalExplosions;
        this.currentExplosions = currentExplosions;
        this.explosionSounds = explosionSounds;
    }

    @Override
    public void show()
    {
        Constants.STATUS = Constants.Status.PLAYING;

        inputMultiplexer.addProcessor(gameHud.getStage());
        inputMultiplexer.addProcessor(deadHud.getStage());
        inputMultiplexer.addProcessor(input);
        Gdx.input.setInputProcessor(inputMultiplexer);

        actionMusic.setLooping(true);
        actionMusic.setLooping(true);
        actionMusic.play();
        actionMusic.setLooping(true);

        APP.camera.setToOrtho(false, Constants.V_WIDTH, Constants.V_HEIGHT);
        APP.camera.update();

        for (int i = 0; i < wave; i++)
        {
            Enemy.currentEnemies.add(Enemy.totalEnemies.get(i));
            Enemy.currentEnemies.get(i).setHealth(48);
            Enemy.currentEnemies.get(i).setPosition(Constants.getRandomPosition(MathUtils.random(0, 15), Enemy.currentEnemies.get(i).getSprite().getWidth(), Enemy.currentEnemies.get(i).getSprite().getHeight()));
            Enemy.currentEnemies.get(i).calculateRotation();
            Enemy.currentEnemies.get(i).calculateVelocity();
        }

        font = new BitmapFont();
        font.setColor(Color.WHITE);

        reloadIcon.setPosition(110, 20);

        player.setReloadingConfig(100);

        // shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // WHILE ALIVE
        switch (Constants.STATUS)
        {
            case PLAYING:

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
                collisionHandler.update(player, this, actionMusic, deadHud);

                // UPDATE HEALTHBAR
                for (int i = 0; i < Enemy.currentEnemies.size; i++)
                {
                    Enemy.currentEnemies.get(i).getHealthBar().getRed().setPosition((Enemy.currentEnemies.get(i).getSprite().getX() + (Enemy.currentEnemies.get(i).getSprite().getWidth() / 2)) - (Enemy.currentEnemies.get(i).getHealthBar().getRed().getWidth() / 2), Enemy.currentEnemies.get(i).getSprite().getY() - HealthBar.Y_OFFSET);
                    Enemy.currentEnemies.get(i).getHealthBar().update(Enemy.currentEnemies.get(i).getHealth());

                    // IF ENEMY DIES
                    if (Enemy.currentEnemies.get(i).getHealth() <= 0)
                    {
                        // IF ENEMY IS EXPLODABLE, THEN EXPLODE
                        if (Enemy.currentEnemies.get(i).isExplodable())
                        {
                            explosionSounds.get(0).play();
                            currentExplosions.add(totalExplosions.get(0));
                            currentExplosions.get(0).setPosition(((Enemy.currentEnemies.get(i).getSprite().getX() + (Enemy.currentEnemies.get(i).getSprite().getWidth() / 2)) - (Explosion.WIDTH / 2)), (Enemy.currentEnemies.get(i).getPosition().y) + 30);
                            currentExplosions.get(0).setRotation(Enemy.currentEnemies.get(i).getAngle());
                        }

                        // REMOVE ENEMY
                        Enemy.currentEnemies.removeIndex(i);
                    }

                    // WHEN WAVE IS CLEARED
                    if (Enemy.currentEnemies.size == 0)
                    {
                        waveSFX.play();
                        wave += 1;
                        startNewWave();
                    }
                }

                // UPDATE EXPLOSIONS
                for (int i = 0; i < currentExplosions.size; i++)
                {
                    currentExplosions.get(i).update(delta);
                    if (currentExplosions.get(i).isAnimationFinished())
                    {
                        currentExplosions.get(i).reset();
                        currentExplosions.removeIndex(i);
                    }
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
                    reloadIcon.rotate(-Player.rotateSpeed);
                else
                    reloadIcon.setRotation(0);
                break;

            case DEAD:
                // deadHud.update(delta);
                break;
        }

        // UPDATE CAMERA
        APP.camera.update();

        // UPDATE
        APP.batch.setProjectionMatrix(APP.camera.combined);

        // --- RENDER ---

        APP.batch.begin();

        // RENDER BACKGROUND
        APP.batch.draw(bg, 0, 0, Constants.V_WIDTH, Constants.V_HEIGHT);

        if (Constants.STATUS == Constants.Status.PLAYING)
        {
            // RENDER ENEMIES
            for (Enemy enemy : Enemy.currentEnemies)
            {
                enemy.render(APP.batch);
                // System.out.println(enemy.getSprite().getHeight());
            }

            // RENDER PLAYER
            player.render(APP.batch);

            // RENDER BULLETS
            for (Bullet bullet : Bullet.currentBullets)
                bullet.render(APP.batch);

            // RENDER EXPLOSION(S)
            for (Explosion explosion : currentExplosions)
                explosion.render(APP.batch);

            // RENDER HEALTHBARS
            for (Enemy enemy : Enemy.currentEnemies)
                enemy.getHealthBar().render(APP.batch);

            // RENDER RELOAD ICON IF RELOADING
            if (!player.isReloading())
                ammoStatus.render(APP.batch, player.ammo.toString(), 110, 20 + ammoStatus.getHeight(player.ammo.toString()), false);
            else
                reloadIcon.draw(APP.batch);

            // RENDER WAVE STATUS
            ammoStatus.render(APP.batch, "wave " + wave.toString(), Constants.V_WIDTH / 2 - (ammoStatus.getWidth("wave " + wave.toString()) / 2), Constants.V_HEIGHT - 30, false);

            // SHOW FPS
            font.draw(APP.batch, Gdx.graphics.getFramesPerSecond() + " ", 50, 1250);
        }

        APP.batch.end();

        /* shapeRenderer.setProjectionMatrix(APP.camera.combined);

        System.out.println((int) player.getSprite().getX() + " " + (int) player.getBounds().getX());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        shapeRenderer.polygon(player.getBounds().getTransformedVertices());

        for (Enemy enemy : Enemy.currentEnemies)
            shapeRenderer.polygon(enemy.getBounds().getTransformedVertices());
        for (Bullet bullet : Bullet.currentBullets)
            shapeRenderer.polygon(bullet.getBounds().getTransformedVertices());

        shapeRenderer.end();

        //for (Bullet bullet : Bullet.currentBullets)
            //System.out.println((int)bullet.getSprite().getX() + " " + (int) bullet.getBounds().getX()); */

        switch (Constants.STATUS)
        {
            case PLAYING:
                gameHud.render();
                break;

            case DEAD:
                deadHud.render();
                break;
        }
    }

    private void startNewWave()
    {
        for (int i = 0; i < MathUtils.round(wave * WAVE_MULTIPLIER); i++)
        {
            Enemy.currentEnemies.add(Enemy.totalEnemies.get(i));
            Enemy.currentEnemies.get(i).setHealth(48);
            Enemy.currentEnemies.get(i).setPosition(Constants.getRandomPosition(MathUtils.random(0, 15), Enemy.currentEnemies.get(i).getSprite().getWidth(), Enemy.currentEnemies.get(i).getSprite().getHeight()));
            Enemy.currentEnemies.get(i).calculateRotation();
            Enemy.currentEnemies.get(i).calculateVelocity();
        }
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
