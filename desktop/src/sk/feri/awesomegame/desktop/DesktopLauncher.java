package sk.feri.awesomegame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import sk.feri.awesomegame.AwesomeGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Awesome Game";
		config.width = 480;
		config.height = 800;
		new LwjglApplication(new AwesomeGame(), config);
	}
}
