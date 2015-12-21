package com.nahroto.fruitdestroyer.entities.enemies;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.nahroto.fruitdestroyer.Application;

public class Orange extends Enemy
{
    public static Array<Orange> totalOranges = new Array<Orange>();

    public Orange(final Application APP, TextureAtlas.AtlasRegion normalTexture, TextureAtlas.AtlasRegion hitTexture, TextureAtlas.AtlasRegion deadTexture, Sprite redBar, Sprite greenBar)
    {
        super(APP, normalTexture, hitTexture, deadTexture, redBar, greenBar, 17, 10, 63, 60);

        maxHealth = 15;
        health = maxHealth;
        explodable = false;
        velocityMultiplier = 2f;
    }
}
