package com.nahroto.fruitdestroyer.weapons;

public final class WeaponList
{
    public final static class PISTOL
    {
        public static final boolean IS_AUTOMATIC = false;
        public static final int DAMAGE = 5;
        public static final int RATE_OF_FIRE = 200;
        public static final int MAG_SIZE = 20;
        public static final float SPREAD = 0f;
    }

    public final static class SHOTGUN
    {
        public static final boolean IS_AUTOMATIC = false;
        public static final int DAMAGE = 20;
        public static final int RATE_OF_FIRE = 500;
        public static final int MAG_SIZE = 30;
        public static final float SPREAD = 0.5f;
    }

    public final static class SUBMACHINE_GUN
    {
        public static final boolean IS_AUTOMATIC = true;
        public static final int DAMAGE = 20;
        public static final int RATE_OF_FIRE = 100;
        public static final int MAG_SIZE = 30;
        public static final float SPREAD = 0.4f;
    }

    public final static class ASSAULT_RIFLE
    {
        public static final boolean IS_AUTOMATIC = true;
        public static final int DAMAGE = 20;
        public static final int RATE_OF_FIRE = 200;
        public static final int MAG_SIZE = 20;
        public static final float SPREAD = 0.2f;
    }

    public final static class MACHINE_GUN
    {
        public static final boolean IS_AUTOMATIC = true;
        public static final int DAMAGE = 20;
        public static final int RATE_OF_FIRE = 200;
        public static final int MAG_SIZE = 100;
        public static final float SPREAD = 0.6f;
    }
}
