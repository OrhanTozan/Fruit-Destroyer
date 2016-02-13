package com.nahroto.fruitdestroyer.screens;

import com.badlogic.gdx.graphics.Color;
import com.nahroto.fruitdestroyer.Application;
import com.nahroto.fruitdestroyer.Font;

public class BasicLoadingScreen
{
    protected Font font;

    public BasicLoadingScreen(final Application APP)
    {
        font = new Font(APP, "loadingscreen.otf", "fonts/trompus.otf", 90, Color.WHITE, true);
    }
}
