package com.nahroto.fruitdestroyer.entities.enemies;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Orange extends Enemy
{
    public static final int COUNT = 30;

    public Orange(TextureAtlas.AtlasRegion normalTexture, TextureAtlas.AtlasRegion hitTexture, Sprite redBar, Sprite greenBar, int BOUNDING_X, int BOUNDING_Y, int BOUNDING_WIDTH, int BOUNDING_HEIGHT)
    {
        super(normalTexture, hitTexture, redBar, greenBar, BOUNDING_X, BOUNDING_Y, BOUNDING_WIDTH, BOUNDING_HEIGHT);
    }
}
