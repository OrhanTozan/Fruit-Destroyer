package com.nahroto.fruitdestroyer.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.nahroto.fruitdestroyer.Constants;
import com.nahroto.fruitdestroyer.weapons.Weapon;
import com.nahroto.fruitdestroyer.weapons.WeaponList;

public class Bullet
{
    public static final int VELOCITY = 1200;

    public static Array<Bullet> totalBullets = new Array<Bullet>();
    public static Array<Bullet> currentBullets = new Array<Bullet>();

    private Vector2 velocity;
    private Sprite sprite;
    private Polygon bounds;
    private float[] vertices;

    private static Weapon currentWeapon;

    private final Weapon PISTOL;
    private final Weapon SHOTGUN;
    private final Weapon SUBMACHINE_GUN;
    private final Weapon ASSAULT_RIFLE;
    private final Weapon MACHINE_GUN;

    public boolean isOutOfScreen;
    public boolean isUsed;

    public Bullet(Sprite sprite)
    {
        this.sprite = sprite;

        PISTOL = new Weapon(WeaponList.PISTOL.IS_AUTOMATIC, WeaponList.PISTOL.DAMAGE, WeaponList.PISTOL.RATE_OF_FIRE, WeaponList.PISTOL.MAG_SIZE, WeaponList.PISTOL.SPREAD);
        SHOTGUN = new Weapon(WeaponList.SHOTGUN.IS_AUTOMATIC, WeaponList.SHOTGUN.DAMAGE, WeaponList.SHOTGUN.RATE_OF_FIRE, WeaponList.SHOTGUN.MAG_SIZE, WeaponList.SHOTGUN.SPREAD);
        SUBMACHINE_GUN = new Weapon(WeaponList.SUBMACHINE_GUN.IS_AUTOMATIC, WeaponList.SUBMACHINE_GUN.DAMAGE, WeaponList.SUBMACHINE_GUN.RATE_OF_FIRE, WeaponList.SUBMACHINE_GUN.MAG_SIZE, WeaponList.SUBMACHINE_GUN.SPREAD);
        ASSAULT_RIFLE = new Weapon(WeaponList.ASSAULT_RIFLE.IS_AUTOMATIC, WeaponList.ASSAULT_RIFLE.DAMAGE, WeaponList.ASSAULT_RIFLE.RATE_OF_FIRE, WeaponList.ASSAULT_RIFLE.MAG_SIZE, WeaponList.ASSAULT_RIFLE.SPREAD);
        MACHINE_GUN = new Weapon(WeaponList.MACHINE_GUN.IS_AUTOMATIC, WeaponList.MACHINE_GUN.DAMAGE, WeaponList.MACHINE_GUN.RATE_OF_FIRE, WeaponList.MACHINE_GUN.MAG_SIZE, WeaponList.MACHINE_GUN.SPREAD);

        currentWeapon = PISTOL;

        velocity = new Vector2();

        vertices = new float[]{
                0, 0,
                0, 10,
                10, 10,
                10, 0 };

        bounds = new Polygon(vertices);
        isOutOfScreen = false;
        isUsed = false;
    }

    public void update(float delta)
    {
        applyVelocityToPosition(delta);
        updateBounds();
        isOutOfScreen();
    }

    private void applyVelocityToPosition(float delta)
    {
        velocity.scl(delta);
        sprite.translate(velocity.x, velocity.y);
        velocity.scl(1 / delta);
    }

    private void isOutOfScreen()
    {
        if (sprite.getX() > Constants.V_WIDTH || sprite.getX() + sprite.getWidth() < 0 || sprite.getY() > Constants.V_HEIGHT || sprite.getY()  + sprite.getHeight() < 0)
            isOutOfScreen = true;
        else
            isOutOfScreen = false;
    }

    private void updateBounds()
    {
        bounds.setPosition(sprite.getX() + 5, sprite.getY() + 5);
    }

    public void render(SpriteBatch batch)
    {
        sprite.draw(batch);
    }

    public void setPosition(float x, float y)
    {
        sprite.setPosition(x, y);
    }

    public void setVelocity(float x, float y)
    {
        velocity.set(x, y);
    }

    public Polygon getBounds()
    {
        return bounds;
    }

    public Sprite getSprite()
    {
        return sprite;
    }

    public static Weapon getWeapon()
    {
        return currentWeapon;
    }
}
