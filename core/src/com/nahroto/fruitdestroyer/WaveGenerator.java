package com.nahroto.fruitdestroyer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.nahroto.fruitdestroyer.entities.enemies.Ananas;
import com.nahroto.fruitdestroyer.entities.enemies.Enemy;
import com.nahroto.fruitdestroyer.entities.enemies.Orange;

public class WaveGenerator
{
    public static final int BUY_WAVE = 1;

    private static final int ANANAS_MINIMUM_WAVE = 1;
    private static final float ORANGE_MULTIPLIER = 1.2f;
    private static final float ANANAS_MULTIPLIER = 0.5f;

    private Array<Enemy> totalEnemies;
    private Array<Enemy> currentEnemies;
    private Array<Orange> totalOranges;
    private Array<Ananas> totalAnanases;

    public static Integer wave = new Integer(1);

    private Array<Enemy> queue;

    private float delay;
    private long startTime;

    public WaveGenerator(Array<Enemy> totalEnemies, Array<Enemy> currentEnemies, Array<Orange> totalOranges, Array<Ananas> totalAnanases)
    {
        this.totalEnemies = totalEnemies;
        this.currentEnemies = currentEnemies;
        this.totalOranges = totalOranges;
        this.totalAnanases = totalAnanases;
        queue = new Array<Enemy>();
    }

    public void startNewWave()
    {
        Logger.log("NEW WAVE STARTED");
        Logger.log("WAVE: " + wave);

        startTime = System.currentTimeMillis();

        restoreAllEnemies();

        delayTime(0f);
            addOranges(MathUtils.round(wave * ORANGE_MULTIPLIER));
            if (wave >= ANANAS_MINIMUM_WAVE)
                addAnanases(MathUtils.round(wave * ANANAS_MULTIPLIER));
    }

    public void update()
    {
        if (queue.size > 0 && System.currentTimeMillis() - startTime >= delay * 1000)
            sendQueueImmediatly();
    }

    private void delayTime(float delayTime)
    {
        delay = delayTime;
    }

    private void restoreAllEnemies()
    {
        for (Enemy enemy : totalEnemies)
            enemy.restoreHealth();
    }

    private void makeEnemiesReady()
    {
        for (Enemy enemy : queue)
        {
            enemy.isUsed = true;

            enemy.setPosition(RandomPositioner.getRandomPosition(enemy.getSprite().getHeight()));
            enemy.calculateRotation();
            enemy.calculateVelocity();
        }
    }

    private void addOranges(int amount)
    {
        for (int i = 0; i < amount; i++)
        {
            checkIfUsedLoop:
            for (Orange orange : totalOranges)
            {
                if (!orange.isUsed)
                {
                    queue.add(orange);
                    orange.isUsed = true;
                    break checkIfUsedLoop;
                }
            }
        }

        if (delay == 0)
            sendQueueImmediatly();
    }

    private void addAnanases(int amount)
    {
        for (int i = 0; i < amount; i++)
        {
            checkIfUsedLoop:
            for (Ananas ananas : totalAnanases)
            {
                if (!ananas.isUsed)
                {
                    queue.add(ananas);
                    ananas.isUsed = true;
                    break checkIfUsedLoop;
                }
            }
        }

        if (delay == 0)
            sendQueueImmediatly();
    }

    private void sendQueueImmediatly()
    {
        makeEnemiesReady();
        currentEnemies.addAll(queue);
        Logger.log("queue released!, size: " + queue.size);
        Logger.log("current enemies size: " + currentEnemies.size);
        queue.clear();
    }

    public Array<Enemy> getQueue()
    {
        return queue;
    }
}