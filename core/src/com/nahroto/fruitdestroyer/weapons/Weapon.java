package com.nahroto.fruitdestroyer.weapons;

public class Weapon
{
    private int damage;
    private int rateOfFire;
    private int magSize;
    private float recoil;
    private int knockbackPower;

    public Weapon(int damage, int rateOfFire, int magSize, float recoil, int knockbackPower)
    {
        this.damage = damage;
        this.rateOfFire = rateOfFire;
        this.magSize = magSize;
        this.recoil = recoil;
        this.knockbackPower = knockbackPower;
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

    public int getKnockbackPower()
    {
        return knockbackPower;
    }

    public void setDamage(int damage)
    {
        this.damage = damage;
    }

    public void setRateOfFire(int rateOfFire)
    {
        this.rateOfFire = rateOfFire;
    }

    public void setMagSize(int magSize)
    {
        this.magSize = magSize;
    }

    public void setRecoil(float recoil)
    {
        this.recoil = recoil;
    }

    public void setKnockbackPower(int knockbackPower)
    {
        this.knockbackPower = knockbackPower;
    }
}
