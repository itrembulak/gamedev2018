package sk.feri.awesomegame;

public class Supply extends GameObject {
	public static final float SUPPLY_WIDTH = 1f;
	public static final float SUPPLY_HEIGHT = 1f;

	public int SUPPLY_QUANTITY;
	float stateTime;

	public Supply(float x, float y, int quantity) {
		super(x, y, SUPPLY_WIDTH, SUPPLY_HEIGHT);
		stateTime = 0;
		SUPPLY_QUANTITY = quantity;
	}

	public void update (float deltaTime) {
		stateTime += deltaTime;
	}
}
