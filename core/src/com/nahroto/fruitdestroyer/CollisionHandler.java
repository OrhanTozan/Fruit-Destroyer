package com.nahroto.fruitdestroyer;

import com.badlogic.gdx.math.Intersector;
import com.nahroto.fruitdestroyer.entities.Bullet;
import com.nahroto.fruitdestroyer.entities.Player;
import com.nahroto.fruitdestroyer.entities.enemies.Enemy;
import com.nahroto.fruitdestroyer.screens.DeadScreen;
import com.nahroto.fruitdestroyer.screens.GameScreen;

public class CollisionHandler
{
    public void update(Application APP, Player player, GameScreen gameScreen)
    {
        for (int i = 0; i < Enemy.currentEnemies.size; i++)
        {
            bulletLoop:
            for (int j = 0; j < Bullet.currentBullets.size; j++)
            {
                // IF ENEMY COLLIDES WITH BULLET
                if (Intersector.overlapConvexPolygons(Enemy.currentEnemies.get(i).getBounds(), Bullet.currentBullets.get(j).getBounds()))
                {
                    // System.out.println(Enemy.currentEnemies.size);

                    // DAMAGE THE ENEMY
                    Enemy.currentEnemies.get(i).renderHit = true;
                    Enemy.currentEnemies.get(i).playSquishSound();
                    Enemy.currentEnemies.get(i).setHitTexture();
                    Enemy.currentEnemies.get(i).reduceHealth(Bullet.currentBullets.get(j).getDamage());

                    // REMOVE THE HIT BULLET
                    Bullet.currentBullets.get(j).isUsed = false;
                    Bullet.currentBullets.get(j).isOutOfScreen = false;
                    Bullet.currentBullets.removeIndex(j);
                    break bulletLoop;
                }
            }

            // IF ENEMY COLLIDES WITH PLAYER
            if (Intersector.overlapConvexPolygons(Enemy.currentEnemies.get(i).getBounds(), player.getBounds()))
                APP.setScreen(new DeadScreen(APP, gameScreen));
        }
    }
}
