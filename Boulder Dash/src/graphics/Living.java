package graphics;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class Living {

	public static final int DOWN = 0;
	public static final int LEFT = 1;
	public static final int RIGHT = 2;
	public static final int UP = 3;

	private ArrayList<BufferedImage> walkingLeftSprites;
	private ArrayList<BufferedImage> walkingRightSprites;
	private ArrayList<BufferedImage> walkingUpSprites;
	private ArrayList<BufferedImage> walkingDownSprites;

	private ArrayList<BufferedImage> standingSprites;

	private Animation walkLeft;
	private Animation walkRight;
	private Animation walkUp;
	private Animation walkDown;

	private Animation standPose;

	private Animation animation;

	private Sprite sprite;

	int x;
	int y;

	int speed;

	public Living(String file, int x, int y, int speed) {
		this.x = x;
		this.y = y;
		this.speed = speed;

		sprite.loadSprite(file);

		walkingDownSprites = new ArrayList<BufferedImage>();
		walkingDownSprites.add(sprite.getSprite(0, DOWN));
		walkingDownSprites.add(sprite.getSprite(2, DOWN));

		walkingLeftSprites = new ArrayList<BufferedImage>();
		walkingLeftSprites.add(sprite.getSprite(0, LEFT));
		walkingLeftSprites.add(sprite.getSprite(2, LEFT));

		walkingRightSprites = new ArrayList<BufferedImage>();
		walkingRightSprites.add(sprite.getSprite(0, RIGHT));
		walkingRightSprites.add(sprite.getSprite(2, RIGHT));

		walkingUpSprites = new ArrayList<BufferedImage>();
		walkingUpSprites.add(sprite.getSprite(0, UP));
		walkingUpSprites.add(sprite.getSprite(2, UP));

		standingSprites = new ArrayList<BufferedImage>();
		standingSprites.add(sprite.getSprite(1, DOWN));
		standingSprites.add(sprite.getSprite(1, LEFT));
		standingSprites.add(sprite.getSprite(1, RIGHT));
		standingSprites.add(sprite.getSprite(1, UP));

		walkLeft = new Animation(walkingLeftSprites, speed);
		walkRight = new Animation(walkingRightSprites, speed);
		walkUp = new Animation(walkingUpSprites, speed);
		walkDown = new Animation(walkingDownSprites, speed);

		standPose = new Animation(standingSprites, 0);

		animation = standPose;

	}

	public void walk(int dir) {
		switch (dir) {
		case RIGHT:
			x += speed;
			break;

		case LEFT:
			x -= speed;
			break;

		case UP:
			y -= speed;
			break;

		case DOWN:
			y += speed;
			break;

		default:
			break;
		}

	}

}
