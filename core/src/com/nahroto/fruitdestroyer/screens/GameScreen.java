package com.nahroto.fruitdestroyer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.nahroto.fruitdestroyer.Application;
import com.nahroto.fruitdestroyer.CameraShaker;
import com.nahroto.fruitdestroyer.RandomPositioner;
import com.nahroto.fruitdestroyer.WaveGenerator;
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
import com.nahroto.fruitdestroyer.huds.BuyHud;
import com.nahroto.fruitdestroyer.huds.DeadHud;
import com.nahroto.fruitdestroyer.huds.GameHud;

public class GameScreen implements Screen
{
    private final Application APP;



    private Texture bg;
    private Player player;
    private InputMultiplexer inputMultiplexer;
    private InputHandler inputHandler;
    private Input input;
    private CollisionHandler collisionHandler;

    private GameHud gameHud;
    private BuyHud buyHud;

    private Font ammoStatus;
    private Music actionMusic;
    private Sprite reloadIcon;

    private Sound waveSFX;

    public static boolean buying;

    private ShapeRenderer shapeRenderer;

    public GameScreen(final Application APP, GameHud gameHud, BuyHud buyHud, Texture bg, Player player, InputMultiplexer inputMultiplexer, InputHandler inputHandler, Input input, CollisionHandler collisionHandler, Font ammoStatus, Music actionMusic, Sprite reloadIcon, Sound waveSFX)
    {
        this.APP = APP;
        this.gameHud = gameHud;
        this.buyHud = buyHud;
        this.bg = bg;
        this.player = player;
        this.inputMultiplexer = inputMultiplexer;
        this.inputHandler = inputHandler;
        this.input = input;
        this.collisionHandler = collisionHandler;
        this.ammoStatus = ammoStatus;
        this.actionMusic = actionMusic;
        this.reloadIcon = reloadIcon;
        this.waveSFX = waveSFX;
    }

    @Override
    public void show()
    {
        System.out.println("GAMESCREEN SHOW");

        inputMultiplexer.clear();
        inputMultiplexer.addProcessor(gameHud.getStage());
        inputMultiplexer.addProcessor(input);
        Gdx.input.setInputProcessor(inputMultiplexer);

        actionMusic.setLooping(true);
        actionMusic.setLooping(true);
        actionMusic.play();
        actionMusic.setLooping(true);

        APP.camera.setToOrtho(false, Constants.V_WIDTH, Constants.V_HEIGHT);
        APP.camera.update();

        reloadIcon.setPosition(110, 20);

        player.setReloadingConfig(100);

        player.ammo = Bullet.getWeapon().getMagSize();

        WaveGenerator.startNewWave();

        if (Constants.DEBUG)
            shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        /* if (Gdx.input.justTouched())
            buyHud.toggle(); */

        if (!GameScreen.buying)
        {
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
                }
            }

            // UPDATE COLLISION
            collisionHandler.update(APP, player, actionMusic);

            // UPDATE HEALTHBAR
            for (int i = 0; i < Enemy.currentEnemies.size; i++)
            {
                Enemy.currentEnemies.get(i).getHealthBar().getRed().setPosition((Enemy.currentEnemies.get(i).getSprite().getX() + (Enemy.currentEnemies.get(i).getSprite().getWidth() / 2)) - (Enemy.currentEnemies.get(i).getHealthBar().getRed().getWidth() / 2), Enemy.currentEnemies.get(i).getSprite().getY() - HealthBar.Y_OFFSET);
                Enemy.currentEnemies.get(i).getHealthBar().update(Enemy.currentEnemies.get(i).getHealth(), Enemy.currentEnemies.get(i).getMaxHealth());

                // IF ENEMY DIES
                if (Enemy.currentEnemies.get(i).getHealth() <= 0)
                {
                    // IF ENEMY IS EXPLODABLE
                    if (Enemy.currentEnemies.get(i).isExplodable())
                    {
                        // GET AN EXPLOSION THAT ISNT BUSY
                        for (int j = 0; j < Explosion.totalExplosions.size; j++)
                        {
                            if (!Explosion.totalExplosions.get(j).isBusy)
                            {
                                Explosion.totalExplosions.get(j).getSound().play();
                                Explosion.totalExplosions.get(j).setPosition(((Enemy.currentEnemies.get(i).getSprite().getX() + (Enemy.currentEnemies.get(i).getSprite().getWidth() / 2)) - (Explosion.WIDTH / 2)), ((Enemy.currentEnemies.get(i).getSprite().getY() + (Enemy.currentEnemies.get(i).getSprite().getHeight() / 2)) - (Explosion.WIDTH / 2)));
                                Explosion.totalExplosions.get(j).setRotation(Enemy.currentEnemies.get(i).getAngle());
                                Explosion.totalExplosions.get(j).isBusy = true;
                                Explosion.currentExplosions.add(Explosion.totalExplosions.get(j));
                                break;
                            }
                        }
                        CameraShaker.startShaking(3f, 750);
                    }

                    // REMOVE ENEMY
                    Enemy.currentEnemies.removeIndex(i);
                }
            }

