package com.ecoquiz.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.ecoquiz.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.height = (int) (LwjglApplicationConfiguration.getDesktopDisplayMode().height * 0.9f);
		config.width = config.height / 16 * 9;
//		config.fullscreen = true;

//		config.width = config.height / 4 * 3;
//		config.width = config.height / 20 * 9;
		config.initialBackgroundColor = Main.clBackground;
		config.title = "EcoAccount";
//		config.addIcon("images/icon_logo32.png", Files.FileType.Internal);
		config.resizable = false;
		config.y = 0;

		new LwjglApplication(new Main(), config);
	}
}
