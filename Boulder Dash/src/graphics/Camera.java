package graphics;

import java.awt.Graphics;

public class Camera {

	public static int x = 0;
	public static int y = 0;

	public static int offsetMaxX = 0;
	public static int offsetMaxY = 0;

	public static int offsetMinX = 0;
	public static int offsetMinY = 0;

	static void set(Graphics g) {

		if (x > offsetMaxX)
			x = offsetMaxX;

		else if (x < offsetMinX)
			x = offsetMinX;

		if (y > offsetMaxY)
			y = offsetMaxY;

		else if (y < offsetMinY)
			y = offsetMinY;

		g.translate(-x, -y);

	}

}