            // WHEN WAVE IS CLEARED, START NEW WAVE
            if (Enemy.currentEnemies.size == 0)
            {
                System.out.println("NEW WAVE STARTED");
                waveSFX.play();
                WaveGenerator.wave++;
                WaveGenerator.startNewWave();
            }

            // UPDATE EXPLOSIONS
            for (int i = 0; i < Explosion.currentExplosions.size; i++)
            {
                Explosion.currentExplosions.get(i).update(delta);
                if (Explosion.currentExplosions.get(i).isAnimationFinished())
                {
                    Explosion.currentExplosions.get(i).isBusy = false;
                    Explosion.currentExplosions.get(i).reset();
                    Explosion.currentExplosions.removeIndex(i);
                }
            }

            // DO NOT SHOW RELOAD BUTTON WHEN AMMO IS FULL OR ALREADY RELOADING
            if (player.ammo == Bullet.getWeapon().getMagSize() || player.isReloading())
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

            CameraShaker.update(APP.camera);
        }

        else
        {
            buyHud.update(delta);
        }

        // UPDATE CAMERA
        APP.camera.update();

        // UPDATE
        APP.batch.setProjectionMatrix(APP.camera.combined);

        // --- RENDER ---

        APP.batch.begin();

        // RENDER BACKGROUND
        APP.batch.draw(bg, -80, -80);

        // RENDER ENEMIES
        for (Enemy enemy : Enemy.currentEnemies)
            enemy.render(APP.batch);

        // RENDER PLAYER
        player.render(APP.batch);

        // RENDER BULLETS
        for (Bullet bullet : Bullet.currentBullets)
            bullet.render(APP.batch);

        // RENDER EXPLOSION(S)
        for (Explosion currentExplosion : Explosion.currentExplosions)
            currentExplosion.render(APP.batch);

        // RENDER HEALTHBARS
        for (Enemy enemy : Enemy.currentEnemies)
            enemy.getHealthBar().render(APP.batch, enemy.getHealth());

        // RENDER RELOAD ICON IF RELOADING
        if (!player.isReloading())
            ammoStatus.render(APP.batch, player.ammo.toString(), 110, 20 + ammoStatus.getHeight(player.ammo.toString()), false);
        else
            reloadIcon.draw(APP.batch);

        // RENDER WAVE STATUS
        ammoStatus.render(APP.batch, "wave " + WaveGenerator.wave.toString(), Constants.V_WIDTH / 2 - (ammoStatus.getWidth("wave " + WaveGenerator.wave.toString()) / 2), Constants.V_HEIGHT - 30, false);

        APP.batch.end();

        if (Constants.DEBUG)
        {
            shapeRenderer.setProjectionMatrix(APP.camera.combined);

            System.out.println((int) player.getSprite().getX() + " " + (int) player.getBounds().getX());
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

            shapeRenderer.polygon(player.getBounds().getTransformedVertices());

            for (Enemy enemy : Enemy.currentEnemies)
                shapeRenderer.polygon(enemy.getBounds().getTransformedVertices());
            for (Bullet bullet : Bullet.currentBullets)
                shapeRenderer.polygon(bullet.getBounds().getTransformedVertices());

            shapeRenderer.end();
        }

        gameHud.render();

        if (GameScreen.buying == true)
            buyHud.render();

        if (Constants.DEBUG)
        {
            System.out.println("Total enemies: " + Enemy.totalEnemies.size + ", " + "current enemies: " + Enemy.currentEnemies.size);
            System.out.println("Total bullets: " + Bullet.totalBullets.size + ", " + "current bullets: " + Bullet.currentBullets.size);
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
