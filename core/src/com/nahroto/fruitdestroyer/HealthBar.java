package com.nahroto.fruitdestroyer;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HealthBar
{
    public static final int Y_OFFSET = 20;

    private Sprite red;
    private Sprite green;

    public HealthBar(Sprite red, Sprite green)
    {
        this.red = red;
        this.green = green;

        this.green.setPosition(this.red.getX() + 1, this.red.getY() + 1);
    }

    public void update(int health, float maxHealth)
    {
        updateGreen((health * 48) / maxHealth);
    }

    private void updateGreen(float health)
    {
        green.setSize(health, green.getHeight());
        green.setPosition(red.getX() + 1, red.getY() + 1);
    }

    public void render(SpriteBatch batch, int health)
    {
        if (health > 0)
        {
            red.draw(batch);
            green.draw(batch);
        }
    }

    public Sprite getRed()
    {
        return red;
    }
}
