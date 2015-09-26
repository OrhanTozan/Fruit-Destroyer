package com.nahroto.fruitdestroyer;

import com.badlogic.gdx.math.Vector2;

public class Constants
{
    public static final int V_WIDTH = 720;
    public static final int V_HEIGHT = 1280;


    private static Vector2[] randomPositions = new Vector2[8];

    public static Vector2 getRandomPosition(int index, float width, float height)
    {
        randomPositions[0].set(0 - width, V_HEIGHT / 3 - (height / 2)); // RANDOM POSITION #1 (0)
        randomPositions[1].set(0 - height, V_HEIGHT / 3 * 2 - (height / 2)); // RANDOM POSITION #2 (1)

        randomPositions[2].set(V_WIDTH, V_HEIGHT / 3 - (height / 2)); // RANDOM POSITION #3 (2)
        randomPositions[3].set(V_WIDTH, V_HEIGHT / 3 * 2 - (height / 2)); // RANDOM POSITION #4 (3)

        randomPositions[4].set(V_WIDTH / 3 - (width / 2), 0 - height); // RANDOM POSITION #5 (4)
        randomPositions[5].set(V_WIDTH / 3 * 2 - (width / 2), 0 - height); // RANDOM POSITION #6 (5)

        randomPositions[6].set(V_WIDTH / 3 - (width / 2), V_HEIGHT); // RANDOM POSITION #7 (6)
        randomPositions[7].set(V_WIDTH / 3 * 2 - (width / 2), V_HEIGHT); // RANDOM POSITION #8 (7)

        return randomPositions[index];
    }
}
