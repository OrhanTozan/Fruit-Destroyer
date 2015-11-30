package com.nahroto.fruitdestroyer.helpers;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.Intersector;
import com.nahroto.fruitdestroyer.Application;
import com.nahroto.fruitdestroyer.entities.Bullet;
import com.nahroto.fruitdestroyer.entities.Player;
import com.nahroto.fruitdestroyer.entities.enemies.Enemy;
import com.nahroto.fruitdestroyer.huds.DeadHud;
import com.nahroto.fruitdestroyer.screens.DeadScreen;

public class CollisionHandler
{
    private DeadScreen deadScreen;

    public CollisionHandler(final Application APP)
    {
        deadScreen = new DeadScreen(APP);
    }

    public void update(Application APP, Player player, Music gameMusic, DeadHud deadHud)
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
                GameResetter.clearGame();
                deadHud.addAllActorsToStage();
                APP.setScreen(randomScreen);
                // Constants.STATUS = Constants.Status.DEAD;
                gameMusic.stop();
            }
        }
    }
}
