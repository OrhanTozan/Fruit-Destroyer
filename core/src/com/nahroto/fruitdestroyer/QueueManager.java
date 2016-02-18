package com.nahroto.fruitdestroyer;

import com.badlogic.gdx.utils.Array;
import com.nahroto.fruitdestroyer.entities.enemies.Enemy;

public class QueueManager
{
    private Array<Enemy> queue1 = new Array<Enemy>();
    private Array<Enemy> queue2 = new Array<Enemy>();
    private Array<Enemy> queue3 = new Array<Enemy>();
    private Array<Enemy> queue4 = new Array<Enemy>();

    private Array<Array<Enemy>> allQueues = new Array<Array<Enemy>>();

    public QueueManager()
    {
        allQueues.add(queue1);
        allQueues.add(queue2);
        allQueues.add(queue3);
        allQueues.add(queue4);
    }

    public Array<Enemy> getQueue(int group)
    {
        switch (group)
        {
            case 1:
                return queue1;
            case 2:
                return queue2;
            case 3:
                return queue3;
            case 4:
                return queue4;
            default:
                return null;
        }
    }

    public void update(int currentGroup, WaveGenerator waveGenerator)
    {

        if (getQueue(currentGroup).size != 0) // IF QUEUE ISNT EMPTY
        {
            waveGenerator.sendQueueImmediatly(getQueue(currentGroup)); // DEPLOY QUEUE
        }
    }

    public Array<Array<Enemy>> getAllQueues()
    {
        return allQueues;
    }
}
