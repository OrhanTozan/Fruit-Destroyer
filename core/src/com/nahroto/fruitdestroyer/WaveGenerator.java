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

    private static final int ANANAS_MINIMUM_WAVE = 6;

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
        if (Debug.LOG_WAVES)
        {
            Logger.log("NEW WAVE STARTED");
            Logger.log("WAVE: " + wave);
        }

        startTime = System.currentTimeMillis();

        restoreAllEnemies();

        if (wave >= 0)
        {
            delayTime(0f);
                int amountOranges1 = MathUtils.round(wave * 1.5f);
                int amountAnanases1 = MathUtils.round(wave * 0.5f);

                addOranges(amountOranges1);
                if (wave >= ANANAS_MINIMUM_WAVE)
                    addAnanases(amountAnanases1);
        }

        if (wave >= 5)
        {
            delayTime(8f);
                int amountOranges2 = MathUtils.round(wave * 0.5f);
                int amountAnanases2 = MathUtils.round(wave * 0.2f);

                addOranges(amountOranges2);
                addAnanases(amountAnanases2);
        }
    }

    public void update()
    {
        if ( (queue.size > 0) && (System.currentTimeMillis() - startTime >= delay * 1000) )
            sendQueueImmediatly();
    }

    public boolean isBuyRound()
    {
        return (wave % BUY_WAVE == 0);
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

        if (delay == 0f)
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

        if (delay == 0f)
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