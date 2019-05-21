package graphics;

public class Camera {

	public static int x = 0;
	public static int y = 0;

	public static int offsetMaxX = 0;
	public static int offsetMaxY = 0;

	public static int offsetMinX = 0;
	public static int offsetMinY = 0;
	
	static void set() {
		// TODO: gestione hitbox camera...
		if (x > offsetMaxX + Sprite.TILE_SIZE) {
			x = offsetMaxX+ Sprite.TILE_SIZE/2;
			}

		else if (x < offsetMinX)
			x = offsetMinX;

		if (y > offsetMaxY + Sprite.TILE_SIZE)
			y = offsetMaxY + Sprite.TILE_SIZE;

		else if (y < offsetMinY)
			y = offsetMinY;

	}

}
