package com.nahroto.fruitdestroyer.helpers;

import com.badlogic.gdx.audio.Music;
import com.nahroto.fruitdestroyer.Constants;
import com.nahroto.fruitdestroyer.entities.Bullet;
import com.nahroto.fruitdestroyer.entities.Player;
import com.nahroto.fruitdestroyer.entities.enemies.Enemy;
import com.nahroto.fruitdestroyer.screens.GameScreen;

public class GameResetter
{
    public static void clearGame()
    {
        // CLEAR ALL ENEMIES
        Enemy.currentEnemies.clear();

        System.out.println(Enemy.currentEnemies.size);

        // CLEAR ALL BULLETS
        Bullet.currentBullets.clear();
    }

    public static void newGame(Music actionMusic)
    {
        Constants.STATUS = Constants.Status.PLAYING;

        // RESET WAVE
        GameScreen.wave = 1;

        // RESET PLAYER AMMO
        Player.ammo = Bullet.getWeapon().getMagSize();

        // START MUSIC AGAIN
        actionMusic.play();

        // START NEW WAVE
        GameScreen.startNewWave();
    }
}
