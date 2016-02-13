package com.nahroto.fruitdestroyer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
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
	public Preferences prefs;
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

	public AdsController adsController;

	private boolean firstTime = true;

	public Application(AdsController adsController)
	{
		if (adsController != null)
			this.adsController = adsController;
		else
			this.adsController = new DummyAdsController();
	}


	@Override
	public void create ()
	{
		prefs = Gdx.app.getPreferences("Fruit_Destroyer_stats");
		batch = new SpriteBatch();
		camera = new OrthographicCamera(Constants.V_WIDTH, Constants.V_HEIGHT);
		viewport = new FitViewport(Constants.V_WIDTH, Constants.V_HEIGHT, camera);
		assets = new AssetManager();
		introScreen = new IntroScreen(this, new Font(this, "slim-joe.otf", "fonts/slim-joe.otf", 48, Color.WHITE, true), new Font(this, "big-john.otf", "fonts/big-john.otf", 72, Color.WHITE, true));
		loadingScreen = new LoadingScreen(this);

		assets.load("music/transition.mp3", Music.class);

	}

	@Override
	public void render()
	{
		super.render();
		if (assets.update() && firstTime)
		{
			setScreen(introScreen);
			firstTime = false;
		}
		// Logger.log(Gdx.graphics.getFramesPerSecond());
	}

	@Override
	public void dispose()
	{
		super.dispose();
		if (Debug.INFO)
			Logger.log("disposed");
		assets.dispose();
		loadingScreen.dispose();
		menuScreen.dispose();
		loadingScreen2.dispose();
	}

	public void showAd()
	{
		if (adsController.isWifiConnected())
		{
			adsController.showInterstitialAd(new Runnable()
			{
				@Override
				public void run()
				{
					if (Debug.AD_INFO)
						System.out.println("Interstitial app closed");
				}
			});
		}
		else if (Debug.AD_INFO)
			System.out.println("Interstitial ad not (yet) loaded");
	}
}
