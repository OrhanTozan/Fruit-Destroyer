package com.nahroto.fruitdestroyer.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Corpse
{
    private static final float DEAD_TIME = 20f;

    private Sprite sprite;

    public boolean isDone;
    public boolean isBusy;

    private long startTime;

    public Corpse(TextureAtlas.AtlasRegion texture)
    {
        sprite = new Sprite(texture);

        isBusy = false;
        isDone = false;
    }

    public void setPosition(float x, float y)
    {
        sprite.setPosition(x, y);
    }

    public void setAngle(float angle)
    {
        sprite.setRotation(angle);
    }

    public void setStartTime()
    {
        this.startTime = System.currentTimeMillis();
    }

    public void update()
    {
        if (System.currentTimeMillis() - startTime > DEAD_TIME * 1000f)
            isDone = true;
    }


    public void render(SpriteBatch batch)
    {
        sprite.draw(batch);
    }
}
