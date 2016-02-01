package com.nahroto.fruitdestroyer;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class RandomPositioner
{
    private static final int MAX_DISTANCE = 100;

    private static Vector2 randomPosition = new Vector2();

    private static boolean positionIsUnder;
    private static float randomX;
    private static float randomY;

    public static Vector2 getRandomPosition(float height)
    {
        positionIsUnder = MathUtils.randomBoolean();

        randomX = MathUtils.random(-MAX_DISTANCE, Constants.V_WIDTH + MAX_DISTANCE);

        if (positionIsUnder)
            randomY = MathUtils.random(-MAX_DISTANCE, 0);
        else
            randomY = MathUtils.random(Constants.V_HEIGHT, Constants.V_HEIGHT + MAX_DISTANCE);

        if (positionIsUnder)
            randomPosition.set(randomX, randomY - height);
        else
            randomPosition.set(randomX, randomY);

        return randomPosition;
    }
}
