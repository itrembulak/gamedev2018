package sk.feri.awesomegame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class World {
	public interface WorldListener {
		public void noAmmo();

		public void hit();

		public void coin();

		public void shoot();

		public void supply();
	}

	public static final float WORLD_WIDTH = 10;
	public static final float WORLD_HEIGHT = 15 * 20;
	public static final int WORLD_STATE_RUNNING = 0;
	public static final int WORLD_STATE_NEXT_LEVEL = 1;
	public static final int WORLD_STATE_GAME_OVER = 2;
	public static final Vector2 gravity = new Vector2(0, -12);

	public final Player player;
	public final List<Enemy> enemies;
	public final List<Projectile> projectiles;
	public final List<Coin> coins;
    public final List<Supply> supplies;
	public final List<Block> blocks;
	public final List<SquareBlock> squareBlocks;
	public Castle castle;
	public final WorldListener listener;
	public final Random rand;

	public float heightSoFar;
	public int score;
	public int state;

	public World (WorldListener listener) {
		this.player = new Player(5, 1);
		this.enemies = new ArrayList<Enemy>();
		this.projectiles = new ArrayList<Projectile>();
		this.coins = new ArrayList<Coin>();
        this.supplies = new ArrayList<Supply>();
		this.blocks = new ArrayList<Block>();
		this.squareBlocks = new ArrayList<SquareBlock>();
		this.listener = listener;
		rand = new Random();
		generateLevel();

		this.heightSoFar = 0;
		this.score = 0;
		this.state = WORLD_STATE_RUNNING;
	}

	private void generateLevel () {
		float y = Block.BLOCK_HEIGHT / 2;
		float minBlockSpacing = 5f;
		while (y < WORLD_HEIGHT - WORLD_WIDTH / 2) {

			int type = rand.nextFloat() > 0.8f ? SquareBlock.BLOCK_TYPE_MOVING : SquareBlock.BLOCK_TYPE_STATIC;
			float x = rand.nextFloat() * (WORLD_WIDTH - SquareBlock.BLOCK_WIDTH) + SquareBlock.BLOCK_WIDTH / 2;

			float variant = rand.nextFloat();
			SquareBlock block = new SquareBlock(type, x, y + 20, GenerateLives());
			if(variant<0.8f) {
				squareBlocks.add(block);

			}
			else {
				GenerateRow(y);
			}



			if (y > WORLD_HEIGHT / 3 && rand.nextFloat() > 0.8f) {
				Enemy enemy = new Enemy(block.position.x + rand.nextFloat(), block.position.y
					+ Enemy.ENEMY_HEIGHT + rand.nextFloat() * 2);
				enemies.add(enemy);
			}

			if (rand.nextFloat() > 0.6f) {
				Coin coin = new Coin(block.position.x + rand.nextFloat(), block.position.y + Coin.COIN_HEIGHT
					+ rand.nextFloat() * 3);
				coins.add(coin);
			}

            if (rand.nextFloat() > 0.6f) {
                Coin coin = new Coin(block.position.x + rand.nextFloat(), block.position.y + Coin.COIN_HEIGHT
                        + rand.nextFloat() * 3);
                coins.add(coin);
            }

            if (rand.nextFloat() > 0.1f) {
                Supply supply = new Supply(block.position.x + rand.nextFloat(), block.position.y + Supply.SUPPLY_HEIGHT
                        + rand.nextFloat() * 3, 3);
                supplies.add(supply);
            }

			y += (minBlockSpacing - 0.5f);
			y -= rand.nextFloat() * (minBlockSpacing / 3);
		}

		castle = new Castle(WORLD_WIDTH / 2, y);
	}

	public void update (float deltaTime, float accelX) {
		updatePlayer(deltaTime, accelX);
		updateEnemies(deltaTime);
		updateCoins(deltaTime);
		updateProjectiles(deltaTime);
        updateSupplies(deltaTime);
		updateBlocks(deltaTime);
		updateSquareBlocks(deltaTime);
		if (player.state != Player.PLAYER_STATE_HIT) checkCollisions();
		checkGameOver();
	}


	private void updatePlayer(float deltaTime, float accelX) {
		if (player.state != Player.PLAYER_STATE_HIT) player.velocity.x = -accelX / 10 * Player.PLAYER_MOVE_VELOCITY;
		player.update(deltaTime);
		heightSoFar = Math.max(player.position.y, heightSoFar);
	}


	private void updateBlocks (float deltaTime) {
		int len = blocks.size();
		for (int i = 0; i < len; i++) {
			Block block = blocks.get(i);
			block.update(deltaTime);
			if (block.state == Block.BLOCK_STATE_PULVERIZING && block.stateTime > Block.BLOCK_STATE_PULVERIZING) {
				blocks.remove(block);
				len = blocks.size();
			}
		}
	}

	private void updateSquareBlocks (float deltaTime) {
		int len = squareBlocks.size();
		for (int i = 0; i < len; i++) {
			SquareBlock block = squareBlocks.get(i);
			block.update(deltaTime);
			if (block.state == SquareBlock.BLOCK_STATE_PULVERIZING && block.stateTime > SquareBlock.BLOCK_STATE_PULVERIZING) {
				squareBlocks.remove(block);
				len = squareBlocks.size();
			}
		}
	}

	private void updateEnemies (float deltaTime) {
		int len = enemies.size();
		for (int i = 0; i < len; i++) {
			Enemy enemy = enemies.get(i);
			enemy.update(deltaTime);
		}
	}

	private void updateProjectiles(float deltaTime) {
		if (Gdx.input.justTouched()) {
			if (player.getProjectile_count() != 0) {
				listener.shoot();
				Projectile projectile = new Projectile(player.position.x, player.position.y);
				projectiles.add(projectile);
				player.projectileShot();
			}else {
				listener.noAmmo();
			}
		}
		int len = projectiles.size();
		for (int i = 0; i < len; i++) {
			Projectile projectile = projectiles.get(i);
			projectile.update(deltaTime);
			if (projectile.state == Projectile.PROJECTILE_STATE_DESTROY && projectile.stateTime > Projectile.PROJECTILE_TIME_DESTROY) {
				projectiles.remove(projectile);
				len = projectiles.size();
			}
		}
	}

	private void updateCoins (float deltaTime) {
		int len = coins.size();
		for (int i = 0; i < len; i++) {
			Coin coin = coins.get(i);
			coin.update(deltaTime);
		}
	}
    private void updateSupplies (float deltaTime) {
        int len = supplies.size();
        for (int i = 0; i < len; i++) {
            Supply supply = supplies.get(i);
            supply.update(deltaTime);
        }
    }


    private void checkCollisions () {
		checkEnemiesCollisions();
		checkItemCollisions();
		checkCastleCollisions();
		checkBlockCollisions();
		checkSquareBlockCollisions();
	}


	private void checkBlockCollisions () {
		int len = blocks.size();
		int len_proj = projectiles.size();
		for (int i = 0; i < len; i++) {
			Block block = blocks.get(i);
			if (block.bounds.overlaps(player.bounds) && block.state != Block.BLOCK_STATE_PULVERIZING) {
				player.hitBlock();
			}
			for (int j = 0; j < len_proj; j++) {
				Projectile projectile = projectiles.get(j);
				if (block.bounds.overlaps(projectile.bounds) && block.state != Block.BLOCK_STATE_PULVERIZING && projectile.state != Projectile.PROJECTILE_STATE_DESTROY) {
					listener.hit();
					block.hitProjectile();
					projectile.hitBox();
				}
			}
		}
	}

	private void checkSquareBlockCollisions () {
		int len = squareBlocks.size();
		int len_proj = projectiles.size();
		for (int i = 0; i < len; i++) {
			SquareBlock block = squareBlocks.get(i);
			if (block.bounds.overlaps(player.bounds) && block.state != SquareBlock.BLOCK_STATE_PULVERIZING) {
				player.hitBlock();
			}
			for (int j = 0; j < len_proj; j++) {
				Projectile projectile = projectiles.get(j);
				if (block.bounds.overlaps(projectile.bounds) && block.state != SquareBlock.BLOCK_STATE_PULVERIZING && projectile.state != Projectile.PROJECTILE_STATE_DESTROY) {
					listener.hit();
					block.hitProjectile();
					projectile.hitBox();
				}
			}
		}
	}

	private void checkEnemiesCollisions() {
		int len = enemies.size();
		for (int i = 0; i < len; i++) {
			Enemy enemy = enemies.get(i);
			if (enemy.bounds.overlaps(player.bounds)) {
				player.hitEnemy();
				listener.hit();
			}
		}
	}

	private void checkItemCollisions () {
		int len = coins.size();
		for (int i = 0; i < len; i++) {
			Coin coin = coins.get(i);
			if (player.bounds.overlaps(coin.bounds)) {
				coins.remove(coin);
				len = coins.size();
				listener.coin();
				score += Coin.COIN_SCORE;
			}

		}

		int len_s = supplies.size();
		for (int i = 0; i < len_s; i++) {
			Supply supply = supplies.get(i);
			if (player.bounds.overlaps(supply.bounds)) {
				supplies.remove(supply);
				len_s = supplies.size();
				listener.supply();
				player.hitSupply(supply.SUPPLY_QUANTITY);
			}
		}

	}

	private void checkCastleCollisions () {
		if (castle.bounds.overlaps(player.bounds)) {
			state = WORLD_STATE_NEXT_LEVEL;
		}
	}

	private void checkGameOver () {
		if (heightSoFar - 7.5f > player.position.y) {
			state = WORLD_STATE_GAME_OVER;
		}
	}

	private  void GenerateRow(float y){

		for (float i=0.5f;i<WORLD_WIDTH;i=i+ SquareBlock.BLOCK_WIDTH + 0.4f) {
			SquareBlock block = new SquareBlock(SquareBlock.BLOCK_TYPE_STATIC, i, y + 20, GenerateLives());
			squareBlocks.add(block);
		}

	}
	private int GenerateLives(){

		int squareBlockLives;
		float random = rand.nextFloat();
		if(random>0.75f){
			squareBlockLives = 3;
		}
		else if(random>0.4f && random<0.75f){
			squareBlockLives = 2;
		}
		else
			squareBlockLives = 1;

		return squareBlockLives;
	}
}
