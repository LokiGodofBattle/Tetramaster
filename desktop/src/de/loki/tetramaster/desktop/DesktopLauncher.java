package de.loki.tetramaster.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import de.loki.tetramaster.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title = "Geometric Space";
		config.width = 1080;
		config.height = 1920;
		config.fullscreen = false;
		config.resizable = false;

		new LwjglApplication(new Main(), config);
	}
}
