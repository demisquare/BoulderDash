package view;

import java.awt.image.BufferedImage;

import model.GameObject;

public class BlockSprite extends DummyClass {

	private BufferedImage img;
	
	public BlockSprite(BufferedImage img) {
		this.img = img;
		logicObj = null;
	}
	
	public BlockSprite(BufferedImage img, GameObject g) {
		this.img = img;
		this.logicObj = g;
	}

	public BufferedImage getImg() {
		return img;
	}

	public void setImg(BufferedImage img) {
		this.img = img;
	}

	public GameObject getLogicObject() {
		return logicObj;
	}

	public void setLogicObject(GameObject g) {
		this.logicObj = g;
	}
	
	
}
