package com.nahroto.fruitdestroyer.entities.enemies;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.nahroto.fruitdestroyer.Application;

public class Watermelon extends Enemy
{
    public Watermelon(final Application APP, TextureAtlas.AtlasRegion normalTexture, TextureAtlas.AtlasRegion hitTexture, Sprite redBar, Sprite greenBar)
    {
        super(APP, normalTexture, hitTexture, redBar, greenBar, 20, 20, 326, 319);
        maxHealth = 200;
        health = maxHealth;
        explodable = false;
        velocityMultiplier = 0.7f;
        knockbackMultiplier = 0f;
    }
}
