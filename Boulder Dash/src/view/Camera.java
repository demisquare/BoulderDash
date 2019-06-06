package view;

public class Camera {

	public static int x = 0;
	public static int y = 0;

	public static int offsetMaxX = 0;
	public static int offsetMaxY = 0;

	public static int offsetMinX = 0;
	public static int offsetMinY = 0;
	
	static void set() {
		// TODO: gestione hitbox camera...
		if (x > offsetMaxX) {
			x = offsetMaxX+6;
			}

		else if (x < offsetMinX)
			x = offsetMinX;

		if (y > offsetMaxY + Sprite.TILE_SIZE)
			y = offsetMaxY + Sprite.TILE_SIZE-3;

		else if (y < offsetMinY)
			y = offsetMinY;

	}

}
