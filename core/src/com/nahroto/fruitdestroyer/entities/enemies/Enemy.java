package com.nahroto.fruitdestroyer.entities.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.nahroto.fruitdestroyer.Application;
import com.nahroto.fruitdestroyer.Constants;
import com.nahroto.fruitdestroyer.Explosion;
import com.nahroto.fruitdestroyer.HealthBar;

public class Enemy
{
    public static Array<Enemy> totalEnemies = new Array<Enemy>();
    public static Array<Enemy> currentEnemies = new Array<Enemy>();

    protected static final int BASEVELOCITY = 30;
    protected float velocityMultiplier;
    protected boolean explodable;

    public boolean renderHit = false;
    public boolean isCollidable;

    public boolean isUsed;

    protected long currentTime;
    public long deadStartTime;
    public boolean isDying;

    protected int maxHealth;
    protected int health;

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
    protected TextureAtlas.AtlasRegion deadTexture;

    protected Polygon bounds;

    protected Sound squishSFX;
    protected float[] vertices;

    public Enemy(final Application APP, TextureAtlas.AtlasRegion normalTexture, TextureAtlas.AtlasRegion hitTexture, TextureAtlas.AtlasRegion deadTexture, Sprite redBar, Sprite greenBar, int BOUNDING_X, int BOUNDING_Y, int BOUNDING_WIDTH, int BOUNDING_HEIGHT)
    {
        this.normalTexture = normalTexture;
        this.hitTexture = hitTexture;
        this.deadTexture = deadTexture;

        this.sprite = new Sprite(normalTexture);

        squishSFX = APP.assets.get("sounds/squish.wav", Sound.class);

        velocity = new Vector2();
        position = new Vector2();
        bounds = new Polygon();

        healthBar = new HealthBar(redBar, greenBar);

        this.BOUNDING_X = BOUNDING_X;
        this.BOUNDING_Y = BOUNDING_Y;
        this.BOUNDING_WIDTH = BOUNDING_WIDTH;
        this.BOUNDING_HEIGHT = BOUNDING_HEIGHT;

        vertices = new float[]{
                0, 0,
                0, BOUNDING_HEIGHT,
                BOUNDING_WIDTH, BOUNDING_HEIGHT,
                BOUNDING_WIDTH, 0};
        bounds.setVertices(vertices);
        bounds.setPosition(sprite.getX(), sprite.getY());
        position.set(this.sprite.getX(), this.sprite.getY());

        bounds.setOrigin(BOUNDING_WIDTH / 2, BOUNDING_HEIGHT / 2);

        isCollidable = true;
        isDying = false;
    }

    public void calculateVelocity()
    {
        float deltaX = Constants.V_WIDTH / 2 - bounds.getX() - (BOUNDING_WIDTH / 2);
        float deltaY = Constants.V_HEIGHT / 2 - bounds.getY() - (BOUNDING_HEIGHT / 2);

        float length = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        float directionX = deltaX / length;
        float directionY = deltaY / length;

        int extraVelocity = MathUtils.random(0, 2);

        velocity.set((BASEVELOCITY + (extraVelocity * 10)) * directionX, (BASEVELOCITY + (extraVelocity * 10)) * directionY);
        velocity.scl(velocityMultiplier);
    }

    public void calculateRotation()
    {
        float deltaX = Constants.V_WIDTH / 2 - bounds.getX() - (BOUNDING_WIDTH / 2);
        float deltaY = Constants.V_HEIGHT / 2 - bounds.getY() - (BOUNDING_HEIGHT / 2);

        angle = (MathUtils.atan2(-deltaX, deltaY) * MathUtils.radiansToDegrees) - 180;

        sprite.setRotation(angle);
        bounds.setRotation(angle);
    }

    public void update(float delta)
    {
        applyVelocityToPosition(delta);
        updateBounds();
        if (!isDying && renderHit && (System.currentTimeMillis() - currentTime > 100))
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

    public void playSquishSound()
    {
        squishSFX.play();
    }


    public void setPosition(Vector2 position)
    {
        sprite.setPosition(position.x, position.y);
        bounds.setPosition(position.x + BOUNDING_X, position.y + BOUNDING_Y);
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

    public void setDeadTexture()
    {
        sprite.setRegion(deadTexture);
        deadStartTime = System.currentTimeMillis();
        isDying = true;
    }

    public void resetVelocity()
    {
        velocity.set(0, 0);
    }

    public float getAngle()
    {
        return angle;
    }

    public Sprite getSprite()
    {
        return sprite;
    }

    public Polygon getBounds()
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

    public int getMaxHealth()
    {
        return maxHealth;
    }

    public void restoreHealth()
    {
        health = maxHealth;
    }

    public void reduceHealth(int damage)
    {
        health -= damage;
    }

    public boolean isExplodable()
    {
        return explodable;
    }
}
