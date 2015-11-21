package com.nahroto.fruitdestroyer.entities.enemies;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.nahroto.fruitdestroyer.Application;

public class Ananas extends Enemy
{
    public static final int COUNT = 10;

    public Ananas(final Application APP, TextureAtlas.AtlasRegion normalTexture, TextureAtlas.AtlasRegion hitTexture, Sprite redBar, Sprite greenBar)
    {
        super(APP, normalTexture, hitTexture, redBar, greenBar, 21, 15, 98, 155);
        explodable = true;
        velocityMultiplier = 0.75f;
    }
}
