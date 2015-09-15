package com.nahroto.fruitdestroyer;

import com.badlogic.gdx.InputProcessor;

public class Input implements InputProcessor
{

    public static boolean touchDown;

    @Override
    public boolean keyDown(int keycode)
    {
        return true;
    }

    @Override
    public boolean keyUp(int keycode)
    {
        return true;
    }

    @Override
    public boolean keyTyped(char character)
    {
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        touchDown = true;
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        touchDown = false;
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY)
    {
        return true;
    }

    @Override
    public boolean scrolled(int amount)
    {
        return true;
    }
}
