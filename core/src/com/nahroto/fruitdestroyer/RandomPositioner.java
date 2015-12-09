package com.nahroto.fruitdestroyer;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class RandomPositioner
{
    private static final int MIN_BOUND = 200;
    private static final int MAX_BOUND = 300;

    private static Vector2 randomPosition = new Vector2();

    private static boolean leftPosition;
    private static boolean underPosition;
    private static float randomX;
    private static float randomY;

    public static Vector2 getRandomPosition()
    {
        leftPosition = MathUtils.randomBoolean();
        underPosition = MathUtils.randomBoolean();

        if (leftPosition)
            randomX = MathUtils.random(-MAX_BOUND, -MIN_BOUND);
        else
            randomX = MathUtils.random(Constants.V_WIDTH + MIN_BOUND, Constants.V_WIDTH + MAX_BOUND);

        if (underPosition)
            randomY = MathUtils.random(-MAX_BOUND, -MIN_BOUND);
        else
            randomY = MathUtils.random(Constants.V_HEIGHT + MIN_BOUND, Constants.V_HEIGHT + MAX_BOUND);

        randomPosition.set(randomX, randomY);

        return randomPosition;
    }
}
