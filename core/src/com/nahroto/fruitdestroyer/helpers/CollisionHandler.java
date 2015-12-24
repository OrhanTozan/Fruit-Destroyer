package com.nahroto.fruitdestroyer.helpers;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.Array;
import com.nahroto.fruitdestroyer.Application;
import com.nahroto.fruitdestroyer.Input;
import com.nahroto.fruitdestroyer.InputHandler;
import com.nahroto.fruitdestroyer.entities.Bullet;
import com.nahroto.fruitdestroyer.entities.Player;
import com.nahroto.fruitdestroyer.entities.enemies.Enemy;
import com.nahroto.fruitdestroyer.huds.DeadHud;
import com.nahroto.fruitdestroyer.screens.DeadScreen;
import com.nahroto.fruitdestroyer.screens.GameScreen;

public class CollisionHandler
{

    private DeadScreen deadScreen;
    private Array<Enemy> currentEnemies;
    private Array<Bullet> currentBullets;

    public CollisionHandler(DeadScreen deadScreen, Array<Enemy> currentEnemies, Array<Bullet> currentBullets)
    {
        this.deadScreen = deadScreen;
        this.currentEnemies = currentEnemies;
        this.currentBullets = currentBullets;
    }

    public void update(Application APP, Player player, Music gameMusic)
    {
        for (int i = 0; i < currentEnemies.size; i++)
        {
            bulletLoop:
            for (int j = 0; j < currentBullets.size; j++)
            {
                // IF ENEMY COLLIDES WITH BULLET
                if (Intersector.overlapConvexPolygons(currentEnemies.get(i).getBounds(), currentBullets.get(j).getBounds()))
                {
                    // DAMAGE THE ENEMY
                    currentEnemies.get(i).renderHit = true;
                    currentEnemies.get(i).playSquishSound();
                    currentEnemies.get(i).setHitTexture();
                    currentEnemies.get(i).reduceHealth(Bullet.getWeapon().getDamage());

                    // REMOVE THE HIT BULLET
                    currentBullets.get(j).isUsed = false;
                    currentBullets.get(j).isOutOfScreen = false;
                    currentBullets.removeIndex(j);
                    break bulletLoop;
                }
            }

            // IF ENEMY COLLIDES WITH PLAYER
            if (Intersector.overlapConvexPolygons(currentEnemies.get(i).getBounds(), player.getBounds()))
            {
                Input.touchDown = false;
                APP.setScreen(deadScreen);
                gameMusic.stop();
            }
        }
    }
}
