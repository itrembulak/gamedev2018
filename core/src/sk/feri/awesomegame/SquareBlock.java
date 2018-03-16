package sk.feri.awesomegame;

/**
 * Created by Vobby on 16. 3. 2018.
 */

public class SquareBlock extends DynamicGameObject {

    public static final float BLOCK_WIDTH = 0.5f;
    public static final float BLOCK_HEIGHT = 0.5f;
    public static final int BLOCK_TYPE_STATIC = 0;
    public static final int BLOCK_TYPE_MOVING = 1;
    public static final int BLOCK_STATE_NORMAL = 0;
    public static final int BLOCK_STATE_PULVERIZING = 1;
    public static final float BLOCK_PULVERIZE_TIME = 0.1f * 4;
    public static final float BLOCK_VELOCITY = 2;

    int type;
    int state;
    float stateTime;
    int lives;

    public SquareBlock(int type, float x, float y, int lives) {
        super(x, y, BLOCK_WIDTH, BLOCK_HEIGHT);
        this.type = type;
        this.state = BLOCK_STATE_NORMAL;
        this.stateTime = 0;
        if (type == BLOCK_TYPE_MOVING) {
            velocity.x = BLOCK_VELOCITY;
        }
        this.lives = lives;
    }

    public void update (float deltaTime) {
        if (type == BLOCK_TYPE_MOVING) {
            position.add(velocity.x * deltaTime, 0);
            bounds.x = position.x - BLOCK_WIDTH / 2;
            bounds.y = position.y - BLOCK_HEIGHT / 2;

            if (position.x < BLOCK_WIDTH / 2) {
                velocity.x = -velocity.x;
                position.x = BLOCK_WIDTH / 2;
            }
            if (position.x > World.WORLD_WIDTH - BLOCK_WIDTH / 2) {
                velocity.x = -velocity.x;
                position.x = World.WORLD_WIDTH - BLOCK_WIDTH / 2;
            }
        }

        stateTime += deltaTime;
    }

    public void pulverize () {
        if (state != BLOCK_STATE_PULVERIZING){
            state = BLOCK_STATE_PULVERIZING;
            stateTime = 0;
        }
        velocity.x = 0;

    }

    public void hitProjectile(){
        lives--;
        if (lives <= 0){
            pulverize();
        }
    }




}
