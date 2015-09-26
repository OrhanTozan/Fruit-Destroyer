package com.nahroto.fruitdestroyer.entities.enemies;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Enemy
{
    protected int BOUNDING_X;
    protected int BOUNDING_Y;
    protected int BOUNDING_WIDTH;
    protected int BOUNDING_HEIGHT;

    protected float angle;

    protected Vector2 velocity;
    protected Vector2 position;

    protected Sprite sprite;
    protected Rectangle bounds;

    public Enemy(Sprite sprite, int BOUNDING_X, int BOUNDING_Y, int BOUNDING_WIDTH, int BOUNDING_HEIGHT)
    {
        this.sprite = sprite;
        velocity = new Vector2();
        position = new Vector2();
        bounds = new Rectangle();

        this.BOUNDING_X = BOUNDING_X;
        this.BOUNDING_Y = BOUNDING_Y;
        this.BOUNDING_WIDTH = BOUNDING_WIDTH;
        this.BOUNDING_HEIGHT = BOUNDING_HEIGHT;

        bounds.set(this.sprite.getX() + this.BOUNDING_X, this.sprite.getY() + this.BOUNDING_Y, this.BOUNDING_WIDTH, this.BOUNDING_HEIGHT);
        position.set(this.sprite.getX(), this.sprite.getY());
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
