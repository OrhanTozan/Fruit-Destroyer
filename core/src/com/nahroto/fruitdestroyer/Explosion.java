package com.nahroto.fruitdestroyer;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

public class Explosion
{
    private static final float ANIMATION_FPS = 30f;

    private Animation animation;
    private float elapsedTime;

    private Vector2 position;

    public Explosion(TextureAtlas atlas)
    {
        animation = new Animation(1 / ANIMATION_FPS, atlas.getRegions());
        position = new Vector2();
    }

    public void update(float delta)
    {
        elapsedTime += delta;
    }

    public void render(SpriteBatch batch)
    {
        batch.draw(animation.getKeyFrame(elapsedTime, false), position.x, position.y);
    }
}
