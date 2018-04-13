package sk.feri.awesomegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public class MainMenuScreen extends ScreenAdapter {
	AwesomeGame game;
	OrthographicCamera guiCam;
	Rectangle soundBounds, lowBounds, mediumBounds, heighBounds;
	Rectangle playBounds;
	Rectangle highscoresBounds;
	Rectangle helpBounds;
	Vector3 touchPoint;
	String difficultyString;

	public MainMenuScreen (AwesomeGame game) {
		this.game = game;

		guiCam = new OrthographicCamera(320, 480);
		guiCam.position.set(320 / 2, 480 / 2, 0);
		soundBounds = new Rectangle(0, 0, 64, 64);
		lowBounds = new Rectangle(200, 10, 30, 30);
		mediumBounds = new Rectangle(240, 10, 30, 30);
		heighBounds = new Rectangle(280, 10, 30, 30);
		playBounds = new Rectangle(160 - 150, 200 + 18, 300, 36);
		highscoresBounds = new Rectangle(160 - 150, 200 - 18, 300, 36);

		// TODO: Help tlacitko neklikaj :D
		//helpBounds = new Rectangle(160 - 150, 200 - 18 - 36, 300, 36);

		helpBounds = new Rectangle();
		touchPoint = new Vector3();
		difficultyString = "DIFFICULTY: " + Settings.difficulty;

		HighScores.loadHighScores("score",5 , Settings.difficulty);
		HighScores.updateScore();
	}

	public void update () {
		if (Gdx.input.justTouched()) {
			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

			if (playBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				game.setScreen(new GameScreen(game));
				return;
			}
			if (highscoresBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				game.setScreen(new HighscoresScreen(game));
				return;
			}
			if (helpBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				game.setScreen(new HelpScreen(game));
				return;
			}
			if (soundBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				Settings.soundEnabled = !Settings.soundEnabled;
				if (Settings.soundEnabled)
					Assets.music.play();
				else
					Assets.music.pause();
			}
			if (lowBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				Settings.setDifficulty(1);
				Settings.save();
				difficultyString = "DIFFICULTY: " + Settings.difficulty;
			}
			if (mediumBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				Settings.setDifficulty(2);
				Settings.save();
				difficultyString = "DIFFICULTY: " + Settings.difficulty;
			}
			if (heighBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				Settings.setDifficulty(3);
				Settings.save();
				difficultyString = "DIFFICULTY: " + Settings.difficulty;
			}
		}
	}

	public void draw () {
		GL20 gl = Gdx.gl;
		gl.glClearColor(1, 0, 0, 1);
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		guiCam.update();
		game.batcher.setProjectionMatrix(guiCam.combined);

		game.batcher.disableBlending();
		game.batcher.begin();
		game.batcher.draw(Assets.backgroundRegion, 0, 0, 320, 480);
		game.batcher.end();

		game.batcher.enableBlending();
		game.batcher.begin();
		game.batcher.draw(Assets.logo, 160 - 274 / 2, 480 - 10 - 142, 274, 142);
		game.batcher.draw(Assets.mainMenu, 10, 200 - 110 / 2, 300, 110);
		game.batcher.draw(Settings.soundEnabled ? Assets.soundOn : Assets.soundOff, 0, 0, 64, 64);

		Assets.font.draw(game.batcher, difficultyString, 90, 60);
		game.batcher.draw(Assets.SquareBlockG, 200, 10, 30, 30);
		game.batcher.draw(Assets.SquareBlockY, 240, 10, 30, 30);
		game.batcher.draw(Assets.SquareBlockR, 280, 10, 30, 30);
		game.batcher.draw(Assets.number1, 205, 15, 20, 20);
		game.batcher.draw(Assets.number2, 245, 15, 20, 20);
		game.batcher.draw(Assets.number3, 283, 15, 20, 20);

		game.batcher.end();	
	}

	@Override
	public void render (float delta) {
		update();
		draw();
	}

	@Override
	public void pause () {
		Settings.save();
		HighScores.updateScore();
	}
}
