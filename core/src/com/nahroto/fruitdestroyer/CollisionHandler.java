package com.nahroto.fruitdestroyer;

import com.nahroto.fruitdestroyer.entities.Bullet;
import com.nahroto.fruitdestroyer.entities.enemies.Enemy;

public class CollisionHandler
{
    public void update()
    {
        for (int i = 0; i < Enemy.currentEnemies.size; i++)
        {
            bulletLoop:
            for (int j = 0; j < Bullet.currentBullets.size; j++)
            {
                if (Enemy.currentEnemies.get(i).getBounds().overlaps(Bullet.currentBullets.get(j).getBounds()))
                {
                    System.out.println(Enemy.currentEnemies.size);
                    System.out.println(Enemy.currentEnemies.size);
                    Enemy.currentEnemies.get(i).setPosition(Constants.getRandomPosition(i, Enemy.currentEnemies.get(i).getSprite().getWidth(), Enemy.currentEnemies.get(i).getSprite().getHeight()));

                    Bullet.currentBullets.get(j).isUsed = false;
                    Bullet.currentBullets.get(j).isOutOfScreen = false;
                    Bullet.currentBullets.removeIndex(j);
                    break bulletLoop;
                }
            }
        }
    }
}
