package view;

import java.awt.image.BufferedImage;

import model.GameObject;

public class BlockSprite {

	private BufferedImage img;
	private GameObject g;
	
	public BlockSprite(BufferedImage img) {
		this.img = img;
		g = null;
	}
	
	public BlockSprite(BufferedImage img, GameObject g) {
		this.img = img;
		this.g = g;
	}

	public BufferedImage getImg() {
		return img;
	}

	public void setImg(BufferedImage img) {
		this.img = img;
	}

	public GameObject getLogicObject() {
		return g;
	}

	public void setLogicObject(GameObject g) {
		this.g = g;
	}
	
	
}
