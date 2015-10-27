package com.nahroto.fruitdestroyer;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

public class Explosion
{
    private static final float ANIMATION_FPS = 20f;
    private static final boolean LOOPING = false;

    private Animation animation;
    private float elapsedTime;

    private Vector2 position;

    public Explosion(TextureAtlas atlas)
    {
        animation = new Animation(1 / ANIMATION_FPS, atlas.getRegions(), Animation.PlayMode.NORMAL);
        position = new Vector2();
    }

    public void update(float delta)
    {
        elapsedTime += delta;
    }

    public void render(SpriteBatch batch)
    {
        batch.draw(animation.getKeyFrame(elapsedTime), position.x, position.y);
    }

    public boolean isAnimationFinished()
    {
        return animation.isAnimationFinished(elapsedTime);
    }

    public void reset()
    {
        elapsedTime = 0;
    }

    public void setPosition(Vector2 position)
    {
        this.position = position;
    }

    public Vector2 getPosition()
    {
        return position;
    }
}
