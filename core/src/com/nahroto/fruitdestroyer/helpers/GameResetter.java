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

    private Array<Enemy> currentEnemies;
    private Array<Bullet> currentBullets;
    private Array<Corpse> currentCorpses;

    private Array<Enemy> totalEnemies;
    private Array<Bullet> totalBullets;
    private Array<Corpse> totalCorpses;

    public GameResetter(final Application APP, Array<Enemy> currentEnemies, Array<Bullet> currentBullets, Array<Corpse> currentCorpses, Array<Enemy> totalEnemies, Array<Bullet> totalBullets, Array<Corpse> totalCorpses)
    {
        this.APP = APP;
        this.currentEnemies= currentEnemies;
        this.currentBullets = currentBullets;
        this.currentCorpses = currentCorpses;
        this.totalEnemies = totalEnemies;
        this.totalBullets = totalBullets;
        this.totalCorpses = totalCorpses;
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

        // RESET WAVE
        WaveGenerator.wave = 1;

        // RESET POINTS
        BuyHud.pointsValue = 0;

        gameHud.updateWaveText();

        // RESET WEAPON STATS
        ExtraAmmoOverlay.currentValue = Bullet.START_MAGSIZE;
        ExtraAmmoOverlay.nextValue = ExtraAmmoOverlay.currentValue + ExtraAmmoOverlay.UPGRADE_STEP;

        ExtraAccuracyOverlay.currentValue = (1 / Bullet.START_RECOIL);
        ExtraAccuracyOverlay.nextValue = ExtraAccuracyOverlay.currentValue + ExtraAccuracyOverlay.UPGRADE_STEP;

        ExtraReloadSpeedOverlay.currentPercentage = 100;
        ExtraReloadSpeedOverlay.nextPercentage = ExtraReloadSpeedOverlay.currentPercentage + ExtraReloadSpeedOverlay.UPGRADE_STEP;

        ExtraKnockbackOverlay.currentValue = Bullet.START_KNOCKBACKPOWER;
        ExtraKnockbackOverlay.nextValue = ExtraKnockbackOverlay.currentValue + ExtraKnockbackOverlay.UPGRADE_STEP;

        APP.setScreen(gameScreen);
    }

    public void setGameScreen(GameScreen gameScreen)
    {
        this.gameScreen = gameScreen;
    }

    public void setGameHud(GameHud gameHud)
    {
        this.gameHud = gameHud;
    }
}
