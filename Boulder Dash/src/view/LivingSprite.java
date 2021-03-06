package view;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import model.*;

public class LivingSprite extends DummyClass {

	public static final int DOWN = 0;
	public static final int LEFT = 1;
	public static final int RIGHT = 2;
	public static final int UP = 3;

	// public static final int STANDING = 4;

	int counter;
	
	private ArrayList<BufferedImage> walkingLeftSprites;
	private ArrayList<BufferedImage> walkingRightSprites;
	private ArrayList<BufferedImage> walkingUpSprites;
	private ArrayList<BufferedImage> walkingDownSprites;

	private ArrayList<BufferedImage> standingLeftSprites;
	private ArrayList<BufferedImage> standingRightSprites;
	private ArrayList<BufferedImage> standingUpSprites;
	private ArrayList<BufferedImage> standingDownSprites;

	private Animation walkLeft;
	private Animation walkRight;
	private Animation walkUp;
	private Animation walkDown;

	private Animation standLeft;
	private Animation standRight;
	private Animation standUp;
	private Animation standDown;

	private Animation animation;

	private Sprite sprite;
	
	public LivingSprite(String file, int speed, GameObject obj) {
		super(obj);
		
		counter = 0;
		
		logicObj = obj;
		
		sprite = new Sprite();
		sprite.loadSprite(file);

		walkingDownSprites = new ArrayList<BufferedImage>();
		walkingDownSprites.add(sprite.getSprite(0, DOWN));
		walkingDownSprites.add(sprite.getSprite(1, DOWN));
		walkingDownSprites.add(sprite.getSprite(2, DOWN));
		walkingDownSprites.add(sprite.getSprite(1, DOWN));

		walkingLeftSprites = new ArrayList<BufferedImage>();
		walkingLeftSprites.add(sprite.getSprite(0, LEFT));
		walkingLeftSprites.add(sprite.getSprite(1, LEFT));
		walkingLeftSprites.add(sprite.getSprite(2, LEFT));
		walkingLeftSprites.add(sprite.getSprite(1, LEFT));

		walkingRightSprites = new ArrayList<BufferedImage>();
		walkingRightSprites.add(sprite.getSprite(0, RIGHT));
		walkingRightSprites.add(sprite.getSprite(1, RIGHT));
		walkingRightSprites.add(sprite.getSprite(2, RIGHT));
		walkingRightSprites.add(sprite.getSprite(1, RIGHT));

		walkingUpSprites = new ArrayList<BufferedImage>();
		walkingUpSprites.add(sprite.getSprite(0, UP));
		walkingUpSprites.add(sprite.getSprite(1, UP));
		walkingUpSprites.add(sprite.getSprite(2, UP));
		walkingUpSprites.add(sprite.getSprite(1, UP));

		standingDownSprites = new ArrayList<BufferedImage>();
		standingLeftSprites = new ArrayList<BufferedImage>();
		standingRightSprites = new ArrayList<BufferedImage>();
		standingUpSprites = new ArrayList<BufferedImage>();

		standingDownSprites.add(sprite.getSprite(1, DOWN));
		standingLeftSprites.add(sprite.getSprite(1, LEFT));
		standingRightSprites.add(sprite.getSprite(1, RIGHT));
		standingUpSprites.add(sprite.getSprite(1, UP));

		walkLeft = new Animation(walkingLeftSprites, speed);
		walkRight = new Animation(walkingRightSprites, speed);
		walkUp = new Animation(walkingUpSprites, speed);
		walkDown = new Animation(walkingDownSprites, speed);

		standLeft = new Animation(standingLeftSprites, speed);
		standRight = new Animation(standingRightSprites, speed);
		standUp = new Animation(standingUpSprites, speed);
		standDown = new Animation(standingDownSprites, speed);

		this.animation = standDown;

	}

	public Animation getAnimation() {
		return animation;
	}

	private void setMovePose(int dir) {
		switch (dir) {
		case RIGHT:
			// System.out.println("right");
			this.animation = walkRight;
			break;

		case LEFT:
			// System.out.println("left");
			this.animation = walkLeft;
			break;

		case UP:
			// System.out.println("up");
			this.animation = walkUp;
			break;

		case DOWN:
			// System.out.println("down");
			this.animation = walkDown;
			break;

		default:
			break;

		}
	}

	private void setStandPose(int dir) {
		switch (dir) {
		case RIGHT:
			// System.out.println("right");
			this.animation = standRight;
			break;

		case LEFT:
			// System.out.println("left");
			this.animation = standLeft;
			break;

		case UP:
			// System.out.println("up");
			this.animation = standUp;
			break;

		case DOWN:
			// System.out.println("down");
			this.animation = standDown;
			break;

		default:
			break;

		}
	}
	
	public void movePose(int dir)
	{
		setMovePose(dir);
		getAnimation().start();
	}
	
	public void standPose(int dir)
	{
		setStandPose(dir);
		getAnimation().start();
	}
}
