package sk.feri.awesomegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class Settings {
	public static boolean soundEnabled = true;
	public static int[] highscores = new int[] {100, 80, 50, 30, 10};
	public final static String file = ".awesomegame";

	public static void load () {
		try {
			FileHandle filehandle = Gdx.files.external(file);
			
			String[] strings = filehandle.readString().split("\n");
			
			soundEnabled = Boolean.parseBoolean(strings[0]);
			for (int i = 0; i < 5; i++) {
				highscores[i] = Integer.parseInt(strings[i+1]);
			}
		} catch (Throwable e) {

			//TODO: zisti
			/*03-20 12:15:34.055 21368-21399/sk.feri.awesomegame.android I/XXXX: com.badlogic.gdx.utils.GdxRuntimeException: Error reading file: .awesomegame (External)
			03-20 12:15:47.540 21368-21399/sk.feri.awesomegame.android I/XXXXZ: com.badlogic.gdx.utils.GdxRuntimeException: Error writing file: .awesomegame (External)
			*/
			Gdx.app.log("XXXX", ""+e);
			// Defaults applied
		}
	}

	public static void save () {
		try {
			FileHandle filehandle = Gdx.files.external(file);
			
			filehandle.writeString(Boolean.toString(soundEnabled)+"\n", false);
			for (int i = 0; i < 5; i++) {
				filehandle.writeString(Integer.toString(highscores[i])+"\n", true);
			}
		} catch (Throwable e) {
			Gdx.app.log("XXXXZ", ""+e);
		}
	}

	public static void addScore (int score) {
		for (int i = 0; i < 5; i++) {
			if (highscores[i] < score) {
				for (int j = 4; j > i; j--)
					highscores[j] = highscores[j - 1];
				highscores[i] = score;
				break;
			}
		}
	}
}
