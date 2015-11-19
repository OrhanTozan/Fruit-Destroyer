package com.nahroto.fruitdestroyer.helpers;

import com.badlogic.gdx.audio.Music;
import com.nahroto.fruitdestroyer.entities.Bullet;
import com.nahroto.fruitdestroyer.entities.Player;
import com.nahroto.fruitdestroyer.entities.enemies.Enemy;
import com.nahroto.fruitdestroyer.screens.GameScreen;

public class GameResetter
{
    public static void resetGame(Music actionMusic)
    {
        // RESET WAVE
        GameScreen.wave = 1;

        // CLEAR ALL ENEMIES
        for (int i = 0; i < Enemy.currentEnemies.size; i++)
            Enemy.currentEnemies.removeIndex(i);

        // CLEAR ALL BULLETS
        for (int i = 0; i < Bullet.currentBullets.size; i++)
            Bullet.currentBullets.removeIndex(i);

        // RESET AMMO
        Player.ammo = 30;

        // RESTART MUSIC
        actionMusic.play();
    }
}
