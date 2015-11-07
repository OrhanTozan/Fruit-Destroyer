package com.nahroto.fruitdestroyer.entities.enemies;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.nahroto.fruitdestroyer.Application;

public class Ananas extends Enemy
{
    public Ananas(final Application APP, TextureAtlas.AtlasRegion normalTexture, TextureAtlas.AtlasRegion hitTexture, Sprite redBar, Sprite greenBar)
    {
        super(APP, normalTexture, hitTexture, redBar, greenBar, 25, 14, 95, 103);
        explodable = true;
    }
}
