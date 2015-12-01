package com.nahroto.fruitdestroyer.helpers;

import com.badlogic.gdx.audio.Music;
import com.nahroto.fruitdestroyer.Application;
import com.nahroto.fruitdestroyer.Constants;
import com.nahroto.fruitdestroyer.entities.Bullet;
import com.nahroto.fruitdestroyer.entities.Player;
import com.nahroto.fruitdestroyer.entities.enemies.Enemy;
import com.nahroto.fruitdestroyer.screens.GameScreen;

public class GameResetter
{
    private final Application APP;
    private GameScreen gameScreen;

    public GameResetter(final Application APP, GameScreen gameScreen)
    {
        this.APP = APP;
        this.gameScreen = gameScreen;
    }

    public void newGame()
    {
        // CLEAR ALL ENEMIES
        Enemy.currentEnemies.clear();

        GameScreen.wave = 1;

        // CLEAR ALL BULLETS
        Bullet.currentBullets.clear();
        APP.setScreen(gameScreen);
    }

    public void setGameScreen(GameScreen gameScreen)
    {
        this.gameScreen = gameScreen;
    }
}
