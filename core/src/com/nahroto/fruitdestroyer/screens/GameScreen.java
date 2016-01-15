package com.nahroto.fruitdestroyer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.nahroto.fruitdestroyer.Application;
import com.nahroto.fruitdestroyer.CameraShaker;
import com.nahroto.fruitdestroyer.Logger;
import com.nahroto.fruitdestroyer.WaveGenerator;
import com.nahroto.fruitdestroyer.entities.Corpse;
import com.nahroto.fruitdestroyer.entities.enemies.Ananas;
import com.nahroto.fruitdestroyer.entities.enemies.Orange;
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
import com.nahroto.fruitdestroyer.huds.GameHud;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;


public class GameScreen implements Screen
{
    private final Application APP;

    private WaveGenerator waveGenerator;

    private Array<Enemy> totalEnemies;
    private Array<Enemy> currentEnemies;
    private Array<Orange> totalOranges;
    private Array<Ananas> totalAnanases;

    private Array<Corpse> currentCorpses;
    private Array<Corpse> totalOrangeCorpses;
    private Array<Corpse> totalAnanasCorpses;

    private Array<Bullet> totalBullets;
    private Array<Bullet> currentBullets;

    private Array<Explosion> totalExplosions;
    private Array<Explosion> currentExplosions;

    private Texture bg;
    private Player player;
    private InputMultiplexer inputMultiplexer;
    private InputHandler inputHandler;
    private Input input;
    private CollisionHandler collisionHandler;

    private GameHud gameHud;
    private BuyHud buyHud;


    private Font ammoStatus;
    private Font accuracyStatus;
    private Music actionMusic;
    private Sprite reloadIcon;
    private Sprite accuracyIcon;

    private Sound waveSFX;

    public static boolean buying;

    private ShapeRenderer shapeRenderer;

    public GameScreen(final Application APP, WaveGenerator waveGenerator, Array<Enemy> totalEnemies, Array<Enemy> currentEnemies, Array<Orange> totalOranges, Array<Ananas> totalAnanases, Array<Corpse> currentCorpses, Array<Corpse> totalOrangeCorpses, Array<Corpse> totalAnanasCorpses, Array<Bullet> totalBullets, Array<Bullet> currentBullets,Array<Explosion> totalExplosions, Array<Explosion> currentExplosions, GameHud gameHud, BuyHud buyHud, Texture bg, Player player, InputMultiplexer inputMultiplexer, Font accuracyStatus, Font ammoStatus, InputHandler inputHandler, Input input, CollisionHandler collisionHandler, Music actionMusic, Sprite reloadIcon, Sound waveSFX, Sprite accuracyIcon)
    {
        this.APP = APP;
        this.waveGenerator = waveGenerator;
        this.gameHud = gameHud;
        this.buyHud = buyHud;
        this.bg = bg;
        this.player = player;
        this.inputMultiplexer = inputMultiplexer;
        this.inputHandler = inputHandler;
        this.input = input;
        this.collisionHandler = collisionHandler;
        this.ammoStatus = ammoStatus;
        this.accuracyStatus = accuracyStatus;
        this.actionMusic = actionMusic;
        this.reloadIcon = reloadIcon;
        this.waveSFX = waveSFX;
        this.totalEnemies = totalEnemies;
        this.currentEnemies = currentEnemies;
        this.totalOranges = totalOranges;
        this.totalAnanases = totalAnanases;
        this.currentCorpses = currentCorpses;
        this.totalOrangeCorpses = totalOrangeCorpses;
        this.totalAnanasCorpses = totalAnanasCorpses;
        this.totalBullets = totalBullets;
        this.currentBullets = currentBullets;
        this.totalExplosions = totalExplosions;
        this.currentExplosions = currentExplosions;
        this.accuracyIcon = accuracyIcon;
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
        accuracyIcon.setPosition(275, 15);

        player.setReloadingConfig(100);

        Player.ammo = Bullet.getWeapon().getMagSize();

        if (Constants.DEBUG)
            shapeRenderer = new ShapeRenderer();

        waveGenerator.wave = 1;
        waveGenerator.startNewWave();
    }

