package com.nahroto.fruitdestroyer.entities.enemies;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.nahroto.fruitdestroyer.Application;
import com.nahroto.fruitdestroyer.Constants;
import com.nahroto.fruitdestroyer.HealthBar;

public class Enemy
{
    public static Array<Enemy> totalEnemies = new Array<Enemy>();
    public static Array<Enemy> currentEnemies = new Array<Enemy>();

    public static final int VELOCITY = 50;

    public boolean renderHit = false;

    protected long currentTime;

    protected int health = 48;

    protected HealthBar healthBar;

    public int BOUNDING_X;
    public int BOUNDING_Y;
    public int BOUNDING_WIDTH;
    public int BOUNDING_HEIGHT;

    protected float angle;

    protected Vector2 velocity;
    protected Vector2 position;

    protected Sprite sprite;

    protected TextureAtlas.AtlasRegion normalTexture;
    protected TextureAtlas.AtlasRegion hitTexture;

    protected Rectangle bounds;

    protected Sound squishSFX;

    public Enemy(final Application APP,TextureAtlas.AtlasRegion normalTexture, TextureAtlas.AtlasRegion hitTexture, Sprite redBar, Sprite greenBar, int BOUNDING_X, int BOUNDING_Y, int BOUNDING_WIDTH, int BOUNDING_HEIGHT)
    {
        this.normalTexture = normalTexture;
        this.hitTexture = hitTexture;

        this.sprite = new Sprite(normalTexture);

        squishSFX = APP.assets.get("sounds/squish.wav", Sound.class);

        velocity = new Vector2();
        position = new Vector2();
        bounds = new Rectangle();

        healthBar = new HealthBar(redBar, greenBar);

        this.BOUNDING_X = BOUNDING_X;
        this.BOUNDING_Y = BOUNDING_Y;
        this.BOUNDING_WIDTH = BOUNDING_WIDTH;
        this.BOUNDING_HEIGHT = BOUNDING_HEIGHT;

        bounds.set(this.sprite.getX() + this.BOUNDING_X, this.sprite.getY() + this.BOUNDING_Y, this.BOUNDING_WIDTH, this.BOUNDING_HEIGHT);
        position.set(this.sprite.getX(), this.sprite.getY());
    }

    public void calculateVelocity()
    {
        float deltaX = Constants.V_WIDTH / 2 - bounds.x - (BOUNDING_WIDTH / 2);
        float deltaY = Constants.V_HEIGHT / 2 - bounds.y - (BOUNDING_HEIGHT / 2);

        float length = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        float directionX = deltaX / length;
        float directionY = deltaY / length;

        int extraVelocity = MathUtils.random(0, 5);

        velocity.set((VELOCITY + (extraVelocity * 20)) * directionX, (VELOCITY + (extraVelocity * 20)) * directionY);
    }

    public void calculateRotation()
    {
        float deltaX = Constants.V_WIDTH / 2 - bounds.x - (BOUNDING_WIDTH / 2);
        float deltaY = Constants.V_HEIGHT / 2 - bounds.y - (BOUNDING_HEIGHT / 2);

        angle = (MathUtils.atan2(-deltaX, deltaY) * MathUtils.radiansToDegrees) - 180;

        sprite.setRotation(angle);
    }

    public void update(float delta)
    {
        applyVelocityToPosition(delta);
        updateBounds();
        if (renderHit && (System.currentTimeMillis() - currentTime > 100))
        {
            renderHit = false;
            setNormalTexture();
        }
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

    public void revive()
    {
        health = 48;
        setPosition(Constants.getRandomPosition(MathUtils.random(0, 15), sprite.getWidth(), sprite.getHeight()));

        calculateRotation();
        calculateVelocity();
    }

    public void playSquishSound()
    {
        squishSFX.play();
    }

    public void setPosition(float x, float y)
    {
        sprite.setPosition(x, y);
        bounds.setPosition(x + BOUNDING_X, y + BOUNDING_Y);
    }

    public void setPosition(Vector2 position)
    {
        sprite.setPosition(position.x, position.y);
        bounds.setPosition(position.x + BOUNDING_X, position.y + BOUNDING_Y);
    }

    public void setVelocity(float x, float y)
    {
        velocity.set(x, y);
    }

    public void setAngle(float degrees)
    {
        sprite.setRotation(degrees);
    }

    public void setNormalTexture()
    {
        sprite.setRegion(normalTexture);
    }

    public void setHitTexture()
    {
        sprite.setRegion(hitTexture);
        currentTime = System.currentTimeMillis();
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

    public Sprite getSprite()
    {
        return sprite;
    }

    public Rectangle getBounds()
    {
        return bounds;
    }

    public HealthBar getHealthBar()
    {
        return healthBar;
    }

    public int getHealth()
    {
        return health;
    }

    public void reduceHealth(int damage)
    {
        health -= damage;
    }
}
