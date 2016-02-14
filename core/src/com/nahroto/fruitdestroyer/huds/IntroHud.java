package com.nahroto.fruitdestroyer.huds;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nahroto.fruitdestroyer.Application;
import com.nahroto.fruitdestroyer.Constants;
import com.nahroto.fruitdestroyer.Font;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class IntroHud extends Hud
{
    private final Application APP;

    private Font slimJoe;
    private Font bigJohn;

    private Image background;

    private Label title;
    private Label underscript1;
    private Label underscript2;

    public IntroHud(Application APP, Viewport viewport, SpriteBatch batch, Font slimJoe, Font bigJohn)
    {
        super(viewport, batch);
        this.APP = APP;
        this.slimJoe = slimJoe;
        this.bigJohn = bigJohn;

        background = new Image(new Texture("backgrounds/white.jpg"));
        title = new Label("NahroTo", new Label.LabelStyle(bigJohn.getFont(), Color.WHITE));
        underscript1 = new Label("a ", new Label.LabelStyle(slimJoe.getFont(), Color.WHITE));
        underscript2 = new Label(" production", new Label.LabelStyle(slimJoe.getFont(), Color.WHITE));

        title.setPosition(Constants.V_WIDTH / 2, Constants.V_HEIGHT / 2, Align.center);
        underscript1.setPosition(title.getX(), title.getY() + 6, Align.bottomRight);
        underscript2.setPosition(title.getX(), title.getY(), Align.topLeft);

        background.addAction(alpha(0f));
        title.addAction(color(Color.BLACK));
        underscript1.addAction(color(Color.BLACK));
        underscript2.addAction(color(Color.BLACK));

        actors.add(background);
        actors.add(title);
        actors.add(underscript1);
        actors.add(underscript2);

        addAllActors();
    }

    public void start()
    {
        title.addAction(sequence(color(Color.WHITE, 2f),
                parallel(color(Color.BLACK, 3f), run(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        background.addAction(alpha(1f, 3f));
                    }
                })),
                delay(2f),
                parallel(alpha(0f, 1f), run(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        underscript1.addAction(alpha(0f, 1f));
                        underscript2.addAction(alpha(0f, 1f));
                    }
                })),
                delay(0.7f),
                run(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        APP.setScreen(APP.loadingScreen);
                    }
                })));
    }
}
