package logic;

import java.awt.image.BufferedImage;

import graphics.Sprite;

public abstract class Block {

	private int x;
	private int y;
	protected Sprite spritesheet;
	protected BufferedImage sprite;

	public Block(int x, int y) {
		super();
		this.x = x;
		this.y = y;
		spritesheet.loadSprite("blockSpriteSheet");

	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

}
