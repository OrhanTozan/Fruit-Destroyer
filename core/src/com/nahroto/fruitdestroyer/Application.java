package com.nahroto.fruitdestroyer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nahroto.fruitdestroyer.screens.DeadScreen;
import com.nahroto.fruitdestroyer.screens.GameScreen;
import com.nahroto.fruitdestroyer.screens.LoadingScreen;
import com.nahroto.fruitdestroyer.screens.LoadingScreen2;
import com.nahroto.fruitdestroyer.screens.MenuScreen;

public class Application extends Game
{
	public SpriteBatch batch;
	public OrthographicCamera camera;
	public Viewport viewport;
	public AssetManager assets;

	public LoadingScreen loadingScreen;
	public MenuScreen menuScreen;
	public LoadingScreen2 loadingScreen2;
	public GameScreen gameScreen;
	public DeadScreen deadScreen;

	
	@Override
	public void create ()
	{
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		viewport = new FitViewport(Constants.V_WIDTH, Constants.V_HEIGHT, camera);
		assets = new AssetManager();

		loadingScreen = new LoadingScreen(this);

		setScreen(loadingScreen);
	}

	@Override
	public void dispose()
	{
		Logger.log("blyat");
		assets.dispose();
	}
}