    private void update(float delta)
    {
        if (!GameScreen.buying)
        {
            // HANDLE INPUT
            inputHandler.update();

            // --- UPDATE ---

            // UPDATE PLAYER
            player.update();
            player.updateAccuracy();

            // UPDATE ENEMIES
            for (int i = 0; i < currentEnemies.size; i++)
                currentEnemies.get(i).update(delta);


            // UPDATE BULLETS
            for (int i = 0; i < currentBullets.size; i++)
            {
                currentBullets.get(i).update(delta);
                if (currentBullets.get(i).isOutOfScreen)
                {
                    currentBullets.get(i).isUsed = false;
                    currentBullets.get(i).isOutOfScreen = false;
                    currentBullets.removeIndex(i);
                }
            }

            // UPDATE COLLISION
            collisionHandler.update(APP, player, actionMusic, delta);

            // UPDATE HEALTHBAR
            for (int i = 0; i < currentEnemies.size; i++)
            {
                // IF ENEMY DIES
                if (currentEnemies.get(i).getHealth() <= 0)
                {
                    if (currentEnemies.get(i) instanceof Orange)
                    {
                        for (Corpse corpse : totalOrangeCorpses)
                        {
                            if (!corpse.isBusy)
                            {
                                corpse.setStartTime();
                                corpse.setPosition(currentEnemies.get(i).getX(), currentEnemies.get(i).getY());
                                corpse.setAngle(currentEnemies.get(i).getAngle());
                                corpse.isBusy = true;
                                currentCorpses.add(corpse);
                                break;
                            }
                        }
                    }
                    if (currentEnemies.get(i) instanceof Ananas)
                    {
                        for (Corpse corpse : totalAnanasCorpses)
                        {
                            if (!corpse.isBusy)
                            {
                                corpse.setStartTime();
                                corpse.setPosition(currentEnemies.get(i).getX(), currentEnemies.get(i).getY());
                                corpse.isBusy = true;
                                currentCorpses.add(corpse);
                                break;
                            }
                        }
                    }

                    // IF ENEMY IS EXPLODABLE
                    if (currentEnemies.get(i).isExplodable())
                    {
                        // GET AN EXPLOSION THAT ISNT BUSY
                        for (int j = 0; j < totalExplosions.size; j++)
                        {
                            if (!totalExplosions.get(j).isBusy)
                            {
                                totalExplosions.get(j).getSound().play();
                                totalExplosions.get(j).setPosition(((currentEnemies.get(i).getSprite().getX() + (currentEnemies.get(i).getSprite().getWidth() / 2)) - (Explosion.WIDTH / 2)), ((currentEnemies.get(i).getSprite().getY() + (currentEnemies.get(i).getSprite().getHeight() / 2)) - (Explosion.WIDTH / 2)));
                                totalExplosions.get(j).setRotation(currentEnemies.get(i).getAngle());
                                totalExplosions.get(j).isBusy = true;
                                currentExplosions.add(totalExplosions.get(j));
                                break;
                            }
                        }
                        gameHud.animateWhiteShader();
                        CameraShaker.startShaking(4f, 750);
                    }

                    currentEnemies.get(i).isUsed = false;
                    currentEnemies.removeIndex(i);
                }
            }

            waveGenerator.update();

            // UPDATE EXPLOSIONS
            for (int i = 0; i < currentExplosions.size; i++)
            {
                currentExplosions.get(i).update(delta);
                if (currentExplosions.get(i).isAnimationFinished())
                {
                    currentExplosions.get(i).isBusy = false;
                    currentExplosions.get(i).reset();
                    currentExplosions.removeIndex(i);
                }
            }

            // DO NOT SHOW RELOAD BUTTON WHEN AMMO IS FULL OR ALREADY RELOADING
            if (Player.ammo == Bullet.getWeapon().getMagSize() || player.isReloading())
                gameHud.getActors().get(0).remove();
            else
                gameHud.addAllActors();

            // UPDATE GAMEHUD
            gameHud.update(delta);

            // IF RELOADING ROTATE RELOAD ICON
            if (player.isReloading())
                reloadIcon.rotate(-Player.rotateSpeed);
            else
                reloadIcon.setRotation(0);

            for (int i = 0; i < currentEnemies.size; i++)
            {
                currentEnemies.get(i).getHealthBar().getRed().setPosition((currentEnemies.get(i).getSprite().getX() + (currentEnemies.get(i).getSprite().getWidth() / 2)) - (currentEnemies.get(i).getHealthBar().getRed().getWidth() / 2), currentEnemies.get(i).getSprite().getY() - HealthBar.Y_OFFSET);
                currentEnemies.get(i).getHealthBar().update(currentEnemies.get(i).getHealth(), currentEnemies.get(i).getMaxHealth());
            }

            for (int i = 0; i < currentCorpses.size; i++)
            {
                currentCorpses.get(i).update();
                if (currentCorpses.get(i).isDone)
                {
                    currentCorpses.get(i).isDone = false;
                    currentCorpses.removeIndex(i);
                }
            }

            CameraShaker.update(APP.camera);

            // WHEN WAVE IS CLEARED, START NEW WAVE
            if (currentEnemies.size == 0 && waveGenerator.getQueue().size == 0 && !gameHud.animatingWave)
            {
                gameHud.animateWaveLabel();
                if (waveGenerator.wave % WaveGenerator.BUY_WAVE == 0 && !gameHud.animatingWave)
                {
                    Input.touchDown = false;
                    buying = true;
                    GameHud.reloadingIsAllowed = false;
                    buyHud.update(delta);
                    buyHud.turnON();
                    Logger.log("BUYTIME");
                }
                else
                {
                    Logger.log("freedom");
                }
            }
        }

        else
        {
            buyHud.update(delta);
            for (int i = 0; i < currentEnemies.size; i++)
            {
                currentEnemies.get(i).getHealthBar().getRed().setPosition((currentEnemies.get(i).getSprite().getX() + (currentEnemies.get(i).getSprite().getWidth() / 2)) - (currentEnemies.get(i).getHealthBar().getRed().getWidth() / 2), currentEnemies.get(i).getSprite().getY() - HealthBar.Y_OFFSET);
                currentEnemies.get(i).getHealthBar().update(currentEnemies.get(i).getHealth(), currentEnemies.get(i).getMaxHealth());
            }
        }

        // Logger.log(Gdx.graphics.getFramesPerSecond());

        // UPDATE CAMERA
        APP.camera.update();

        // UPDATE
        APP.batch.setProjectionMatrix(APP.camera.combined);
    }



    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        // --- RENDER ---
        APP.batch.begin();

