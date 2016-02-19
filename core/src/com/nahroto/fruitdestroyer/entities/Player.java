package com.nahroto.fruitdestroyer.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.nahroto.fruitdestroyer.Application;
import com.nahroto.fruitdestroyer.CameraShaker;
import com.nahroto.fruitdestroyer.Constants;
import com.nahroto.fruitdestroyer.Debug;
import com.nahroto.fruitdestroyer.Logger;

public class Player
{
    private final Application APP;

    private static final int KICK = 40;
    private static final float MAX_SPREAD = 0.7f;

    private Array<Bullet> totalBullets;
    private Array<Bullet> currentBullets;

    private Sprite sprite;
    private Sprite flashSprite;

    public static float rotateSpeed;

    private Vector2 unprojectedCoordinates;

    private float deltaX;
    private float deltaY;

    private float flashPositionX;
    private float flashPositionY;

    private float angle;

    private long timeSinceShot;
    private long timeSinceReload;

    private boolean offsetNeeded;
    private boolean flashNeeded;
    private boolean reloading;

    public static Integer ammo;

    private Sound shotSFX;
    private Sound emptySFX;
    private Sound reload100SFX;
    private Sound reload75SFX;
    private Sound reload50SFX;
    private Sound reload25SFX;
    private Sound currentReloadSound;

    private Polygon bounds;
    private float[] vertices;

    private int reloadTime;

    public static float spread;
    private long shootingTime;

    private long timeSinceFirstShot;
    private long timeSinceLastShot;
    private boolean firstShotFired;

    private static int reloadSpeedPercentage;

    public Player(Sprite sprite, Sprite flashSprite, Array<Bullet> totalBullets, Array<Bullet> currentBullets, final Application APP)
    {
        this.APP = APP;

        this.totalBullets = totalBullets;
        this.currentBullets = currentBullets;

        this.sprite = sprite;
        this.sprite.setPosition(Constants.V_WIDTH / 2 - 32, Constants.V_HEIGHT / 2 - 28);
        this.sprite.setOrigin(32, 28);

        this.flashSprite = flashSprite;
        this.flashSprite.setOrigin(0, 22);

        vertices = new float[]{
                0, 0,
                0, this.sprite.getHeight(),
                this.sprite.getWidth() / 2, this.sprite.getHeight(),
                this.sprite.getWidth() / 2, 0};

        bounds = new Polygon(vertices);

        shotSFX = APP.assets.get("sounds/shot.wav", Sound.class);
        emptySFX = APP.assets.get("sounds/empty2.wav", Sound.class);
        reload100SFX = APP.assets.get("sounds/reload100.wav", Sound.class);
        reload75SFX = APP.assets.get("sounds/reload75.wav", Sound.class);
        reload50SFX = APP.assets.get("sounds/reload50.wav", Sound.class);
        reload25SFX = APP.assets.get("sounds/reload25.wav", Sound.class);

        currentReloadSound = reload100SFX;

        reloadTime = 1850;

        unprojectedCoordinates = new Vector2();

        offsetNeeded = false;
        flashNeeded = false;
        reloading = false;

        ammo = new Integer(Bullet.getWeapon().getMagSize());
    }

    public void update()
    {
        if (firstShotFired && System.currentTimeMillis() - timeSinceLastShot > 500)
            firstShotFired = false;

        if (offsetNeeded && System.currentTimeMillis() - timeSinceShot > 50)
        {
            this.sprite.setPosition(Constants.V_WIDTH / 2 - 32, Constants.V_HEIGHT / 2 - 28);
            offsetNeeded = false;
        }

        if (flashNeeded && System.currentTimeMillis() - timeSinceShot > 50)
            flashNeeded = false;

        if (flashNeeded)
            flashSprite.setPosition((Constants.V_WIDTH / 2) + flashPositionX, ((Constants.V_HEIGHT / 2) + flashPositionY) - flashSprite.getHeight() / 2);

        if (reloading && System.currentTimeMillis() - timeSinceReload > reloadTime)
        {
            ammo = Bullet.getWeapon().getMagSize();
            reloading = false;
        }

        bounds.setPosition(sprite.getX(), sprite.getY());
    }

