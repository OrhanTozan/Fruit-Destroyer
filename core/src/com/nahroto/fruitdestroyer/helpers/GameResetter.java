package com.nahroto.fruitdestroyer.helpers;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Array;
import com.nahroto.fruitdestroyer.Application;
import com.nahroto.fruitdestroyer.Constants;
import com.nahroto.fruitdestroyer.WaveGenerator;
import com.nahroto.fruitdestroyer.entities.Bullet;
import com.nahroto.fruitdestroyer.entities.Corpse;
import com.nahroto.fruitdestroyer.entities.Player;
import com.nahroto.fruitdestroyer.entities.enemies.Enemy;
import com.nahroto.fruitdestroyer.huds.BuyHud;
import com.nahroto.fruitdestroyer.huds.GameHud;
import com.nahroto.fruitdestroyer.huds.powerupoverlays.PowerupOverlay;
import com.nahroto.fruitdestroyer.huds.upgradeoverlays.ExtraAccuracyOverlay;
import com.nahroto.fruitdestroyer.huds.upgradeoverlays.ExtraAmmoOverlay;
import com.nahroto.fruitdestroyer.huds.upgradeoverlays.ExtraKnockbackOverlay;
import com.nahroto.fruitdestroyer.huds.upgradeoverlays.ExtraReloadSpeedOverlay;
import com.nahroto.fruitdestroyer.screens.GameScreen;

public class GameResetter
{
    private final Application APP;
    private GameScreen gameScreen;

    private GameHud gameHud;

    private Array<Enemy> waveQueue;

    private Array<Enemy> currentEnemies;
    private Array<Bullet> currentBullets;
    private Array<Corpse> currentCorpses;

    private Array<Enemy> totalEnemies;
    private Array<Bullet> totalBullets;
    private Array<Corpse> totalCorpses;

    private Player player;

    private ExtraAmmoOverlay extraAmmoOverlay;
    private ExtraAccuracyOverlay extraAccuracyOverlay;
    private ExtraReloadSpeedOverlay extraReloadSpeedOverlay;
    private ExtraKnockbackOverlay extraKnockbackOverlay;
    private PowerupOverlay marksmanOverlay;
    private PowerupOverlay instaKillOverlay;
    private PowerupOverlay bombOverlay;

    public GameResetter(final Application APP, Player player, Array<Enemy> currentEnemies, Array<Bullet> currentBullets, Array<Corpse> currentCorpses, Array<Enemy> totalEnemies, Array<Bullet> totalBullets, Array<Corpse> totalCorpses)
    {
        this.APP = APP;
        this.currentEnemies= currentEnemies;
        this.currentBullets = currentBullets;
        this.currentCorpses = currentCorpses;
        this.totalEnemies = totalEnemies;
        this.totalBullets = totalBullets;
        this.totalCorpses = totalCorpses;
        this.player = player;
    }

    public void newGame()
    {
        for (Enemy enemy : totalEnemies)
            enemy.isUsed = false;

        for (Bullet bullet : totalBullets)
            bullet.isUsed = false;

        for (Corpse corpse : totalCorpses)
        {
            corpse.isBusy = false;
            corpse.isDone = false;
        }

        // CLEAR ALL ENEMIES
        currentEnemies.clear();

        // CLEAR ALL BULLETS
        currentBullets.clear();

        // CLEAR ALL CORPSES
        currentCorpses.clear();

        // RESET POINTS
        BuyHud.pointsValue = 0;

        // RESET WAVE VALUE
        WaveGenerator.wave = 1;

        // CLEAR WAVE QUEUE
        waveQueue.clear();

        // UPDATE WAVE COUNTER
        gameHud.updateWaveText();

        // RESET WEAPON STATS
        Bullet.resetWeapon();

        player.setReloadingConfig(100);

        // RESET UPGRADE STATS
        extraAmmoOverlay.reset();
        extraAccuracyOverlay.reset();
        extraReloadSpeedOverlay.reset();
        extraKnockbackOverlay.reset();
        marksmanOverlay.reset();
        instaKillOverlay.reset();
        bombOverlay.reset();
    }

    public void setGameScreen(GameScreen gameScreen)
    {
        this.gameScreen = gameScreen;
    }

    public GameScreen getGameScreen()
    {
        return gameScreen;
    }

    public void setGameHud(GameHud gameHud)
    {
        this.gameHud = gameHud;
    }

    public void setOverlays(ExtraAmmoOverlay extraAmmoOverlay, ExtraAccuracyOverlay extraAccuracyOverlay, ExtraReloadSpeedOverlay extraReloadSpeedOverlay, ExtraKnockbackOverlay extraKnockbackOverlay, PowerupOverlay marksmanOverlay, PowerupOverlay instaKillOverlay, PowerupOverlay bombOverlay)
    {
        this.extraAmmoOverlay = extraAmmoOverlay;
        this.extraAccuracyOverlay = extraAccuracyOverlay;
        this.extraReloadSpeedOverlay = extraReloadSpeedOverlay;
        this.extraKnockbackOverlay = extraKnockbackOverlay;
        this.marksmanOverlay = marksmanOverlay;
        this.instaKillOverlay = instaKillOverlay;
        this.bombOverlay = bombOverlay;
    }

    public void setWaveQueue(Array<Enemy> waveQueue)
    {
        this.waveQueue = waveQueue;
    }
}
