package sk.feri.awesomegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class HighscoresScreen extends ScreenAdapter {
	AwesomeGame game;
	OrthographicCamera guiCam;
	Rectangle backBounds;
	Vector3 touchPoint;
	String[] highScores;
	float xOffset = 0;
	GlyphLayout glyphLayout = new GlyphLayout();
	String attemptsString, shotsString, distanceString, maxDistanceString, difficultyString, highscoreTypeString, orderString;
	Rectangle internalScoreBounds, externalScoreBounds, scoreOrderBounds, maxDistanceBounds, distanceBounds, gamesPlayedBounds;
	boolean drawExternalScore, drawScoreButtons;
	String scoreOrderBy;

	public HighscoresScreen (AwesomeGame game) {
		this.game = game;

		guiCam = new OrthographicCamera(320, 480);
		guiCam.position.set(320 / 2, 480 / 2, 0);
		backBounds = new Rectangle(0, 0, 64, 64);
		touchPoint = new Vector3();
		highScores = new String[5];
		for (int i = 0; i < 5; i++) {
			highScores[i] = i + 1 + ". " + Settings.highscores[Settings.difficulty -1 ][i];
			glyphLayout.setText(Assets.font, highScores[i]);
			xOffset = Math.max(glyphLayout.width, xOffset);
		}
		xOffset = 160 - xOffset / 2 + Assets.font.getSpaceWidth() / 2;

		attemptsString = "GAMES PLAYED: " + Settings.attempts;
		shotsString = "NO. SHOTS: " + Settings.shots;
		distanceString = "DIST.: " + Settings.distance + "m";
		maxDistanceString = "MAX DIST.: " + Settings.getMaxDistance() + "m";
		difficultyString = "DIFFICULTY: " + Settings.difficulty;
		highscoreTypeString = "YOUR";


		internalScoreBounds = new Rectangle(240, 50, 30, 30);
		externalScoreBounds = new Rectangle(280, 50, 30, 30);

		scoreOrderBounds = new Rectangle(160, 10, 30, 30);
		maxDistanceBounds = new Rectangle(200, 10, 30, 30);
		distanceBounds = new Rectangle(240, 10, 30, 30);
		gamesPlayedBounds = new Rectangle(280, 10, 30, 30);

		drawExternalScore = false;
		drawScoreButtons = false;
		scoreOrderBy = "score";
		orderString = "BEST SCORE";
		HighScores.loadHighScores(scoreOrderBy, 5, Settings.difficulty);
	}

	public void update () {
		if (Gdx.input.justTouched()) {
			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

			if (backBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				game.setScreen(new MainMenuScreen(game));
				return;
			}
			if (internalScoreBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				drawExternalScore = false;
			}
			if (externalScoreBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				drawExternalScore = true;
			}
			if (scoreOrderBounds.contains(touchPoint.x, touchPoint.y)) { scoreOrderBy = "score"; orderString = "BEST SCORE"; HighScores.loadHighScores(scoreOrderBy, 5, Settings.difficulty); }
			if (maxDistanceBounds.contains(touchPoint.x, touchPoint.y)) { scoreOrderBy = "max_distance"; orderString = "MAX. DISTANCE"; HighScores.loadHighScores(scoreOrderBy, 5, Settings.difficulty); }
			if (distanceBounds.contains(touchPoint.x, touchPoint.y)) { scoreOrderBy = "distance"; orderString = "DISTANCE TOTAL"; HighScores.loadHighScores(scoreOrderBy, 5, Settings.difficulty); }
			if (gamesPlayedBounds.contains(touchPoint.x, touchPoint.y)) { scoreOrderBy = "games_played"; orderString = "MAX. GAMES"; HighScores.loadHighScores(scoreOrderBy, 5, Settings.difficulty); }
		}
		if (HighScores.scoresList.size() > 0){
			drawScoreButtons = true;
		}else {
			drawScoreButtons = false;
		}
		if (drawExternalScore){
			highscoreTypeString = "WORLD";
		}else{
			highscoreTypeString = "YOUR";
		}
	}

	public void draw () {
		GL20 gl = Gdx.gl;
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		guiCam.update();

		game.batcher.setProjectionMatrix(guiCam.combined);
		game.batcher.disableBlending();
		game.batcher.begin();
		game.batcher.draw(Assets.backgroundRegion, 0, 0, 320, 480);
		game.batcher.end();

		game.batcher.enableBlending();
		game.batcher.begin();

		Assets.font.draw(game.batcher, highscoreTypeString, xOffset, 360 + 32);
		game.batcher.draw(Assets.highScoresRegion, 10, 360 - 16, 300, 33);

		drawScores();

		game.batcher.draw(Assets.arrow, 0, 0, 64, 64);

		if (drawScoreButtons) {
			//order by buttons
			game.batcher.draw(Assets.SquareBlockG, 160, 10, 30, 30);
			game.batcher.draw(Assets.SquareBlockG, 200, 10, 30, 30);
			game.batcher.draw(Assets.SquareBlockY, 240, 10, 30, 30);
			game.batcher.draw(Assets.SquareBlockR, 280, 10, 30, 30);
			//score buttons
			game.batcher.draw(Assets.SquareBlockG, 240, 50, 30, 30);
			game.batcher.draw(Assets.SquareBlockR, 280, 50, 30, 30);
		}
		game.batcher.end();
	}

	private void drawScores() {
		if (drawExternalScore){
			//Order by string
			Assets.font.draw(game.batcher, orderString, 10, 335);
			float y = 200;
			String text;
			try {
				for (int i = HighScores.scoresList.size() - 1; i >= 0; i--) {
					text = i + 1 + ". " + HighScores.scoresList.get(i).getUsername();
					if (scoreOrderBy.equals("score"))
						text += ": " + HighScores.scoresList.get(i).getScore();
					if (scoreOrderBy.equals("max_distance"))
						text += ": " + HighScores.scoresList.get(i).getMaxDistance() + "m";
					if (scoreOrderBy.equals("distance"))
						text += ": " + HighScores.scoresList.get(i).getDistance() + "m";
					if (scoreOrderBy.equals("games_played"))
						text += ": " + HighScores.scoresList.get(i).getGamesPlayed();

					Assets.font.draw(game.batcher, text, 10, y);
					y += Assets.font.getLineHeight();
				}
			}catch (Exception e){
				// Prevention from crash when data not loaded yet
			}
			Assets.font.draw(game.batcher, "...", 10, 180);
			Assets.font.draw(game.batcher, HighScores.playerRank + ". YOU: " + HighScores.playerValue, 10, 160);
		}else {
			float y = 230;
			for (int i = 4; i >= 0; i--) {
				Assets.font.draw(game.batcher, highScores[i], xOffset, y);
				y += Assets.font.getLineHeight();
			}
			Assets.font.draw(game.batcher, attemptsString, 10, 170);
			Assets.font.draw(game.batcher, shotsString, 10, 150);
			Assets.font.draw(game.batcher, distanceString, 10, 130);
			Assets.font.draw(game.batcher, maxDistanceString, 10, 110);
			Assets.font.draw(game.batcher, difficultyString, 10, 90);
		}
	}

	@Override
	public void render (float delta) {
		update();
		draw();
	}
}
