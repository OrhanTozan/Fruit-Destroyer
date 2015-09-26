package com.nahroto.fruitdestroyer.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.nahroto.fruitdestroyer.Application;

public class DesktopLauncher
{
	public static void main (String[] arg)
	{
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Fruit Destroyer";
		config.width = 480;
		config.height = 800;
		new LwjglApplication(new Application(), config);
	}
}
