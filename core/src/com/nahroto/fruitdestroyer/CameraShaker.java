package com.nahroto.fruitdestroyer;

import com.badlogic.gdx.graphics.OrthographicCamera;

public class CameraShaker
{
    private static float oldPosX;
    private static float oldPosY;

    public static void shakeCamera(float amount, float duration, OrthographicCamera camera)
    {
        oldPosX = camera.position.x;
        oldPosY = camera.position.y;
        camera.translate(amount, amount);
        camera.update();
    }

    public static void resetCamera(OrthographicCamera camera)
    {
        camera.position.x = oldPosX;
        camera.position.y = oldPosY;
    }
}
