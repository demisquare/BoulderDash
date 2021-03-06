//AUTORE: Maria De Miglio
package view;

import java.awt.Graphics;
import java.awt.Toolkit;

import menu.Options;
import model.World;

public class Renderer {

//	la classe � completamente statica
	private Renderer() {};

	// dimensioni viewport
	public static int WINDOW_WIDTH = 920;
	public static int WINDOW_HEIGHT = 720;

	static void init(World world) {
		
		if(Options.full_screen) {
			Toolkit tk = Toolkit.getDefaultToolkit();
			double xSize = tk.getScreenSize().getWidth();
			double ySize = tk.getScreenSize().getHeight();

			Camera.offsetMaxX = world.getWidth() - (int) ((WINDOW_WIDTH * xSize) / 1280);
			Camera.offsetMaxY = world.getHeight() - (int) ((WINDOW_HEIGHT * ySize) / 720);
		
		} else {
			
			Camera.offsetMaxX = world.getWidth() - WINDOW_WIDTH;
			Camera.offsetMaxY = world.getHeight() - WINDOW_HEIGHT;
		}
	}

	static void render(Graphics g, Level l) {

		if(Options.full_screen) {
			
			Toolkit tk = Toolkit.getDefaultToolkit();
			double xSize = tk.getScreenSize().getWidth();
			double ySize = tk.getScreenSize().getHeight();

			// imposta camera...
			Camera.x = l.world.getPlayer().getX() * Sprite.TILE_SIZE - (int) (((WINDOW_WIDTH * xSize) / 1280) / 2) + Sprite.TILE_SIZE;
			Camera.y = l.world.getPlayer().getY() * Sprite.TILE_SIZE - (int) (((WINDOW_HEIGHT * ySize) / 720) / 2) + Sprite.TILE_SIZE;

			Camera.set();
		
		} else {
			Camera.x = l.world.getPlayer().getX() * Sprite.TILE_SIZE - (WINDOW_WIDTH / 2) + Sprite.TILE_SIZE;
			Camera.y = l.world.getPlayer().getY() * Sprite.TILE_SIZE - (WINDOW_HEIGHT/ 2) + Sprite.TILE_SIZE;

			Camera.set();
		}
		// muovi telecamera...
		g.translate(-Camera.x, -Camera.y);
		
		// disegna mappa...
		for (int i = 0; i < l.getBlockSprites().size(); ++i) {

			int x = l.getBlockSprites().get(i).getLogicObject().getX();
			int y = l.getBlockSprites().get(i).getLogicObject().getY();

			g.drawImage(
					l.getBlockSprites().get(i).getImg(), 
					x * Sprite.TILE_SIZE, 
					y * Sprite.TILE_SIZE, 
					null);
		}

		// disegna player...
		for (int i = 0; i < l.playerSprites.size(); ++i) {

			g.drawImage(
					l.playerSprites.get(i).getAnimation().getSprite(),
					l.world.getPlayer().getX() * Sprite.TILE_SIZE, 
					l.world.getPlayer().getY() * Sprite.TILE_SIZE, 
					null);
		}

		if (Options.multiplayer) {
			// disegna host...
			for (int i = 0; i < l.hostSprites.size(); ++i) {

				g.drawImage(
						l.hostSprites.get(i).getAnimation().getSprite(),
						l.world.getHost().getX() * Sprite.TILE_SIZE, 
						l.world.getHost().getY() * Sprite.TILE_SIZE, 
						null);
			}
		}

		for (int i = 0; i < l.enemySprites.size(); ++i) {
			// disegna i nemici...
			if(!l.enemySprites.get(i).getLogicObj().isDead())
			g.drawImage(
					l.enemySprites.get(i).getAnimation().getSprite(),
					l.world.getEnemies().get(i).getX() * Sprite.TILE_SIZE,
					l.world.getEnemies().get(i).getY() * Sprite.TILE_SIZE, 
					null);
		}
	}
}
