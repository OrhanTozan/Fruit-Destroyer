package com.nahroto.fruitdestroyer.huds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nahroto.fruitdestroyer.Constants;
import com.nahroto.fruitdestroyer.Font;
import com.nahroto.fruitdestroyer.Logger;
import com.nahroto.fruitdestroyer.WaveGenerator;
import com.nahroto.fruitdestroyer.entities.Player;
import com.nahroto.fruitdestroyer.huds.powerupoverlays.PowerupOverlay;
import com.nahroto.fruitdestroyer.huds.upgradeoverlays.ExtraAccuracyOverlay;
import com.nahroto.fruitdestroyer.huds.upgradeoverlays.ExtraAmmoOverlay;
import com.nahroto.fruitdestroyer.huds.upgradeoverlays.ExtraKnockbackOverlay;
import com.nahroto.fruitdestroyer.huds.upgradeoverlays.ExtraReloadSpeedOverlay;
import com.nahroto.fruitdestroyer.screens.GameScreen;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class BuyHud extends Hud
{
    public static final int POINTS_REWARD = 3;

    private static final float EASE_TIME = 0.5f;
    private static final int UPGRADEBUTTONS_Y = 800;

    public static Integer pointsValue;

    private Label pointsLabel;

    private Image overlay;
    private Image pointsOverlay;
    private Image upgradesTitle;
    private Image powerupTitle;
    private Image blackShader;

    private ImageButton extraAmmoButton;
    private ImageButton accuracyButton;
    private ImageButton reloadSpeedButton;
    private ImageButton knockbackButton;

    private ImageButton marksmanButton;
    private ImageButton instakillButton;
    private ImageButton bombButton;

    private ExtraAmmoOverlay extraAmmoOverlay;
    private ExtraAccuracyOverlay extraAccuracyOverlay;
    private ExtraReloadSpeedOverlay extraReloadSpeedOverlay;
    private ExtraKnockbackOverlay extraKnockbackOverlay;

    private PowerupOverlay marksmanOverlay;
    private PowerupOverlay instakillOverlay;
    private PowerupOverlay bombOverlay;

    private ImageButton doneButton;

    private Runnable toggleBuying;
    private Runnable resetPosition;
    private Runnable hideBlackShader;
    private Runnable animateWave;
    private Runnable animateNextWave;
    private Runnable prepareWaveStatus;

    private Runnable setGameScreenInput;
    private Runnable turnON;

    private InputMultiplexer inputMultiplexer;

    public BuyHud(Viewport viewport, SpriteBatch batch, final GameHud gameHud, final WaveGenerator waveGenerator, TextureAtlas gameScreenAtlas, Texture blackShaderTexture, final InputMultiplexer gameScreenInput, Player player)
    {
        super(viewport, batch);

        inputMultiplexer = new InputMultiplexer();
        pointsValue = new Integer(0);

        inputMultiplexer.addProcessor(stage);

        toggleBuying = new Runnable()
        {
            @Override
            public void run()
            {
                GameScreen.buying = !GameScreen.buying;
            }
        };

        resetPosition = new Runnable()
        {
            @Override
            public void run()
            {
                resetPosition();
            }
        };

        hideBlackShader = new Runnable()
        {
            @Override
            public void run()
            {
                hideBlackShader();
            }
        };

        prepareWaveStatus = new Runnable()
        {
            @Override
            public void run()
            {
                gameHud.prepareWaveStatus();
            }
        };

        animateWave = new Runnable()
        {
            @Override
            public void run()
            {
                gameHud.animateWaveLabel(waveGenerator.isBuyRound());
            }
        };

        animateNextWave = new Runnable()
        {
            @Override
            public void run()
            {
                Logger.log("animatin next wave");
                gameHud.animateNextWave();
            }
        };

        setGameScreenInput = new Runnable()
        {
            @Override
            public void run()
            {
                Gdx.input.setInputProcessor(gameScreenInput);
            }
        };

        turnON = new Runnable()
        {
            @Override
            public void run()
            {
                addPoints(BuyHud.POINTS_REWARD);
                Gdx.input.setInputProcessor(inputMultiplexer);
                easeIn();
                showBlackShader();
            }
        };

        blackShader = new Image(blackShaderTexture);
        blackShader.setPosition(-80, -80);
        blackShader.addAction(alpha(0f));

        pointsLabel = new Label(pointsValue.toString(), new Label.LabelStyle(new Font("fonts/trompus.otf", 70, new Color(0.25f, 0.85f, 0.15f, 1f), Color.BLACK, 3, true).getFont(), new Color(0.25f, 0.85f, 0.15f, 1f)));

        overlay = new Image(gameScreenAtlas.findRegion("overlay"));
        pointsOverlay = new Image(gameScreenAtlas.findRegion("points-overlay"));
        upgradesTitle = new Image(gameScreenAtlas.findRegion("upgrades-text"));
        powerupTitle = new Image(gameScreenAtlas.findRegion("powerup-text"));

        resetPosition();

        pointsOverlay.setPosition(overlay.getX() + 70, overlay.getY() + 70);
        pointsLabel.setPosition(pointsOverlay.getX(Align.center), pointsOverlay.getY(Align.center) - 25, Align.center);
        upgradesTitle.setPosition(overlay.getX(Align.top), overlay.getY(Align.top) - 70, Align.top);
        powerupTitle.setPosition(overlay.getX(Align.top), overlay.getY(Align.center) - 30, Align.top);

        extraAmmoButton = new ImageButton(new TextureRegionDrawable(gameScreenAtlas.findRegion("moreAmmoUpgrade")), new TextureRegionDrawable(gameScreenAtlas.findRegion("moreAmmoUpgrade-down")));
        accuracyButton = new ImageButton(new TextureRegionDrawable(gameScreenAtlas.findRegion("accuracyUpgrade")), new TextureRegionDrawable(gameScreenAtlas.findRegion("accuracyUpgrade-down")));
        reloadSpeedButton = new ImageButton(new TextureRegionDrawable(gameScreenAtlas.findRegion("reloadUpgrade")), new TextureRegionDrawable(gameScreenAtlas.findRegion("reloadUpgrade-down")));
        knockbackButton = new ImageButton(new TextureRegionDrawable(gameScreenAtlas.findRegion("knockbackUpgrade")), new TextureRegionDrawable(gameScreenAtlas.findRegion("knockbackUpgrade-down")));

        marksmanButton = new ImageButton(new TextureRegionDrawable(gameScreenAtlas.findRegion("marksman-up")), new TextureRegionDrawable(gameScreenAtlas.findRegion("marksman-down")));
        instakillButton = new ImageButton(new TextureRegionDrawable(gameScreenAtlas.findRegion("instakill-up")), new TextureRegionDrawable(gameScreenAtlas.findRegion("instakill-down")));
        bombButton = new ImageButton(new TextureRegionDrawable(gameScreenAtlas.findRegion("bomb-up")), new TextureRegionDrawable(gameScreenAtlas.findRegion("bomb-down")));

        extraAmmoButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                addOverlayActors("extraAmmo");
            }
        });
        accuracyButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                addOverlayActors("extraAccuracy");
            }
        });
        reloadSpeedButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                addOverlayActors("extraReloadSpeed");
            }
        });
        knockbackButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                addOverlayActors("extraKnockback");
            }
        });
        marksmanButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                addOverlayActors("Marksman");
            }
        });
        instakillButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                addOverlayActors("InstaKiller");
            }
        });
        bombButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                addOverlayActors("Bacteria Bomb");
            }
        });

        extraAmmoOverlay = new ExtraAmmoOverlay(extraAmmoButton, gameScreenAtlas, this, player);
        extraAccuracyOverlay = new ExtraAccuracyOverlay(accuracyButton, gameScreenAtlas, this, player);
        extraReloadSpeedOverlay = new ExtraReloadSpeedOverlay(reloadSpeedButton, gameScreenAtlas, this, player);
        extraKnockbackOverlay = new ExtraKnockbackOverlay(knockbackButton, gameScreenAtlas, this, player);

        marksmanOverlay = new PowerupOverlay("Marksman", "marksman", 1, new ImageButton(new TextureRegionDrawable(gameScreenAtlas.findRegion("marksman-up")), new TextureRegionDrawable(gameScreenAtlas.findRegion("marksman-down"))), gameScreenAtlas, this);
        instakillOverlay = new PowerupOverlay("InstaKiller", "instakill", 1, new ImageButton(new TextureRegionDrawable(gameScreenAtlas.findRegion("instakill-up")), new TextureRegionDrawable(gameScreenAtlas.findRegion("instakill-down"))), gameScreenAtlas, this);
        bombOverlay = new PowerupOverlay("Bacteria Bomb", "bomb", 1, new ImageButton(new TextureRegionDrawable(gameScreenAtlas.findRegion("bomb-up")), new TextureRegionDrawable(gameScreenAtlas.findRegion("bomb-down"))), gameScreenAtlas, this);

        extraAmmoOverlay.setPosition(Constants.V_WIDTH / 2, Constants.V_HEIGHT / 2 + 25, Align.center);
        extraAccuracyOverlay.setPosition(Constants.V_WIDTH / 2, Constants.V_HEIGHT / 2 + 25, Align.center);
        extraReloadSpeedOverlay.setPosition(Constants.V_WIDTH / 2, Constants.V_HEIGHT / 2 + 25, Align.center);
        extraKnockbackOverlay.setPosition(Constants.V_WIDTH / 2, Constants.V_HEIGHT / 2 + 25, Align.center);

        marksmanOverlay.setPosition(Constants.V_WIDTH / 2, Constants.V_HEIGHT / 2 + 25, Align.center);
        instakillOverlay.setPosition(Constants.V_WIDTH / 2, Constants.V_HEIGHT / 2 + 25, Align.center);
        bombOverlay.setPosition(Constants.V_WIDTH / 2, Constants.V_HEIGHT / 2 + 25, Align.center);

        doneButton = new ImageButton(new TextureRegionDrawable(gameScreenAtlas.findRegion("upgradeDone")), new TextureRegionDrawable(gameScreenAtlas.findRegion("upgradeDone-down")));

        extraAmmoButton.setPosition(overlay.getX(Align.center)  - accuracyButton.getWidth(), overlay.getY() + UPGRADEBUTTONS_Y - 100, Align.center);
        accuracyButton.setPosition(overlay.getX(Align.center), overlay.getY() + UPGRADEBUTTONS_Y, Align.center);
        reloadSpeedButton.setPosition(overlay.getX(Align.center)  + accuracyButton.getWidth(), overlay.getY() + UPGRADEBUTTONS_Y - 100, Align.center);
        knockbackButton.setPosition(accuracyButton.getX(), overlay.getY() + UPGRADEBUTTONS_Y - 300);

        marksmanButton.setPosition(overlay.getX() + 150, powerupTitle.getY(Align.top) - 100, Align.top);
        instakillButton.setPosition(marksmanButton.getX(Align.right) + 100, powerupTitle.getY(Align.top) - 100, Align.top);
        bombButton.setPosition(instakillButton.getX(Align.right) + 100, powerupTitle.getY(Align.top) - 100, Align.top);

        doneButton.setPosition(overlay.getX(Align.center) + 110, pointsOverlay.getY(Align.center), Align.center);

        doneButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                easeOut();
                GameHud.reloadingIsAllowed = true;
            }
        });


        actors.add(blackShader);
        actors.add(overlay);
        actors.add(pointsOverlay);
        actors.add(pointsLabel);
        actors.add(upgradesTitle);
        actors.add(powerupTitle);
        actors.add(extraAmmoButton);
        actors.add(accuracyButton);
        actors.add(reloadSpeedButton);
        actors.add(knockbackButton);
        actors.add(marksmanButton);
        actors.add(instakillButton);
        actors.add(bombButton);
        actors.add(doneButton);

        addAllActors();
    }

    @Override
    public void update(float delta)
    {
        super.update(delta);
        updateComponents();
    }

    private void updateComponents()
    {
        upgradesTitle.setPosition(overlay.getX(Align.top), overlay.getY(Align.top) - 70, Align.top);
        powerupTitle.setPosition(overlay.getX(Align.top), overlay.getY(Align.center) - 30, Align.top);
        pointsOverlay.setPosition(overlay.getX() + 55, overlay.getY() + 55);
        pointsLabel.setPosition(pointsOverlay.getX(Align.center), pointsOverlay.getY(Align.center) - 25, Align.center);
        pointsLabel.setText(pointsValue.toString());
        extraAmmoButton.setPosition(overlay.getX(Align.center) - accuracyButton.getWidth(), overlay.getY() + UPGRADEBUTTONS_Y - 100, Align.center);
        accuracyButton.setPosition(overlay.getX(Align.center), overlay.getY() + UPGRADEBUTTONS_Y, Align.center);
        reloadSpeedButton.setPosition(overlay.getX(Align.center) + accuracyButton.getWidth(), overlay.getY() + UPGRADEBUTTONS_Y - 100, Align.center);
        knockbackButton.setPosition(accuracyButton.getX(), overlay.getY() + UPGRADEBUTTONS_Y - 300);
        marksmanButton.setPosition(overlay.getX() + 150, powerupTitle.getY(Align.top) - 100, Align.top);
        instakillButton.setPosition(marksmanButton.getX(Align.right) + 100, powerupTitle.getY(Align.top) - 100, Align.top);
        bombButton.setPosition(instakillButton.getX(Align.right) + 100, powerupTitle.getY(Align.top) - 100, Align.top);
        doneButton.setPosition(overlay.getX(Align.center) + 115, pointsOverlay.getY(Align.center), Align.center);

        extraAmmoOverlay.updatePriceLabelColor(pointsValue);
        extraAccuracyOverlay.updatePriceLabelColor(pointsValue);
        extraReloadSpeedOverlay.updatePriceLabelColor(pointsValue);
        extraKnockbackOverlay.updatePriceLabelColor(pointsValue);

        marksmanOverlay.updatePriceLabelColor(pointsValue);
        instakillOverlay.updatePriceLabelColor(pointsValue);
        bombOverlay.updatePriceLabelColor(pointsValue);

    }


    public void addBuyOverlayActors()
    {
        actors.add(blackShader);
        actors.add(overlay);
        actors.add(pointsOverlay);
        actors.add(pointsLabel);
        actors.add(upgradesTitle);
        actors.add(powerupTitle);
        actors.add(extraAmmoButton);
        actors.add(accuracyButton);
        actors.add(reloadSpeedButton);
        actors.add(knockbackButton);
        actors.add(marksmanButton);
        actors.add(instakillButton);
        actors.add(bombButton);
        actors.add(doneButton);
        addAllActors();
    }

    public void addOverlayActors(String upgradeType)
    {
        actors.add(blackShader);
        actors.add(pointsOverlay);
        actors.add(pointsLabel);
        if (upgradeType.equals("extraAmmo"))
            actors.addAll(extraAmmoOverlay.getActors());
        else if (upgradeType.equals("extraAccuracy"))
            actors.addAll(extraAccuracyOverlay.getActors());
        else if (upgradeType.equals("extraReloadSpeed"))
            actors.addAll(extraReloadSpeedOverlay.getActors());
        else if (upgradeType.equals("extraKnockback"))
            actors.addAll(extraKnockbackOverlay.getActors());
        else if (upgradeType.equals("Marksman"))
            actors.addAll(marksmanOverlay.getActors());
        else if (upgradeType.equals("InstaKiller"))
            actors.addAll(instakillOverlay.getActors());
        else if (upgradeType.equals("Bacteria Bomb"))
            actors.addAll(bombOverlay.getActors());
        addAllActors();
    }

    public void turnON()
    {
        this.getStage().addAction(sequence(delay(1f), run(turnON)));
    }

    private void easeIn()
    {
        GameScreen.buying = true;
        overlay.addAction(sequence(moveToAligned(Constants.V_WIDTH / 2, Constants.V_HEIGHT / 2, Align.center, EASE_TIME, Interpolation.pow2Out)));
    }

    private void easeOut()
    {
        overlay.addAction(sequence(parallel(moveToAligned(0, Constants.V_HEIGHT / 2, Align.right, EASE_TIME, Interpolation.pow2Out), run(hideBlackShader)), run(resetPosition), run(prepareWaveStatus), run(animateNextWave), run(toggleBuying)));
    }

    private void resetPosition()
    {
        overlay.setPosition(Constants.V_WIDTH, Constants.V_HEIGHT / 2, Align.left);
    }

    private void showBlackShader()
    {
        blackShader.addAction(alpha(1f, EASE_TIME, Interpolation.pow2Out));
    }

    private void hideBlackShader()
    {
        blackShader.addAction(alpha(0f, EASE_TIME, Interpolation.pow2Out));
    }

    public void addPoints(int amount)
    {
        pointsValue += amount;
        pointsLabel.setText(pointsValue.toString());
    }

    public void reducePoints(int amount)
    {
        pointsValue -= amount;
        pointsLabel.setText(pointsValue.toString());
    }

    public void setPoints(int amount)
    {
        pointsValue = amount;
        pointsLabel.setText(pointsValue.toString());
    }

    public int getPoints()
    {
        return pointsValue;
    }

    public ExtraAmmoOverlay getExtraAmmoOverlay()
    {
        return extraAmmoOverlay;
    }

    public ExtraAccuracyOverlay getExtraAccuracyOverlay()
    {
        return extraAccuracyOverlay;
    }

    public ExtraReloadSpeedOverlay getExtraReloadSpeedOverlay()
    {
        return extraReloadSpeedOverlay;
    }

    public ExtraKnockbackOverlay getExtraKnockbackOverlay()
    {
        return extraKnockbackOverlay;
    }
}
