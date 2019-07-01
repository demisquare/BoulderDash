//AUTORE: Maria De Miglio
package view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/*Classe che gestisce lo spritesheet di un personaggio*/
public class Sprite {

	private BufferedImage spriteSheet;
	public static final int TILE_SIZE = 48;

	public Sprite() {
		spriteSheet = null;
	}
	
	// carica lo spritesheet...
	public void loadSprite(String file) {

		try {
			spriteSheet = ImageIO.read
					(new File("." + File.separator +
							  "resources" + File.separator +
							  "assets" + File.separator +
							  TILE_SIZE + File.separator +
							  file + ".png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// restituisce lo sprite singolo date le coordinate...
	public BufferedImage getSprite(int xGrid, int yGrid) {

		return spriteSheet.getSubimage(xGrid * TILE_SIZE, yGrid * TILE_SIZE, TILE_SIZE, TILE_SIZE);
	}

}