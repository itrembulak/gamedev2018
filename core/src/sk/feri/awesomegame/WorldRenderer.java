package sk.feri.awesomegame;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class WorldRenderer {
	static final float FRUSTUM_WIDTH = 10;
	static final float FRUSTUM_HEIGHT = 15;
	World world;
	OrthographicCamera cam;
	SpriteBatch batch;

	public WorldRenderer (SpriteBatch batch, World world) {
		this.world = world;
		this.cam = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
		this.cam.position.set(FRUSTUM_WIDTH / 2, FRUSTUM_HEIGHT / 2, 0);
		this.batch = batch;
	}

	public void render () {
		cam.position.y = world.player.position.y + 6.5f;
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		renderBackground();
		renderObjects();
	}

	public void renderBackground () {
		batch.disableBlending();
		batch.begin();
		batch.draw(Assets.backgroundRegion, cam.position.x - FRUSTUM_WIDTH / 2, cam.position.y - FRUSTUM_HEIGHT / 2, FRUSTUM_WIDTH,
			FRUSTUM_HEIGHT);
		batch.end();
	}

	public void renderObjects () {
		batch.enableBlending();
		batch.begin();
		renderPlayer();
		renderItems();
		renderEnemies();
		//renderCastle();
		renderProjectiles();
		renderBlocks();
		renderSquareBlocks();
		batch.end();
	}

	private void renderPlayer () {
		TextureRegion keyFrame;
		switch (world.player.state) {
		case Player.PLAYER_STATE_NORMAL:
			//keyFrame = Assets.playerNormal;
			keyFrame = Assets.playerNormal.getKeyFrame(world.player.stateTime, Animation.ANIMATION_LOOPING);
			break;
		case Player.PLAYER_STATE_HIT:
			world.state = World.WORLD_STATE_GAME_OVER;
		default:
			keyFrame = Assets.playerHit;
		}

		float side = world.player.velocity.x < 0 ? -1 : 1;
		if (side < 0)
			batch.draw(keyFrame, world.player.position.x + 0.5f, world.player.position.y - 0.5f, side * 1, 1);
		else
			batch.draw(keyFrame, world.player.position.x - 0.5f, world.player.position.y - 0.5f, side * 1, 1);

	}

	private void renderBlocks() {
		int len = world.blocks.size();
		for (int i = 0; i < len; i++) {
			Block block = world.blocks.get(i);
			TextureRegion keyFrame = Assets.block;
			if (block.state == Block.BLOCK_STATE_PULVERIZING) {
				keyFrame = Assets.brakingBlock.getKeyFrame(block.stateTime, Animation.ANIMATION_NONLOOPING);
			}

			batch.draw(keyFrame, block.position.x - 1, block.position.y - 0.25f, 2, 0.5f);
		}
	}
	private void renderSquareBlocks() {
		int len = world.squareBlocks.size();
		for (int i = 0; i < len; i++) {
			SquareBlock block = world.squareBlocks.get(i);
			TextureRegion keyFrame;
			if(block.lives==3){
				keyFrame = Assets.SquareBlockR;
			}
			else
			if(block.lives==2){
				keyFrame = Assets.SquareBlockB;
			}
			else
			{
				keyFrame = Assets.SquareBlockG;
			}
			if (block.state == SquareBlock.BLOCK_STATE_PULVERIZING) {
				keyFrame = Assets.breakingSquareBlock.getKeyFrame(block.stateTime, Animation.ANIMATION_NONLOOPING);
			}

			batch.draw(keyFrame, block.position.x - 0.7f, block.position.y - 0.7f, 1.4f, 1.4f);
		}
	}

	private void renderItems () {
		int len = world.coins.size();
		for (int i = 0; i < len; i++) {
			Coin coin = world.coins.get(i);
			TextureRegion keyFrame = Assets.coinAnim.getKeyFrame(coin.stateTime, Animation.ANIMATION_LOOPING);
			batch.draw(keyFrame, coin.position.x - 0.5f, coin.position.y - 0.5f, 1, 1);
		}

		len = world.supplies.size();
		for (int i = 0; i < len; i++) {
			Supply supply = world.supplies.get(i);
			batch.draw(Assets.supply, supply.position.x - 0.5f, supply.position.y - 0.5f, 1, 1);
		}
	}

	private void renderEnemies () {
		int len = world.enemies.size();
		for (int i = 0; i < len; i++) {
			Enemy enemy = world.enemies.get(i);
			TextureRegion keyFrame = Assets.enemyFly.getKeyFrame(enemy.stateTime, Animation.ANIMATION_LOOPING);
			float side = enemy.velocity.x < 0 ? -1 : 1;
			if (side < 0)
				batch.draw(keyFrame, enemy.position.x + 0.5f, enemy.position.y - 0.5f, side * 1, 1);
			else
				batch.draw(keyFrame, enemy.position.x - 0.5f, enemy.position.y - 0.5f, side * 1, 1);
		}
	}

	private void renderCastle () {
		Castle castle = world.castle;
		batch.draw(Assets.castle, castle.position.x - 1, castle.position.y - 1, 2, 2);
	}

	public void renderProjectiles() {
		int len = world.projectiles.size();
		for (int i = 0; i < len; i++) {
			Projectile projectile = world.projectiles.get(i);
			TextureRegion keyFrame = Assets.projectileAnim.getKeyFrame(projectile.stateTime, Animation.ANIMATION_LOOPING);
			float side = projectile.velocity.x < 0 ? -1 : 1;
			if (side < 0)
				batch.draw(keyFrame, projectile.position.x + 0.4f, projectile.position.y - 0.2f, side * 0.4f, 0.8f);
			else
				batch.draw(keyFrame, projectile.position.x - 0.4f, projectile.position.y - 0.2f, side * 0.4f, 0.8f);
		}
	}
}
