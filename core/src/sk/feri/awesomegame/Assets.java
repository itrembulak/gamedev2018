package sk.feri.awesomegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class Assets {
	public static Texture background;
	public static TextureRegion backgroundRegion;

	public static Texture items;
	public static TextureRegion mainMenu;
	public static TextureRegion pauseMenu;
	public static TextureRegion ready;
	public static TextureRegion gameOver;
	public static TextureRegion highScoresRegion;
	public static TextureRegion soundOn;
	public static TextureRegion soundOff;
	public static TextureRegion arrow;
	public static TextureRegion pause;
	public static TextureRegion supply;
	public static TextureRegion number1;
	public static TextureRegion number2;
	public static TextureRegion number3;
	public static Animation coinAnim;


	public static Animation enemyFly;
	public static TextureRegion block;

	public static Animation brakingBlock;
	public static BitmapFont font;

	public static Texture textures;
	public static TextureRegion SquareBlockR;
	public static TextureRegion SquareBlockG;
	public static TextureRegion SquareBlockY;
	public static TextureRegion ButtonYou;
	public static TextureRegion ButtonWorld;
	public static TextureRegion ButtonScore;
	public static TextureRegion ButtonMaxDistance;
	public static TextureRegion ButtonTotalDistance;
	public static TextureRegion ButtonGames;
	public static TextureRegion ButtonName;
	public static Animation playerNormal;
	public static TextureRegion logo;
	public static Animation projectileAnim;
	public static TextureRegion playerHit;
	public static Animation breakingSquareBlockR;
	public static Animation breakingSquareBlockY;
	public static Animation breakingSquareBlockG;
	//public static TextureRegion playerNormal;

	public static Music music;
	public static Sound highJumpSound;
	public static Sound hitSound;
	public static Sound coinSound;
	public static Sound supplySound;
	public static Sound projectileSound;
	public static Sound clickSound;
	public static Sound noAmmoSound;
	public static Sound gameOverSound;
	public static Sound pulverizeSound;

	public static Texture loadTexture (String file) {
		return new Texture(Gdx.files.internal(file));
	}

	public static void load () {
		background = loadTexture("data/backgroundH.png");
		backgroundRegion = new TextureRegion(background, 0, 0, 320, 480);

		items = loadTexture("data/items.png");
		mainMenu = new TextureRegion(items, 0, 224, 300, 110);
		pauseMenu = new TextureRegion(items, 224, 128, 192, 96);
		ready = new TextureRegion(items, 320, 224, 192, 32);
		gameOver = new TextureRegion(items, 352, 256, 160, 96);
		highScoresRegion = new TextureRegion(Assets.items, 0, 257, 300, 110 / 3);

		soundOff = new TextureRegion(items, 0, 0, 64, 64);
		soundOn = new TextureRegion(items, 64, 0, 64, 64);
		arrow = new TextureRegion(items, 0, 64, 64, 64);
		pause = new TextureRegion(items, 64, 64, 64, 64);


		coinAnim = new Animation(0.2f, new TextureRegion(items, 128, 32, 32, 32), new TextureRegion(items, 160, 32, 32, 32),
			new TextureRegion(items, 192, 32, 32, 32), new TextureRegion(items, 160, 32, 32, 32));

		//playerNormal = new Animation(0.2f, new TextureRegion(items, 64, 128, 32, 32), new TextureRegion(items, 96, 128, 32, 32));

		enemyFly = new Animation(0.2f, new TextureRegion(items, 0, 160, 32, 32), new TextureRegion(items, 32, 160, 32, 32));

		block = new TextureRegion(items, 64, 160, 64, 16);
		brakingBlock = new Animation(0.2f, new TextureRegion(items, 64, 160, 64, 16), new TextureRegion(items, 64, 176, 64, 16),
				new TextureRegion(items, 64, 192, 64, 16), new TextureRegion(items, 64, 208, 64, 16));

		//hraniciar = loadTexture("data/hraniciar.png");
		textures = loadTexture("data/textures.png");
		SquareBlockG = new TextureRegion(textures, 0, 103, 32, 32);
		SquareBlockY = new TextureRegion(textures, 0, 135, 32, 32);
		SquareBlockR = new TextureRegion(textures, 0, 167, 32, 32);
		ButtonWorld = new TextureRegion(textures, 35, 274, 35, 35);
		ButtonYou= new TextureRegion(textures, 0, 274, 35, 35);
		ButtonMaxDistance= new TextureRegion(textures, 105, 274, 35, 35);
		ButtonTotalDistance = new TextureRegion(textures, 140, 274, 35, 35);
		ButtonGames = new TextureRegion(textures, 175, 274, 35, 35);
		ButtonScore = new TextureRegion(textures, 70, 274, 35, 35);
		ButtonName = new TextureRegion(textures, 0,309 , 32, 32);
		playerNormal = new Animation(0.2f, new TextureRegion(textures, 0, 203, 62, 62), new TextureRegion(textures, 62, 203, 62, 62), new TextureRegion(textures, 124, 203, 62, 62));
		logo = new TextureRegion(textures, 0, 352, 274, 142);
		projectileAnim  = new Animation(0.2f, new TextureRegion(textures, 0, 34, 5, 11), new TextureRegion(textures, 5, 34, 5, 11), new TextureRegion(textures, 10, 34, 5, 11),
										new TextureRegion(textures, 15, 34, 5, 11), new TextureRegion(textures, 20, 34, 5, 11));
		supply = new TextureRegion(textures, 128, 0, 32, 32);
		playerHit = new TextureRegion(textures, 186, 203, 62, 62);
		breakingSquareBlockG  = new Animation(0.2f, new TextureRegion(textures, 32, 103, 32, 32),new TextureRegion(textures, 64, 103, 32, 32),new TextureRegion(textures, 96, 103, 32, 32));
		breakingSquareBlockY  = new Animation(0.2f, new TextureRegion(textures, 32, 135, 32, 32),new TextureRegion(textures, 64, 135, 32, 32),new TextureRegion(textures, 96, 135, 32, 32));
		breakingSquareBlockR  = new Animation(0.2f, new TextureRegion(textures, 32, 167, 32, 32),new TextureRegion(textures, 64, 167, 32, 32),new TextureRegion(textures, 96, 167, 32, 32));
		number1 = new TextureRegion(textures, 0, 51, 16, 22);
		number2 = new TextureRegion(textures, 16, 51, 16, 22);
		number3 = new TextureRegion(textures, 32, 51, 16, 22);

		font = new BitmapFont(Gdx.files.internal("data/font.fnt"), Gdx.files.internal("data/font.png"), false);

		music = Gdx.audio.newMusic(Gdx.files.internal("data/music.mp3"));
		music.setLooping(true);
		music.setVolume(0.4f);
		if (Settings.soundEnabled) music.play();
		highJumpSound = Gdx.audio.newSound(Gdx.files.internal("data/highjump.wav"));
		hitSound = Gdx.audio.newSound(Gdx.files.internal("data/hit.wav"));
		coinSound = Gdx.audio.newSound(Gdx.files.internal("data/coin.wav"));
		supplySound = Gdx.audio.newSound(Gdx.files.internal("data/highjump.wav"));
		projectileSound = Gdx.audio.newSound(Gdx.files.internal("data/pow.mp3"));
		clickSound = Gdx.audio.newSound(Gdx.files.internal("data/click.wav"));
		noAmmoSound = Gdx.audio.newSound(Gdx.files.internal("data/no-ammo.wav"));
		gameOverSound = Gdx.audio.newSound(Gdx.files.internal("data/gameover.wav"));
		pulverizeSound = Gdx.audio.newSound(Gdx.files.internal("data/pulverize.wav"));
	}

	public static void playSound (Sound sound) {
		if (Settings.soundEnabled) sound.play(1);
	}
}
