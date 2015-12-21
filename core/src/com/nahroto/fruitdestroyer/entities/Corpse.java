package com.nahroto.fruitdestroyer.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Corpse
{
    public static Array<Corpse> currentCorpses = new Array<Corpse>();

    private Sprite sprite;
    private Vector2 position;

    public Corpse(TextureAtlas.AtlasRegion texture)
    {
        sprite = new Sprite(texture);
    }

    public void setPosition(float x, float y)
    {
        position.set(x, y);
    }

    public void render(SpriteBatch batch)
    {
        sprite.draw(batch);
    }
}
