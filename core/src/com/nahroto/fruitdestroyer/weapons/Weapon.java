package com.nahroto.fruitdestroyer.weapons;

public class Weapon
{
    public static final int PISTOL = 0;
    public static final int SHOTGUN = 1;
    public static final int SMG = 2;
    public static final int ASSAULT_RIFLE = 3;
    public static final int MACHINE_GUN = 4;

    private int damage;
    private int rateOfFire;
    private int magSize;
    private float accuracy;

    public Weapon(int damage, int rateOfFire, int magSize, float accuracy)
    {
        this.damage = damage;
        this.rateOfFire = rateOfFire;
        this.magSize = magSize;
        this.accuracy = accuracy;
    }

    public int getDamage()
    {
        return damage;
    }

    public int getRateOfFire()
    {
        return rateOfFire;
    }

    public int getMagSize()
    {
        return magSize;
    }

    public float getAccuracy()
    {
        return accuracy;
    }
}