    public void followFinger()
    {
        unprojectedCoordinates.set(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
        APP.viewport.unproject(unprojectedCoordinates);

        deltaX = unprojectedCoordinates.x - Constants.V_WIDTH / 2;
        deltaY = unprojectedCoordinates.y - Constants.V_HEIGHT / 2;

        angle = (MathUtils.atan2(deltaX, deltaY) * MathUtils.radiansToDegrees) - 90;

        sprite.setRotation(angle);
    }

    public void shoot()
    {
        if (ammo > 0) // IF THERE IS AMMO
        {
            if (!reloading)
            {
                if (!CameraShaker.busy)
                    CameraShaker.startShaking(1.5f, 300);

                shotSFX.play();

                ammo--; // REDUCE AMMO BY 1

                if (offsetNeeded == false)
                    timeSinceShot = System.currentTimeMillis();

                offsetNeeded = true;
                flashNeeded = true;

                timeSinceLastShot = System.currentTimeMillis();

                if (!firstShotFired)
                {
                    if (Debug.INFO)
                        Logger.log("first shot fired");
                    firstShotFired = true;
                    timeSinceFirstShot = System.currentTimeMillis();
                }

                // System.out.println(Bullet.totalBullets.get(0).isUsed);
                for (int i = 0; i < totalBullets.size; i++)
                {
                    if (totalBullets.get(i).isUsed == false)
                    {
                        totalBullets.get(i).isOutOfScreen = false;
                        totalBullets.get(i).isUsed = true;

                        deltaX += MathUtils.random(-spread, spread) * 500f;
                        deltaY += MathUtils.random(-spread, spread) * 500f;

                        float length = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);

                        deltaY *= -1;

                        float directionX = deltaX / length;
                        float directionY = deltaY / length;

                        float bulletPositionX = (MathUtils.cos(angle * MathUtils.degreesToRadians) * 85);
                        float bulletPositionY = (MathUtils.sin(angle * MathUtils.degreesToRadians) * 85);

                        flashPositionX = (MathUtils.cos(angle * MathUtils.degreesToRadians) * 80);
                        flashPositionY = (MathUtils.sin(angle * MathUtils.degreesToRadians) * 80);

                        flashSprite.setRotation(angle);
                        flashSprite.setPosition((Constants.V_WIDTH / 2) + flashPositionX, ((Constants.V_HEIGHT / 2) + flashPositionY) - flashSprite.getHeight() / 2);

                        deltaY *= -1;
                        float bulletAngle = (MathUtils.atan2(deltaX, deltaY) * MathUtils.radiansToDegrees) - 90;

                        totalBullets.get(i).getSprite().setRotation(bulletAngle);
                        totalBullets.get(i).getBounds().setRotation(bulletAngle);

                        // OFFSET PLAYER TO GIVE KICK
                        offsetBack();

                        totalBullets.get(i).setPosition((Constants.V_WIDTH / 2 + bulletPositionX) - 15, (Constants.V_HEIGHT / 2 + bulletPositionY) - 15);
                        totalBullets.get(i).setVelocity(directionX * Bullet.VELOCITY, directionY * Bullet.VELOCITY);

                        currentBullets.add(totalBullets.get(i));
                        break;
                    }
                }
            }
        }

        else if (!reloading)
            emptySFX.play();
    }

    public void reload()
    {
        if (!reloading && ammo != Bullet.getWeapon().getMagSize())
        {
            reloading = true;
            currentReloadSound.play();
            timeSinceReload = System.currentTimeMillis();
        }
    }

    private void offsetBack()
    {
        sprite.translateX(-(MathUtils.cos(angle * MathUtils.degreesToRadians) * KICK));
        sprite.translateY(-(MathUtils.sin(angle * MathUtils.degreesToRadians) * KICK));
    }


    public void render(SpriteBatch batch)
    {
        sprite.draw(batch);
        if (flashNeeded)
            flashSprite.draw(batch);
    }

    public boolean isReloading()
    {
        return reloading;
    }

    public void setReloadingConfig(int percentage)
    {
        reloadSpeedPercentage = percentage;
        switch (percentage)
        {
            case 25: // FASTEST
                currentReloadSound = reload25SFX;
                reloadTime = 460;
                rotateSpeed = 4 * 4;
                break;
            case 50:
                currentReloadSound = reload50SFX;
                reloadTime = 930;
                rotateSpeed = 4 * 2;
                break;
            case 75:
                currentReloadSound = reload75SFX;
                reloadTime = 1390;
                rotateSpeed = 4 * 1.33f;
                break;
            case 100: // SLOWEST
                currentReloadSound = reload100SFX;
                reloadTime = 1850;
                rotateSpeed = 4;
                break;
        }
    }
    public static int getReloadSpeedPercentage()
    {
        return reloadSpeedPercentage;
    }

    public Polygon getBounds()
    {
        return bounds;
    }

    public Sprite getSprite()
    {
        return sprite;
    }

    public void updateAccuracy()
    {
        if (firstShotFired)
            shootingTime = System.currentTimeMillis() - timeSinceFirstShot;
        else
            shootingTime = 0;

        spread = (shootingTime / 3000f) * Bullet.getWeapon().getRecoil();

        if (spread > MAX_SPREAD)
            spread = MAX_SPREAD;

        // Logger.log("spread: " + spread);
    }
}
