package com.nahroto.fruitdestroyer.huds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Hud
{
    protected Stage stage;
    protected Array<Actor> actors;

    public Hud(Viewport viewport, SpriteBatch batch)
    {
        stage = new Stage(viewport, batch);
        actors = new Array<Actor>();
        Gdx.input.setInputProcessor(stage);
    }

    public void addAllActors()
    {
        for (Actor actor : actors)
        {
            stage.addActor(actor);
        }
    }

    public void removeAllActors()
    {
        for (Actor actor : stage.getActors())
        {
            actor.remove();
        }
    }

    public void emptyActorList()
    {
        actors.clear();
    }

    public void update(float delta)
    {
        stage.act(delta);
    }

    public void render()
    {
        stage.draw();
    }

    public Stage getStage()
    {
        return stage;
    }

    public Array<Actor> getActors()
    {
        return actors;
    }
}
