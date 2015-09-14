package com.nahroto.fruitdestroyer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nahroto.fruitdestroyer.screens.GameScreen;

public class Application extends Game
{
	public SpriteBatch batch;
	public OrthographicCamera camera;
	public Viewport viewport;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		viewport = new FitViewport(Constants.V_WIDTH, Constants.V_HEIGHT, camera);
		setScreen(new GameScreen(this));
	}
}
