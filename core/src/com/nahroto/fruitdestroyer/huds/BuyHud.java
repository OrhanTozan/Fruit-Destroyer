package com.nahroto.fruitdestroyer.huds;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nahroto.fruitdestroyer.Constants;
import com.nahroto.fruitdestroyer.Logger;
import com.nahroto.fruitdestroyer.screens.GameScreen;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class BuyHud extends Hud
{
    private boolean on;
    private Image overlay;
    private Runnable toggleBuying;

    public BuyHud(Viewport viewport, SpriteBatch batch, TextureAtlas gameScreenAtlas)
    {
        super(viewport, batch);

        toggleBuying = new Runnable()
        {
            @Override
            public void run()
            {
                GameScreen.buying = !GameScreen.buying;
            }
        };

        overlay = new Image(gameScreenAtlas.findRegion("overlay"));
        overlay.setPosition(Constants.V_WIDTH, Constants.V_HEIGHT / 2, Align.left);


        actors.add(overlay);

        addAllActorsToStage();
    }

    public void toggle()
    {
        if (!on)
        {
            Logger.log("toggled");
            easeIn();
            on = true;
        }

        else
        {
            easeOut();
            on = false;
        }
    }

    private void easeIn()
    {
        Logger.log(GameScreen.buying);
        overlay.addAction(parallel(moveToAligned(Constants.V_WIDTH / 2, Constants.V_HEIGHT / 2, Align.center, 0.5f, Interpolation.pow2Out), run(toggleBuying)));
        Logger.log(GameScreen.buying);
    }

    private void easeOut()
    {
        overlay.addAction(sequence(moveToAligned(Constants.V_WIDTH, Constants.V_HEIGHT / 2, Align.left, 0.5f, Interpolation.pow2Out), run(toggleBuying)));
    }
}
