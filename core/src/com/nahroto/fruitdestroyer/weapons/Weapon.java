package com.nahroto.fruitdestroyer.weapons;

public class Weapon
{
    private int damage;
    private int rateOfFire;
    private int magSize;
    private float recoil;

    public Weapon(int damage, int rateOfFire, int magSize, float recoil)
    {
        this.damage = damage;
        this.rateOfFire = rateOfFire;
        this.magSize = magSize;
        this.recoil = recoil;
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

    public float getRecoil()
    {
        return recoil;
    }
}
