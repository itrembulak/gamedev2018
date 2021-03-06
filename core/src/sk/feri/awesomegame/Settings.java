package sk.feri.awesomegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class Settings {
	public static boolean soundEnabled = true;
	public static int[][] highscores = {
			new int[] {1, 1, 1, 1, 1},
			new int[] {2, 2, 2, 2, 2},
			new int[] {3, 3, 3, 3, 3}
	};
	public static int attempts = 0;
	public static int shots = 0;
	public static int distance = 0;
	public final static String file = ".awesomegame";

	public static int DIFFICULTY_LOW = 1;
	public static int DIFFICULTY_MEDIUM = 2;
	public static int DIFFICULTY_HEIGH = 3;
	public static int difficulty = DIFFICULTY_LOW;



	public static void load () {
		try {
			FileHandle filehandle = Gdx.files.external(file);
			
			String[] strings = filehandle.readString().split("\n");
			
			soundEnabled = Boolean.parseBoolean(strings[0]);
			attempts = Integer.parseInt(strings[1]);
			shots = Integer.parseInt(strings[2]);
			distance = Integer.parseInt(strings[3]);
			difficulty = Integer.parseInt(strings[4]);

			for (int i = 0; i < 5; i++) {
				highscores[0][i] = Integer.parseInt(strings[i+5]);
			}
			for (int i = 0; i < 5; i++) {
				highscores[1][i] = Integer.parseInt(strings[i+10]);
			}
			for (int i = 0; i < 5; i++) {
				highscores[2][i] = Integer.parseInt(strings[i+15]);
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
			filehandle.writeString(Integer.toString(difficulty)+"\n", true);

			for (int i = 0; i < 5; i++) {
				filehandle.writeString(Integer.toString(highscores[0][i])+"\n", true);
			}
			for (int i = 0; i < 5; i++) {
				filehandle.writeString(Integer.toString(highscores[1][i])+"\n", true);
			}
			for (int i = 0; i < 5; i++) {
				filehandle.writeString(Integer.toString(highscores[2][i])+"\n", true);
			}
		} catch (Throwable e) {
			Gdx.app.log("SETTINGS", ""+e);
		}
	}

	public static void addScore (int score) {
		for (int i = 0; i < 5; i++) {
			if (highscores[difficulty -1][i] < score) {
				for (int j = 4; j > i; j--)
					highscores[difficulty -1][j] = highscores[difficulty -1][j - 1];
				highscores[difficulty -1][i] = score;
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
		distance += increment;
	}
	public static void setDifficulty(int newDifficulty){
		difficulty = newDifficulty;
	}
}
