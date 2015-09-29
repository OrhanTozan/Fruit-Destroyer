package com.nahroto.fruitdestroyer;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HealthBar
{
    private Sprite red;
    private Sprite green;

    public HealthBar(Sprite red, Sprite green)
    {
        this.red = red;
        this.green = green;

        this.green.setPosition(this.red.getX() + 1, this.red.getY() + 1);
    }

    public void update(int health)
    {
        updateGreen(health);
    }

    private void updateGreen(int health)
    {
        green.setSize(health, green.getHeight());
        green.setPosition(red.getX() + 1, red.getY() + 1);
    }

    public void render(SpriteBatch batch)
    {
        red.draw(batch);
        green.draw(batch);
    }
}
