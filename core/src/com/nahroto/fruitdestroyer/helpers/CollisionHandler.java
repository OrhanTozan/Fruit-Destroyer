package com.nahroto.fruitdestroyer.helpers;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
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

    public CollisionHandler(DeadScreen deadScreen)
    {
        this.deadScreen = deadScreen;
    }

    public void update(Application APP, Player player, Music gameMusic)
    {
        for (int i = 0; i < Enemy.currentEnemies.size; i++)
        {
            if (Enemy.currentEnemies.get(i).isCollidable)
            {
                bulletLoop:
                for (int j = 0; j < Bullet.currentBullets.size; j++)
                {
                    // IF ENEMY COLLIDES WITH BULLET
                    if (Intersector.overlapConvexPolygons(Enemy.currentEnemies.get(i).getBounds(), Bullet.currentBullets.get(j).getBounds()))
                    {
                        // DAMAGE THE ENEMY
                        Enemy.currentEnemies.get(i).renderHit = true;
                        Enemy.currentEnemies.get(i).playSquishSound();
                        Enemy.currentEnemies.get(i).setHitTexture();
                        Enemy.currentEnemies.get(i).reduceHealth(Bullet.getWeapon().getDamage());

                        // REMOVE THE HIT BULLET
                        Bullet.currentBullets.get(j).isUsed = false;
                        Bullet.currentBullets.get(j).isOutOfScreen = false;
                        Bullet.currentBullets.removeIndex(j);
                        break bulletLoop;
                    }
                }

                // IF ENEMY COLLIDES WITH PLAYER
                if (Intersector.overlapConvexPolygons(Enemy.currentEnemies.get(i).getBounds(), player.getBounds()))
                {
                    Input.touchDown = false;
                    APP.setScreen(deadScreen);
                    gameMusic.stop();
                }
            }
        }
    }
}
