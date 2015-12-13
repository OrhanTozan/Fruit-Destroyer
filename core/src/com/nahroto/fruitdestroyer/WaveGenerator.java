package com.nahroto.fruitdestroyer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.nahroto.fruitdestroyer.entities.enemies.Ananas;
import com.nahroto.fruitdestroyer.entities.enemies.Enemy;
import com.nahroto.fruitdestroyer.entities.enemies.Orange;

public class WaveGenerator
{
    private static final int ANANAS_MINIMUM_WAVE = 5;

    private static final float ORANGE_MULTIPLIER = 1f;
    private static final float ANANAS_MULTIPLIER = 0.5f;

    public static Integer wave = new Integer(1);

    public static void startNewWave()
    {
        restoreAllEnemies();
        addOranges(MathUtils.round(wave * ORANGE_MULTIPLIER));
        if (wave >= ANANAS_MINIMUM_WAVE)
            addAnanases(MathUtils.round(wave * ANANAS_MULTIPLIER));
        makeEnemiesReady();

        delay(1);
    }

    public static void update(float delta)
    {

    }

    private static void delay(float delayTime)
    {

    }


    private static void restoreAllEnemies()
    {
        for (Enemy enemy : Enemy.totalEnemies)
            enemy.restoreHealth();
    }

    private static void makeEnemiesReady()
    {
        for (Enemy enemy : Enemy.currentEnemies)
        {
            enemy.setPosition(RandomPositioner.getRandomPosition());
            enemy.calculateRotation();
            enemy.calculateVelocity();
        }
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
