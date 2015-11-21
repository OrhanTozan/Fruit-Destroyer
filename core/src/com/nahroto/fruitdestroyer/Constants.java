package com.nahroto.fruitdestroyer;

import com.badlogic.gdx.math.Vector2;

public class Constants
{
    public static final boolean DEBUG = true;

    public static final int V_WIDTH = 720;
    public static final int V_HEIGHT = 1280;

    public enum Status
    {
        LOADINGSCREEN,
        LOADINGSCREEN2,
        MENUSCREEN,
        PLAYING,
        DEAD
    }

    public static Status STATUS;

    private static Vector2[] randomPositions = { new Vector2(), new Vector2(), new Vector2(), new Vector2(), new Vector2(),
            new Vector2(), new Vector2(), new Vector2(), new Vector2(), new Vector2(), new Vector2(), new Vector2(), new Vector2(),
            new Vector2(), new Vector2(), new Vector2() };

    public static Vector2 getRandomPosition(int index, float width, float height)
    {
        randomPositions[0].set(0 - width, 0 - (height / 2)); // RANDOM POSITION #1 (0)
        randomPositions[1].set(0 - height, V_HEIGHT / 4 - (height / 2)); // RANDOM POSITION #2 (1)
        randomPositions[2].set(0 - width, V_HEIGHT / 4 * 2 - (height / 2)); // RANDOM POSITION #3 (2)
        randomPositions[3].set(0 - height, V_HEIGHT / 4 * 3 - (height / 2)); // RANDOM POSITION #4 (3)
        randomPositions[4].set(0 - height, V_HEIGHT - (height / 2)); // RANDOM POSITION #5 (4)

        randomPositions[5].set(V_WIDTH, 0 - (height / 2)); // RANDOM POSITION #6 (5)
        randomPositions[6].set(V_WIDTH, V_HEIGHT / 4 - (height / 2)); // RANDOM POSITION #7 (6)
        randomPositions[7].set(V_WIDTH, V_HEIGHT / 4 * 2 - (height / 2)); // RANDOM POSITION #8 (7)
        randomPositions[8].set(V_WIDTH, V_HEIGHT / 4 * 3 - (height / 2)); // RANDOM POSITION #9 (8)
        randomPositions[9].set(V_WIDTH, V_HEIGHT - (height / 2)); // RANDOM POSITION #10 (9)

        randomPositions[10].set(V_WIDTH / 4 - (width / 2), 0 - height); // RANDOM POSITION #11 (10)
        randomPositions[11].set(V_WIDTH / 4 * 2 - (width / 2), 0 - height); // RANDOM POSITION #12 (11)
        randomPositions[12].set(V_WIDTH / 4 * 3 - (width / 2), 0 - height); // RANDOM POSITION #13 (12)

        randomPositions[13].set(V_WIDTH / 4 - (width / 2), V_HEIGHT); // RANDOM POSITION #14 (13)
        randomPositions[14].set(V_WIDTH / 4 * 2 - (width / 2), V_HEIGHT); // RANDOM POSITION #15 (14)
        randomPositions[15].set(V_WIDTH / 4 * 3 - (width / 2), V_HEIGHT); // RANDOM POSITION #16 (15)

        return randomPositions[index];
    }
}
