package com.mygdx.game.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.addIcon("logos/logo128.png", FileType.Internal);
		config.addIcon("logos/logo64.png", FileType.Internal);
		config.addIcon("logos/logo32.png", FileType.Internal);
		config.addIcon("logos/logo16.png", FileType.Internal);
		LwjglApplication application =  new LwjglApplication(new Game(), config);
	}
}
