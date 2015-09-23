package com.nahroto.fruitdestroyer.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.nahroto.fruitdestroyer.Constants;

public class Bullet
{
    public static final int COUNT = 30;
    public static final int RATE_OF_FIRE = 200;
    public static final int VELOCITY = 700;

    public static Array<Bullet> totalBullets = new Array<Bullet>();
    public static Array<Bullet> currentBullets = new Array<Bullet>();

    private Vector2 position;
    private Vector2 velocity;
    private Sprite sprite;
    private Rectangle bounds;

    public boolean isOutOfScreen;
    public boolean isUsed;

    public Bullet(Sprite sprite)
    {
        this.sprite = sprite;
        position = new Vector2();
        velocity = new Vector2();
        bounds = new Rectangle(position.x, position.y, sprite.getWidth(), sprite.getHeight());
        isOutOfScreen = false;
        isUsed = false;
    }


    public void update(float delta)
    {
        updateBounds();
        applyVelocityToPosition(delta);
        isOutOfScreen();
    }

    private void applyVelocityToPosition(float delta)
    {
        velocity.scl(delta);
        position.add(velocity);
        velocity.scl(1 / delta);
    }

    private void isOutOfScreen()
    {
        if (position.x > Constants.V_WIDTH || position.x  + sprite.getWidth() < 0 || position.y > Constants.V_HEIGHT || position.y  + sprite.getHeight()< 0)
            isOutOfScreen = true;
        else isOutOfScreen = false;
    }

    private void updateBounds()
    {
        bounds.setPosition(this.position);
    }

    public void render(SpriteBatch batch)
    {
        batch.draw(sprite, position.x, position.y);
    }


    public void setPosition(float x, float y)
    {
        position.set(x, y);
    }

    public Vector2 getPosition()
    {
        return position;
    }

    public void setVelocity(float x, float y)
    {
        velocity.set(x, y);
    }
}
