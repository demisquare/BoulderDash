package logic;

public abstract class Living {

	public static final int DOWN = 0;
	public static final int LEFT = 1;
	public static final int RIGHT = 2;
	public static final int UP = 3;
	static int directions[][] = {{0, 1}, {-1, 0}, {1, 0}, {0, -1}};
	static Map map = null;
	
	int x;
	int y;
	int speed;

	public Living(int x, int y, int speed) {
		this.x = x;
		this.y = y;
		this.speed = speed;
	}

	public abstract void move(int dir);

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getSpeed() {
		return speed;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
}
