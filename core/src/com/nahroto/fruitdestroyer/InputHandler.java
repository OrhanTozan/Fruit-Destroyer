package com.nahroto.fruitdestroyer;

import com.nahroto.fruitdestroyer.entities.Player;

public class InputHandler
{
    private final Application APP;
    private Player player;

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
            player.shoot();
        }
    }
}
