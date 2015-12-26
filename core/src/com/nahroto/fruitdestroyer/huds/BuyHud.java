package com.nahroto.fruitdestroyer.huds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nahroto.fruitdestroyer.Constants;
import com.nahroto.fruitdestroyer.screens.GameScreen;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class BuyHud extends Hud
{
    private final float EASE_TIME = 0.7f;
    private final int SPACE_BETWEEN_BUTTONS = 60;

    private boolean isShowed;
    private boolean isEasing;

    private Image overlay;
    private Image upgradesTitle;
    private Image blackShader;

    private ImageButton extraAmmoButton;
    private ImageButton accuracyButton;

    private Runnable toggleBuying;
    private Runnable toggleEasing;
    private Runnable resetPosition;
    private Runnable hideBlackShader;

    private InputMultiplexer inputMultiplexer;

    public BuyHud(Viewport viewport, SpriteBatch batch, TextureAtlas gameScreenAtlas, Texture blackShaderTexture)
    {
        super(viewport, batch);

        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);

        toggleBuying = new Runnable()
        {
            @Override
            public void run()
            {
                GameScreen.buying = !GameScreen.buying;
            }
        };

        toggleEasing = new Runnable()
        {
            @Override
            public void run()
            {
                isEasing = !isEasing;
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

        blackShader = new Image(blackShaderTexture);
        blackShader.addAction(alpha(0f));

        overlay = new Image(gameScreenAtlas.findRegion("overlay"));
        upgradesTitle = new Image(gameScreenAtlas.findRegion("upgrades-text"));

        upgradesTitle.setPosition(overlay.getX(Align.top), overlay.getY(Align.top) - 70, Align.top);

        extraAmmoButton = new ImageButton(new TextureRegionDrawable(gameScreenAtlas.findRegion("moreAmmoUpgrade")), new TextureRegionDrawable(gameScreenAtlas.findRegion("moreAmmoUpgrade-down")));
        accuracyButton = new ImageButton(new TextureRegionDrawable(gameScreenAtlas.findRegion("accuracyUpgrade")), new TextureRegionDrawable(gameScreenAtlas.findRegion("accuracyUpgrade-down")));

        extraAmmoButton.setPosition(overlay.getX() + SPACE_BETWEEN_BUTTONS, upgradesTitle.getY(Align.bottom) - 30, Align.topLeft);
        accuracyButton.setPosition(overlay.getX() + extraAmmoButton.getWidth() + SPACE_BETWEEN_BUTTONS, upgradesTitle.getY(Align.bottom) - 30, Align.topLeft);

        resetPosition();

        actors.add(blackShader);
        actors.add(overlay);
        actors.add(upgradesTitle);
        actors.add(extraAmmoButton);
        actors.add(accuracyButton);

        addAllActorsToStage();
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
        extraAmmoButton.setPosition(overlay.getX() + SPACE_BETWEEN_BUTTONS, upgradesTitle.getY(Align.bottom) - 30, Align.topLeft);
        accuracyButton.setPosition(overlay.getX() + extraAmmoButton.getWidth() + SPACE_BETWEEN_BUTTONS, upgradesTitle.getY(Align.bottom) - 30, Align.topLeft);
    }

    public void toggle(InputMultiplexer gameScreenInput)
    {
        if (isShowed == false)
        {
            Gdx.input.setInputProcessor(inputMultiplexer);
            easeIn();
            showBlackShader();
            isShowed = true;
        }

        else
        {
            Gdx.input.setInputProcessor(gameScreenInput);
            easeOut();
            isShowed = false;
        }
    }

    private void easeIn()
    {
        isEasing = true;
        overlay.addAction(sequence(
                moveToAligned(Constants.V_WIDTH / 2, Constants.V_HEIGHT / 2, Align.center, EASE_TIME, Interpolation.pow2Out),
                run(toggleEasing)));
        GameScreen.buying = true;
    }

    private void easeOut()
    {
        isEasing = true;
        overlay.addAction(sequence(
                parallel(moveToAligned(0, Constants.V_HEIGHT / 2, Align.right, EASE_TIME, Interpolation.pow2Out), run(hideBlackShader)),
                run(toggleEasing),
                run(toggleBuying),
                run(resetPosition)));
    }

    private void resetPosition()
    {
        overlay.setPosition(Constants.V_WIDTH, Constants.V_HEIGHT / 2, Align.left);
    }

    private void showBlackShader()
    {
        blackShader.addAction(alpha(1f, EASE_TIME));
    }

    private void hideBlackShader()
    {
        blackShader.addAction(
                alpha(0f, EASE_TIME));
    }
}
