//AUTORE: Davide Caligiuri
package view;

import java.awt.image.BufferedImage;

import model.GameObject;

public class BlockSprite extends DummyClass {

	private BufferedImage img;
	
	public BlockSprite(BufferedImage img) {
		super(null);	
		this.img = img;
	}
	
	public BlockSprite(BufferedImage img, GameObject g) {
		super(g);
		this.img = img;
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
