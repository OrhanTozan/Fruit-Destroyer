package com.nahroto.fruitdestroyer;

import com.nahroto.fruitdestroyer.entities.Bullet;
import com.nahroto.fruitdestroyer.entities.Player;

public class InputHandler
{
    private Player player;
    private long lastShotTime;

    public InputHandler(Player player)
    {
        this.player = player;
    }

    public void update()
    {
        if (Input.touchDown)
        {
            player.followFinger();
            if (System.currentTimeMillis() - lastShotTime > Bullet.rateOfFire)
            {
                lastShotTime = System.currentTimeMillis();
                player.shoot();
            }
        }
    }
}
