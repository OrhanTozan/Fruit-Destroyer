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

    private int currentGroup;

    private Array<Enemy> totalEnemies;
    private Array<Enemy> currentEnemies;
    private Array<Orange> totalOranges;
    private Array<Ananas> totalAnanases;
    private Array<Watermelon> totalWatermelons;

    public static Integer wave = new Integer(1);

    private QueueManager queueManager = new QueueManager();

    public WaveGenerator(Array<Enemy> totalEnemies, Array<Enemy> currentEnemies, Array<Orange> totalOranges, Array<Ananas> totalAnanases, Array<Watermelon> totalWatermelons)
    {
        this.totalEnemies = totalEnemies;
        this.currentEnemies = currentEnemies;
        this.totalOranges = totalOranges;
        this.totalAnanases = totalAnanases;
        this.totalWatermelons = totalWatermelons;
    }

    public void startNewWave()
    {
        if (Debug.LOG_WAVES)
        {
            Logger.log("NEW WAVE STARTED");
            Logger.log("WAVE: " + wave);
        }
        currentGroup = 1;
        int amountOranges = 0;
        int amountAnanases = 0;
        int amountWatermelons = 0;

        restoreAllEnemies();

        if (wave >= 0)
        {
            // FIRST GROUP
            amountOranges = MathUtils.round(2 + wave * 0.5f);
            if (amountOranges > 10)
                amountOranges = 10;
            amountAnanases = MathUtils.round(2 + wave * 0.14f);

            addOranges(amountOranges, 1);
            addAnanases(amountAnanases, 1);

            if (wave >= 8)
            {
                amountWatermelons = MathUtils.ceil(wave * 0.05f);
                addWatermelons(amountWatermelons, 1);
            }

            // SECOND GROUP
            amountOranges = MathUtils.round(wave * 0.3f);
            if (amountOranges > 15)
                amountOranges = 15;
            amountAnanases = MathUtils.round(wave * 0.14f);

            addOranges(amountOranges, 2);
            addAnanases(amountAnanases, 2);
        }

        if (wave >= 5)
        {
            // THIRD GROUP
            amountOranges = MathUtils.round(wave * 0.5f);
            amountAnanases = MathUtils.round(wave * 0.14f);

            addOranges(amountOranges, 3);
            addAnanases(amountAnanases, 3);

            if (wave >= 8)
            {
                amountWatermelons = MathUtils.ceil(wave * 0.1f);
                addWatermelons(amountWatermelons, 3);
            }
        }
        if (wave >= 7)
        {
            // FOURTH GROUP
            amountOranges = MathUtils.round(wave * 0.3f);
            amountAnanases = MathUtils.round(wave * 0.14f);
            amountWatermelons = MathUtils.ceil(wave * 0.1f);

            addOranges(amountOranges, 4);
            addAnanases(amountAnanases, 4);
            addWatermelons(amountWatermelons, 4);
        }

    }

    public void update()
    {
        if (currentEnemies.size == 0)
            currentGroup++;
        if (currentGroup > 4)
            currentGroup = 1;
        queueManager.update(currentGroup, this);
    }

    public boolean isBuyRound()
    {
        return (wave % BUY_WAVE == 0);
    }

    private void restoreAllEnemies()
    {
        for (Enemy enemy : totalEnemies)
            enemy.restoreHealth();
    }

    private void makeEnemiesReady(Array<Enemy> queue)
    {
        for (Enemy enemy : queue)
        {
            enemy.isUsed = true;

            enemy.setPosition(RandomPositioner.getRandomPosition(enemy.getSprite().getHeight()));
            enemy.calculateRotation();
            enemy.calculateVelocity();
        }
    }

    private void addOranges(int amount, int groupIndex)
    {
        Array<Enemy> queue = queueManager.getQueue(groupIndex);
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
        if (currentGroup == groupIndex)
        {
            sendQueueImmediatly(queue);
        }
    }

    private void addAnanases(int amount, int groupIndex)
    {
        Array<Enemy> queue = queueManager.getQueue(groupIndex);
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
        if (currentGroup == groupIndex)
        {
            sendQueueImmediatly(queue);
        }
    }

    private void addWatermelons(int amount, int groupIndex)
    {
        Array<Enemy> queue = queueManager.getQueue(groupIndex);
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
        if (currentGroup == groupIndex)
        {
            sendQueueImmediatly(queue);
        }
    }

    public void sendQueueImmediatly(Array<Enemy> queue)
    {
        makeEnemiesReady(queue);
        currentEnemies.addAll(queue);
        if (Debug.LOG_WAVES)
        {
            Logger.log("CURRENTGROUPINDEX: " + currentGroup);
            Logger.log("QUEUE RELEASED!, size: " + queue.size);
            Logger.log("current enemies size: " + currentEnemies.size);
        }
        queue.clear();
    }

    public boolean allQueuesEmpty()
    {
        int counterEmpty = 0;
        for (Array<Enemy> queue : queueManager.getAllQueues())
        {
            if (queue.size == 0)
                counterEmpty++;
        }
        return counterEmpty == queueManager.getAllQueues().size;
    }

    public Array<Array<Enemy>> getQueues()
    {
        return queueManager.getAllQueues();
    }
}