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

    public GameResetter(final Application APP)
    {
        this.APP = APP;
    }

    public void newGame()
    {
        // CLEAR ALL ENEMIES
        Enemy.currentEnemies.clear();

        // CLEAR ALL BULLETS
        Bullet.currentBullets.clear();

        // RESET WAVE
        GameScreen.wave = 1;

        APP.setScreen(gameScreen);
    }

    public void setGameScreen(GameScreen gameScreen)
    {
        this.gameScreen = gameScreen;
    }
}
