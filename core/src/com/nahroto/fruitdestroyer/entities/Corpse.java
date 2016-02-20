package com.nahroto.fruitdestroyer.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Corpse
{
    private Sprite sprite;

    public boolean isBusy = false;


    public Corpse(TextureAtlas.AtlasRegion texture)
    {
        sprite = new Sprite(texture);
    }

    public void setPosition(float x, float y)
    {
        sprite.setPosition(x, y);
    }

    public void setAngle(float angle)
    {
        sprite.setRotation(angle);
    }

    public void render(SpriteBatch batch)
    {
        sprite.draw(batch);
    }

    public Sprite getSprite()
    {
        return sprite;
    }
}
