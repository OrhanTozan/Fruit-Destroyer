package com.nahroto.fruitdestroyer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Player
{
    private Sprite sprite;
    private Vector2 unprojectedCoordinates;

    public Player(Sprite sprite)
    {
        this.sprite = sprite;
        this.sprite.setPosition(Constants.V_WIDTH / 2 - 32, Constants.V_HEIGHT / 2 - 28);
        this.sprite.setOrigin(32, 28);

        unprojectedCoordinates = new Vector2();
    }

    public void update(final Application APP)
    {
        if (Gdx.input.isTouched())
        {
            unprojectedCoordinates.set(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
            APP.viewport.unproject(unprojectedCoordinates);

            float deltaX = unprojectedCoordinates.x - Constants.V_WIDTH / 2;
            float deltaY = unprojectedCoordinates.y - Constants.V_HEIGHT / 2;

            sprite.setRotation((MathUtils.atan2(deltaX, deltaY) * MathUtils.radiansToDegrees) - 90);
        }
    }

    public void render(SpriteBatch batch)
    {
        sprite.draw(batch);
    }
}
