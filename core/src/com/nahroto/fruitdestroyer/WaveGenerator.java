package com.nahroto.fruitdestroyer;

import com.badlogic.gdx.math.MathUtils;
import com.nahroto.fruitdestroyer.entities.enemies.Ananas;
import com.nahroto.fruitdestroyer.entities.enemies.Enemy;
import com.nahroto.fruitdestroyer.entities.enemies.Orange;

public class WaveGenerator
{
    private static final float WAVE_MULTIPLIER = 1.5f;

    public static Integer wave = new Integer(1);

    public static void startNewWave()
    {
        addOranges(30);
    }

    private static void addOranges(int number)
    {
        for (int i = 0; i < number; i++)
        {
            Enemy.currentEnemies.add(Orange.totalOranges.get(i));
        }
    }

    private static void addAnanases(int number)
    {
        for (int i = 0; i < number; i++)
        {
            Enemy.currentEnemies.add(Ananas.totalAnanases.get(i));
        }
    }
}
