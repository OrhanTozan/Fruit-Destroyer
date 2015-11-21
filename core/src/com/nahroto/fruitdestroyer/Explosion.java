package com.nahroto.fruitdestroyer;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Explosion
{
    public static final int WIDTH = 144;
    private static final float ANIMATION_FPS = 20f;

    public static Array<Explosion> totalExplosions = new Array<Explosion>();
    public static Array<Explosion> currentExplosions = new Array<Explosion>();

    private Animation animation;
    private float elapsedTime;

    private Vector2 position;

    private float rotation;

    public static boolean isBusy;

    private Sound sound;

    public Explosion(TextureAtlas atlas, Sound sound)
    {
        animation = new Animation(1 / ANIMATION_FPS, atlas.getRegions(), Animation.PlayMode.NORMAL);
        position = new Vector2();

        this.sound = sound;

    }

    public void update(float delta)
    {
        elapsedTime += delta;
    }

    public void render(SpriteBatch batch)
    {
        batch.draw(animation.getKeyFrame(elapsedTime), position.x, position.y, 72, 72, 144, 144, 1, 1, rotation + 90, true);
    }

    public boolean isAnimationFinished()
    {
        return animation.isAnimationFinished(elapsedTime);
    }

    public void reset()
    {
        elapsedTime = 0;
    }

    public void setPosition(float x, float y)
    {
        position.set(x, y);
    }

    public void setRotation(float rotation)
    {
        this.rotation = rotation;
    }

    public Vector2 getPosition()
    {
        return position;
    }

    public Sound getSound()
    {
        return sound;
    }
}
