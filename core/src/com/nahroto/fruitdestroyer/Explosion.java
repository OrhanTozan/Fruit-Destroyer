package com.nahroto.fruitdestroyer;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Explosion
{
    private static final float ANIMATION_FPS = 30f;

    private Animation animation;
    private TextureRegion[] textureRegions;

    public Explosion(TextureRegion textureRegion)
    {
        textureRegions = new TextureRegion[15];
        for (int i = 0; i < 15; i++)
        {
            textureRegions[i] = new TextureRegion(textureRegion);
        }
        animation = new Animation(1/ANIMATION_FPS);
    }
}
