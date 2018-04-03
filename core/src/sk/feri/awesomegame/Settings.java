package sk.feri.awesomegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class Settings {
	public static boolean soundEnabled = true;
	public static int[] highscores = new int[] {100, 80, 50, 30, 10};
	public static int attempts = 0;
	public static int shots = 0;
	public static int distance = 0;
	public final static String file = ".awesomegame";

	public static void load () {
		try {
			FileHandle filehandle = Gdx.files.external(file);
			
			String[] strings = filehandle.readString().split("\n");
			
			soundEnabled = Boolean.parseBoolean(strings[0]);
			attempts = Integer.parseInt(strings[1]);
			shots = Integer.parseInt(strings[2]);
			distance = Integer.parseInt(strings[3]);

			for (int i = 0; i < 5; i++) {
				highscores[i] = Integer.parseInt(strings[i+4]);
			}
		} catch (Throwable e) {
			Gdx.app.log("SETTINGS", ""+e);
		}
	}

	public static void save () {
		try {
			FileHandle filehandle = Gdx.files.external(file);
			
			filehandle.writeString(Boolean.toString(soundEnabled)+"\n", false);
			filehandle.writeString(Integer.toString(attempts)+"\n", true);
			filehandle.writeString(Integer.toString(shots)+"\n", true);
			filehandle.writeString(Integer.toString(distance)+"\n", true);

			for (int i = 0; i < 5; i++) {
				filehandle.writeString(Integer.toString(highscores[i])+"\n", true);
			}
		} catch (Throwable e) {
			Gdx.app.log("SETTINGS", ""+e);
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

	public static void addAtenpt(){
		attempts++;
	}
	public static void addShot(){
		shots++;
	}
	public static void addDistance(int increment){
		distance = increment;
	}
}
