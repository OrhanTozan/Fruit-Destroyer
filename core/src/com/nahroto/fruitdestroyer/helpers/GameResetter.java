package com.nahroto.fruitdestroyer.helpers;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Array;
import com.nahroto.fruitdestroyer.Application;
import com.nahroto.fruitdestroyer.Constants;
import com.nahroto.fruitdestroyer.WaveGenerator;
import com.nahroto.fruitdestroyer.entities.Bullet;
import com.nahroto.fruitdestroyer.entities.Player;
import com.nahroto.fruitdestroyer.entities.enemies.Enemy;
import com.nahroto.fruitdestroyer.screens.GameScreen;

public class GameResetter
{
    private final Application APP;
    private GameScreen gameScreen;

    private Array<Enemy> currentEnemies;
    private Array<Bullet> currentBullets;

    public GameResetter(final Application APP, Array<Enemy> currentEnemies, Array<Bullet> currentBullets)
    {
        this.APP = APP;
        this.currentEnemies= currentEnemies;
        this.currentBullets = currentBullets;
    }

    public void newGame()
    {
        // CLEAR ALL ENEMIES
        currentEnemies.clear();

        // CLEAR ALL BULLETS
        currentBullets.clear();

        // RESET WAVE
        WaveGenerator.wave = 1;

        APP.setScreen(gameScreen);
    }

    public void setGameScreen(GameScreen gameScreen)
    {
        this.gameScreen = gameScreen;
    }
}
