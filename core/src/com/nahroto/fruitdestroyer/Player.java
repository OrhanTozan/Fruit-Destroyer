package com.nahroto.fruitdestroyer;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player
{
    private Sprite sprite;

    public Player(Sprite sprite)
    {
        this.sprite = sprite;
    }

    public void update()
    {

    }

    public void render(SpriteBatch batch)
    {
        sprite.draw(batch);
    }
}
