package com.nahroto.fruitdestroyer.entities.enemies;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Enemy
{
    private static final int BOUNDING_X = 17;
    private static final int BOUNDING_Y = 10;
    private static final int BOUNDING_WIDTH = 63;
    private static final int BOUNDING_HEIGHT = 60;

    private float angle;

    private Vector2 velocity;
    private Vector2 position;

    private Sprite sprite;
    private Rectangle bounds;

    public Enemy(Sprite sprite)
    {
        this.sprite = sprite;
        velocity = new Vector2();
        position = new Vector2();
        bounds = new Rectangle();

        bounds.set(sprite.getX() + BOUNDING_X, sprite.getY() + BOUNDING_Y, BOUNDING_WIDTH, BOUNDING_HEIGHT);
        position.set(sprite.getX(), sprite.getY());
    }

    public void update(float delta)
    {
        applyVelocityToPosition(delta);
        updateBounds();
    }

    public void render(SpriteBatch batch)
    {
        sprite.draw(batch);
    }

    protected void applyVelocityToPosition(float delta)
    {
        velocity.scl(delta);
        sprite.translate(velocity.x, velocity.y);
        velocity.scl(1 / delta);
    }

    protected void updateBounds()
    {
        bounds.setPosition(sprite.getX() + BOUNDING_X, sprite.getY() + BOUNDING_Y);
    }

    public void setPosition(float x, float y)
    {
        sprite.setPosition(x, y);
    }

    public void setVelocity(float x, float y)
    {
        velocity.set(x, y);
    }

    public void setAngle(float degrees)
    {
        sprite.setRotation(degrees);
    }

    public Vector2 getPosition()
    {
        position.set(sprite.getX(), sprite.getY());
        return position;
    }

    public Vector2 getVelocity()
    {
        return velocity;
    }

    public float getAngle()
    {
        return angle;
    }
}
