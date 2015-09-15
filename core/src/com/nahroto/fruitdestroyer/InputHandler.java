package com.nahroto.fruitdestroyer;

import com.nahroto.fruitdestroyer.entities.Bullet;
import com.nahroto.fruitdestroyer.entities.Player;

public class InputHandler
{
    private final Application APP;
    private Player player;
    private long lastShotTime;

    public InputHandler(final Application APP, Player player)
    {
        this.APP = APP;
        this.player = player;
    }

    public void update()
    {
        if (Input.touchDown)
        {
            player.followFinger();
            if (System.currentTimeMillis() - lastShotTime > Bullet.RATE_OF_FIRE)
            {
                lastShotTime = System.currentTimeMillis();
                player.shoot();
            }
        }
    }
}
