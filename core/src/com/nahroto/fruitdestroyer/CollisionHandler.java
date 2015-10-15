package com.nahroto.fruitdestroyer;

import com.nahroto.fruitdestroyer.entities.Bullet;
import com.nahroto.fruitdestroyer.entities.Player;
import com.nahroto.fruitdestroyer.entities.enemies.Enemy;

public class CollisionHandler
{
    public void update(Player player)
    {
        for (int i = 0; i < Enemy.currentEnemies.size; i++)
        {
            bulletLoop:
            for (int j = 0; j < Bullet.currentBullets.size; j++)
            {
                if (Enemy.currentEnemies.get(i).getBounds().overlaps(Bullet.currentBullets.get(j).getBounds()))
                {
                    // System.out.println(Enemy.currentEnemies.size);

                    Enemy.currentEnemies.get(i).renderHit = true;
                    Enemy.currentEnemies.get(i).playSquishSound();
                    Enemy.currentEnemies.get(i).setHitTexture();
                    Enemy.currentEnemies.get(i).reduceHealth(Bullet.currentBullets.get(j).getDamage());

                    Bullet.currentBullets.get(j).isUsed = false;
                    Bullet.currentBullets.get(j).isOutOfScreen = false;
                    Bullet.currentBullets.removeIndex(j);
                    break bulletLoop;
                }
            }

            // if (Enemy.currentEnemies.get(i).getBounds().overlaps(player.getBounds()))

        }
    }
}
