package com.nahroto.fruitdestroyer.entities.enemies;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.nahroto.fruitdestroyer.Application;

public class Ananas extends Enemy
{
    public static Array<Ananas> totalAnanases = new Array<Ananas>();

    public Ananas(final Application APP, TextureAtlas.AtlasRegion normalTexture, TextureAtlas.AtlasRegion hitTexture, Sprite redBar, Sprite greenBar)
    {
        super(APP, normalTexture, hitTexture, redBar, greenBar, 21, 15, 98, 155);

        maxHealth = 50;
        health = maxHealth;
        explodable = true;
        velocityMultiplier = 1f;
    }
}
