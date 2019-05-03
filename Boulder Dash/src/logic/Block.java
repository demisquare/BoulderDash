package logic;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public abstract class Block {

	private int x;
	private int y;
	protected BufferedImage sprite;

	public Block(int x, int y) {
		super();
		this.x = x;
		this.y = y;

	}

	protected void loadSprite(String file) {
		try {
			sprite = ImageIO.read(new File("assets" + File.separator + file + ".png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public BufferedImage getSprite() {
		return sprite;
	}

	public void setSprite(BufferedImage sprite) {
		this.sprite = sprite;
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
	};

}
