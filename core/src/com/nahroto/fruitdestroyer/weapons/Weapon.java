package com.nahroto.fruitdestroyer.weapons;

public class Weapon
{
    private int damage;
    private int rateOfFire;
    private int magSize;
    private float spread;

    public Weapon(int damage, int rateOfFire, int magSize, float spread)
    {
        this.damage = damage;
        this.rateOfFire = rateOfFire;
        this.magSize = magSize;
        this.spread = spread;
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

    public float getSpread()
    {
        return spread;
    }
}
