package com.nahroto.fruitdestroyer;

import com.badlogic.gdx.math.Vector2;

public class Constants
{
    public static final int V_WIDTH = 720;
    public static final int V_HEIGHT = 1280;
    public static final Vector2[] randomPositions =
            {
                    new Vector2(0, V_HEIGHT / 3), // RANDOM POSITION #1
                    new Vector2(0, V_HEIGHT / 3 * 2), // RANDOM POSITION #2
                    
                    new Vector2(720, V_HEIGHT / 3), // RANDOM POSITION #3
                    new Vector2(720, V_HEIGHT / 3 * 2), // RANDOM POSITION #4
            }
}
