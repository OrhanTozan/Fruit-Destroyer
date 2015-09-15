package com.nahroto.fruitdestroyer.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Bullet
{
    public static final int COUNT = 30;

    private Vector2 position;
    private Vector2 velocity;
    private Sprite sprite;
    private Rectangle bounds;

    public static Array<Bullet> totalBullets = new Array<Bullet>();
    public static Array<Bullet> currentBullets = new Array<Bullet>();

    public Bullet(Sprite sprite)
    {
        this.sprite = sprite;
        position = new Vector2();
        velocity = new Vector2();
        bounds = new Rectangle(position.x, position.y, sprite.getWidth(), sprite.getHeight());
    }


    public void update(float delta)
    {
        updateBounds();
        applyVelocityToPosition(delta);
    }

    private void applyVelocityToPosition(float delta)
    {
        velocity.scl(delta);
        position.add(velocity);
        velocity.scl(1 / delta);
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
