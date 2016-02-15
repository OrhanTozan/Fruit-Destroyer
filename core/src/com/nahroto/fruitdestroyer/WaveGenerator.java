package com.nahroto.fruitdestroyer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.nahroto.fruitdestroyer.entities.enemies.Ananas;
import com.nahroto.fruitdestroyer.entities.enemies.Enemy;
import com.nahroto.fruitdestroyer.entities.enemies.Orange;
import com.nahroto.fruitdestroyer.entities.enemies.Watermelon;

public class WaveGenerator
{
    public static final int BUY_WAVE = 1;

    private static final int ANANAS_MINIMUM_WAVE = 6;

    private Array<Enemy> totalEnemies;
    private Array<Enemy> currentEnemies;
    private Array<Orange> totalOranges;
    private Array<Ananas> totalAnanases;
    private Array<Watermelon> totalWatermelons;

    public static Integer wave = new Integer(1);

    private Array<Enemy> queue;

    private float delay;
    private long startTime;

    public WaveGenerator(Array<Enemy> totalEnemies, Array<Enemy> currentEnemies, Array<Orange> totalOranges, Array<Ananas> totalAnanases, Array<Watermelon> totalWatermelons)
    {
        this.totalEnemies = totalEnemies;
        this.currentEnemies = currentEnemies;
        this.totalOranges = totalOranges;
        this.totalAnanases = totalAnanases;
        this.totalWatermelons = totalWatermelons;
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
            delayTime(0f); // FIRST GROUP
                int amountOranges1 = MathUtils.round(2 + wave * 0.3f);
                if (amountOranges1 > 10)
                    amountOranges1 = 10;
                int amountAnanases1 = MathUtils.round(2 + wave * 0.14f);

                addOranges(amountOranges1);
                addAnanases(amountAnanases1);

            delayTime(3f);
                if (wave >= 8)
                {
                    int amountWatermelons1 = MathUtils.ceil(wave * 0.05f);
                    addWatermelons(amountWatermelons1);
                }

            delayTime(5f + (wave * 0.5f)); // SECOND GROUP
                int amountOranges2 = MathUtils.round(wave * 0.4f);
                int amountAnanases2 = MathUtils.round(wave * 0.14f);

                addOranges(amountOranges2);
                addAnanases(amountAnanases2);
        }

        if (wave >= 5)
        {
            delayTime(12f); // THIRD GROUP
                int amountOranges3 = MathUtils.round(wave * 0.4f);
                int amountAnanases3 = MathUtils.round(wave * 0.14f);

                addOranges(amountOranges3);
                addAnanases(amountAnanases3);

            delayTime(15f);
                if (wave >= 8)
                {
                    int amountWatermelons3 = MathUtils.ceil(wave * 0.1f);
                    addWatermelons(amountWatermelons3);
                }
        }
        if (wave >= 7)
        {
            delayTime(17f);
                int amountOranges4 = MathUtils.round(wave * 0.5f);
                int amountAnanases4 = MathUtils.round(wave * 0.14f);
                int amountWatermelons4 = MathUtils.ceil(wave * 0.1f);

                addOranges(amountOranges4);
                addAnanases(amountAnanases4);

            delayTime(20f);
                addWatermelons(amountWatermelons4);
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

    private void addWatermelons(int amount)
    {
        for (int i = 0; i < amount; i++)
        {
            checkIfUsedLoop:
            for (Watermelon watermelon : totalWatermelons)
            {
                if (!watermelon.isUsed)
                {
                    queue.add(watermelon);
                    watermelon.isUsed = true;
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
        if (Debug.LOG_WAVES)
        {
            Logger.log("queue released!, size: " + queue.size);
            Logger.log("current enemies size: " + currentEnemies.size);
        }
        queue.clear();
    }

    public Array<Enemy> getQueue()
    {
        return queue;
    }
}