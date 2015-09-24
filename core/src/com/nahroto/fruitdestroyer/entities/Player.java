package com.nahroto.fruitdestroyer.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.nahroto.fruitdestroyer.Application;
import com.nahroto.fruitdestroyer.Constants;

public class Player
{
    private final Application APP;

    private final int KICK = 10;

    private Sprite sprite;
    private Vector2 unprojectedCoordinates;

    private float deltaX;
    private float deltaY;

    private float directionX;
    private float directionY;

    private float angle;

    private Sound shotSFX;

    public Player(Sprite sprite, final Application APP)
    {
        this.APP = APP;

        this.sprite = sprite;
        this.sprite.setPosition(Constants.V_WIDTH / 2 - 32, Constants.V_HEIGHT / 2 - 28);
        this.sprite.setOrigin(32, 28);


        shotSFX = APP.assets.get("sounds/shot.wav", Sound.class);
        unprojectedCoordinates = new Vector2();
    }

    public void update()
    {
    }

    public void followFinger()
    {
        unprojectedCoordinates.set(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
        APP.viewport.unproject(unprojectedCoordinates);

        deltaX = unprojectedCoordinates.x - sprite.getX() + 32;
        deltaY = unprojectedCoordinates.y - sprite.getY() + 28;

        angle = (MathUtils.atan2(deltaX, deltaY) * MathUtils.radiansToDegrees) - 90;

        System.out.println(angle);
        sprite.setRotation(angle);
    }

    public void shoot()
    {
        shotSFX.play();
        System.out.println(Bullet.totalBullets.get(0).isUsed);
        for (int i = 0; i < Bullet.totalBullets.size; i++)
        {
            if (Bullet.totalBullets.get(i).isUsed == false)
            {
                Bullet.totalBullets.get(i).isOutOfScreen = false;
                Bullet.totalBullets.get(i).isUsed = true;

                float len = (float)Math.sqrt(deltaX * deltaX + deltaY * deltaY);

                deltaY *= -1;

                directionX = deltaX / len;
                directionY = deltaY / len;
                float positionX = (MathUtils.cos(angle * MathUtils.degreesToRadians) * 85);
                float positionY = (MathUtils.sin(angle * MathUtils.degreesToRadians) * 85);

                // OFFSET PLAYER TO GIVE KICK
                // offsetBack();

                Bullet.totalBullets.get(i).setPosition((sprite.getX() + 32 + positionX) - 10, (sprite.getY() + 28 + positionY) - 10);
                Bullet.totalBullets.get(i).setVelocity(directionX * Bullet.VELOCITY, directionY * Bullet.VELOCITY);

                Bullet.currentBullets.add(Bullet.totalBullets.get(i));
                break;
            }
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
    }
}
