package com.nahroto.fruitdestroyer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nahroto.fruitdestroyer.screens.DeadScreen;
import com.nahroto.fruitdestroyer.screens.GameScreen;
import com.nahroto.fruitdestroyer.screens.IntroScreen;
import com.nahroto.fruitdestroyer.screens.LoadingScreen;
import com.nahroto.fruitdestroyer.screens.LoadingScreen2;
import com.nahroto.fruitdestroyer.screens.MenuScreen;

public class Application extends Game
{
	public SpriteBatch batch;
	public OrthographicCamera camera;
	public Viewport viewport;
	public AssetManager assets;

	public IntroScreen introScreen;
	public LoadingScreen loadingScreen;
	public MenuScreen menuScreen;
	public LoadingScreen2 loadingScreen2;
	public GameScreen gameScreen;
	public DeadScreen deadScreen;

	@Override
	public void create ()
	{
		batch = new SpriteBatch();
		camera = new OrthographicCamera(Constants.V_WIDTH, Constants.V_HEIGHT);
		viewport = new FitViewport(Constants.V_WIDTH, Constants.V_HEIGHT, camera);
		assets = new AssetManager();

		assets.load("music/epictheme.ogg", Music.class);
		introScreen = new IntroScreen(this);
		loadingScreen = new LoadingScreen(this);

		if (assets.update())
			setScreen(introScreen);
	}

	@Override
	public void render()
	{
		super.render();
		Logger.log(Gdx.graphics.getFramesPerSecond());
	}

	@Override
	public void dispose()
	{
		if (Debug.INFO)
			Logger.log("disposed");
		assets.dispose();
		loadingScreen.dispose();
		menuScreen.dispose();
		loadingScreen2.dispose();
	}
}
