
package sk.feri.awesomegame;


 public class Player extends DynamicGameObject {
	public static final int PLAYER_STATE_NORMAL = 0;
	public static final int PLAYER_STATE_HIT = 1;
	public static final float PLAYER_MOVE_VELOCITY = 30;
	public static final float PLAYER_WIDTH = 0.8f;
	public static final float PLAYER_HEIGHT = 0.8f;
	public static float PLAYER_MOVE_UP_VELOCITY = 5;

	 private int projectilesCount = 20;
	int state;
	float stateTime;

	public Player(float x, float y) {
		super(x, y, PLAYER_WIDTH, PLAYER_HEIGHT);
		state = PLAYER_STATE_NORMAL;
		stateTime = 0;
	}

	public void update (float deltaTime) {
		position.add(velocity.x * deltaTime, velocity.y * deltaTime);
		bounds.x = position.x - bounds.width / 2;
		bounds.y = position.y - bounds.height / 2;

		velocity.y = PLAYER_MOVE_UP_VELOCITY;

		// Block on side borders
		/*float wallMargin = (bounds.getWidth() / 2) + 0.1f;
		if (position.x <  wallMargin) position.x = wallMargin;
		if (position.x > World.WORLD_WIDTH - wallMargin) position.x = World.WORLD_WIDTH - wallMargin;*/

		// Go through the side borders
		if (position.x < 0) position.x = World.WORLD_WIDTH;
		if (position.x > World.WORLD_WIDTH) position.x = 0;

		stateTime += deltaTime;
	}


	public void hitEnemy () {
		velocity.set(0, 0);
		state = PLAYER_STATE_HIT;
		stateTime = 0;
	}

	 public void hitSupply(int count) {
		 projectilesCount += count;
	 }

	 public void hitBlock () {
		 velocity.set(0, 0);
		 state = PLAYER_STATE_HIT;
		 stateTime = 0;
	 }

	public void projectileShot(){
		projectilesCount --;
	}

	 public int getProjectileCount() {
		 return projectilesCount;
	 }

	 public static void setPlayerMoveUpVelocity(float velocity){
		 PLAYER_MOVE_UP_VELOCITY = velocity;
	 }
	 public void setProjectilesCount(int count){
		 projectilesCount = count;
	 }
 }
