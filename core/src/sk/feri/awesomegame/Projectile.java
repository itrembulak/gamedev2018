package sk.feri.awesomegame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public class Projectile extends DynamicGameObject {
	public static final float PROJCTILE_WIDTH = 1;
	public static final float PROJCTILE_HEIGHT = 0.6f;
	public static final float PROJCTILE_VELOCITY = 3f;
	public static final float PROJCTILE_RANGE = 17f;
	public static final int PROJECTILE_STATE_FIRED = 0;
	public static final int PROJECTILE_STATE_DESTROY = 1;
	public static final float PROJECTILE_TIME_DESTROY = 0.2f * 2;

	float stateTime = 0;
	int state;
	float fired_y;

	public Projectile(float x, float y) {
		super(x, y, PROJCTILE_WIDTH, PROJCTILE_HEIGHT);
		velocity.set(PROJCTILE_VELOCITY, 0);
		state = PROJECTILE_STATE_FIRED;
		fired_y = y;
	}

	public void update (float deltaTime) {
		position.add(velocity.x * deltaTime, velocity.y * deltaTime);
		bounds.x = position.x - PROJCTILE_WIDTH / 2;
		bounds.y = position.y - PROJCTILE_HEIGHT / 2;

		if (state != PROJECTILE_STATE_DESTROY)
			velocity.y += PROJCTILE_VELOCITY;
		velocity.x = 0;

		if (position.y - fired_y > PROJCTILE_RANGE){
			state = PROJECTILE_STATE_DESTROY;
			velocity.y = 0;
		}

		stateTime += deltaTime;
	}

	public void hitBox(){
		state = PROJECTILE_STATE_DESTROY;
		velocity.y = 0;
	}
}
