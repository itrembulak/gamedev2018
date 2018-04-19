package sk.feri.awesomegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class Settings {
	public static boolean soundEnabled = true;
	public static int[][] highscores = {
			new int[] {0, 0, 0, 0, 0},
			new int[] {0, 0, 0, 0, 0},
			new int[] {0, 0, 0, 0, 0}
	};
	public static int attempts = 0;
	public static int shots = 0;
	public static int distance = 0;
	public final static String file = ".awesomegame";
	public static int maxHeight[] = {0, 0, 0};
	public static String username = "Guy"+ (int)(Math.random() * 9999);

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
			maxHeight[0] = Integer.parseInt(strings[5]);
			maxHeight[1] = Integer.parseInt(strings[6]);
			maxHeight[2] = Integer.parseInt(strings[7]);
			username = strings[8];


			for (int i = 0; i < 5; i++) {
				highscores[0][i] = Integer.parseInt(strings[i+9]);
			}
			for (int i = 0; i < 5; i++) {
				highscores[1][i] = Integer.parseInt(strings[i+14]);
			}
			for (int i = 0; i < 5; i++) {
				highscores[2][i] = Integer.parseInt(strings[i+19]);
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
			filehandle.writeString(Integer.toString(maxHeight[0])+"\n", true);
			filehandle.writeString(Integer.toString(maxHeight[1])+"\n", true);
			filehandle.writeString(Integer.toString(maxHeight[2])+"\n", true);
			filehandle.writeString(username+"\n", true);

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
	public static void setMaxDistance(int height){
		if (maxHeight[difficulty - 1] < height)
			maxHeight[difficulty - 1] = height;
	}
	public static int getMaxDistance(){ return maxHeight[difficulty - 1]; }
	public static void setUsername(String newUsername){
		username = newUsername;
	}

	public static void resetSettings(){
		highscores[0][0] = highscores[0][1] = highscores[0][2] = highscores[0][3] = highscores[0][4] = 0;
		highscores[1][0] = highscores[1][1] = highscores[1][2] = highscores[1][3] = highscores[1][4] = 0;
		highscores[2][0] = highscores[2][1] = highscores[2][2] = highscores[2][3] = highscores[2][4] = 0;
		attempts = 0;
		shots = 0;
		distance = 0;

		maxHeight[0] = maxHeight[1] = maxHeight[2] = 0;
		username = "Guy"+ (int)(Math.random() * 9999);
	}
}
