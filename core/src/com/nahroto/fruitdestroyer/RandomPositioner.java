package com.nahroto.fruitdestroyer;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class RandomPositioner
{
    private static final int MAX_DISTANCE = 300;

    private static Vector2 randomPosition = new Vector2();

    private static boolean underPosition;
    private static float randomX;
    private static float randomY;

    public static Vector2 getRandomPosition(float height)
    {
        underPosition = MathUtils.randomBoolean();

        randomX = MathUtils.random(-MAX_DISTANCE, Constants.V_WIDTH + MAX_DISTANCE);

        if (underPosition)
            randomY = MathUtils.random(-MAX_DISTANCE, 0);
        else
            randomY = MathUtils.random(Constants.V_HEIGHT, Constants.V_HEIGHT + MAX_DISTANCE);

        if (underPosition)
            randomPosition.set(randomX, randomY - height);
        else
            randomPosition.set(randomX, randomY);

        return randomPosition;
    }
}