        // RENDER BACKGROUND
        APP.batch.draw(bg, -80, -80);

        for (Corpse corpse : currentCorpses)
            corpse.render(APP.batch);

        // RENDER ENEMIES
        for (Enemy enemy : currentEnemies)
            enemy.render(APP.batch);

        // RENDER PLAYER
        player.render(APP.batch);

        // RENDER BULLETS
        for (Bullet bullet : currentBullets)
            bullet.render(APP.batch);

        // RENDER EXPLOSION(S)
        for (Explosion currentExplosion : currentExplosions)
            currentExplosion.render(APP.batch);

        // RENDER HEALTHBARS
        for (Enemy enemy : currentEnemies)
        {
            if (enemy.isUsed && enemy.getHealth() > 0)
                enemy.getHealthBar().render(APP.batch, enemy.getHealth());
        }

        // RENDER RELOAD ICON IF RELOADING
        if (!player.isReloading())
            ammoStatus.render(APP.batch, Player.ammo.toString(), 110, 20 + ammoStatus.getHeight(Player.ammo.toString()), false);
        else
            reloadIcon.draw(APP.batch);

        // RENDER WAVE STATUS
        // ammoStatus.render(APP.batch, "wave " + WaveGenerator.wave.toString(), Constants.V_WIDTH / 2 - (ammoStatus.getWidth("wave " + WaveGenerator.wave.toString()) / 2), Constants.V_HEIGHT - 30, false);
        accuracyStatus.render(APP.batch, MathUtils.round((100 - (100 * Player.spread))) + "%", Constants.V_WIDTH / 2 - accuracyStatus.getWidth(MathUtils.round((100 - (100 * Player.spread))) + "%") / 2 + 30, 60, false);
        accuracyIcon.draw(APP.batch);


        APP.batch.end();

        if (Constants.DEBUG)
        {
            shapeRenderer.setProjectionMatrix(APP.camera.combined);

            System.out.println((int) player.getSprite().getX() + " " + (int) player.getBounds().getX());
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

            shapeRenderer.polygon(player.getBounds().getTransformedVertices());

            for (Enemy enemy : currentEnemies)
                shapeRenderer.polygon(enemy.getBounds().getTransformedVertices());
            for (Bullet bullet : currentBullets)
                shapeRenderer.polygon(bullet.getBounds().getTransformedVertices());

            shapeRenderer.end();
        }


        gameHud.render();


        if (GameScreen.buying == true)
            buyHud.render();

        if (Constants.DEBUG)
            System.out.println("Total bullets: " + totalBullets.size + ", " + "current bullets: " + currentBullets.size);
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
        Logger.log("seuefea");
    }
}
