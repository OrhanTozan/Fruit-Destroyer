package com.nahroto.fruitdestroyer.screens;

import com.badlogic.gdx.graphics.Color;
import com.nahroto.fruitdestroyer.Font;

public class AbstractLoadingScreen
{
    protected Font font;

    public AbstractLoadingScreen()
    {
        font = new Font("fonts/trompus.otf", 90, Color.WHITE, true);
    }
}
