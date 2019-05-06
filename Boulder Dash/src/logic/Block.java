package logic;

import java.awt.image.BufferedImage;

import graphics.Sprite;

public abstract class Block {

	//corrispondono ai valori di ritorno dei vari getType: essendo public, si possono usare all'esterno in modo simile a Color.RED
	public static byte types[] = { EMPTY_BLOCK = 0, DIAMOND = 1, GROUND = 2, ROCK = 3};

	private int x;
	private int y;
	protected Sprite spritesheet;
	protected BufferedImage sprite;

	public abstract byte getType(); //per distinguere i vari blocchi iterando su Map
	public abstract void update();  //interfaccia per i metodi delle classi figlie

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
