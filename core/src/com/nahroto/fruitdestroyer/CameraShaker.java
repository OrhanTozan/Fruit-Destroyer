package com.nahroto.fruitdestroyer;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;

public class CameraShaker
{
    public static boolean busy;

    private static final int RNG = 5;

    private static long startTime;
    private static float multiplier;
    private static float duration;

    public static void update(OrthographicCamera camera)
    {
        if (System.currentTimeMillis() - startTime <= duration)
        {
            busy = true;
            camera.position.x = Constants.V_WIDTH / 2 + (MathUtils.random(-RNG, RNG) * multiplier);
            camera.position.y = Constants.V_HEIGHT / 2 + (MathUtils.random(-RNG, RNG) * multiplier);
        }

        else
        {
            busy = false;
            camera.position.x = Constants.V_WIDTH / 2;
            camera.position.y = Constants.V_HEIGHT / 2;
        }

        camera.update();
    }

    public static void startShaking(float multiplier, float duration)
    {
        startTime = System.currentTimeMillis();
        CameraShaker.multiplier = multiplier;
        CameraShaker.duration = duration;
    }
}
