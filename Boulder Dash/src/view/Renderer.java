package view;

import java.awt.Graphics;

import model.World;

public class Renderer {

	private Renderer() {};
	
	// dimensioni viewport
	public static final int WINDOW_WIDTH = 920;
	public static final int WINDOW_HEIGHT = 720;

	static void init(World world) {
		// TODO: definire hitbox...
		 Camera.offsetMaxX = world.getWidth() - WINDOW_WIDTH;
		 Camera.offsetMaxY = world.getHeight() - WINDOW_HEIGHT;
	}

	static void render(Graphics g, Level l) {
		// imposta camera...
		Camera.x = l.world.getPlayer().getX() * Sprite.TILE_SIZE - (WINDOW_WIDTH / 2) + Sprite.TILE_SIZE;
		Camera.y = l.world.getPlayer().getY() * Sprite.TILE_SIZE - (WINDOW_HEIGHT / 2) + Sprite.TILE_SIZE;

		// TODO: imposta hitbox camera...
		Camera.set();

		// muovi telecamera...
		g.translate(-Camera.x, -Camera.y);

		// disegna mappa...
		for(int i = 0; i < l.getBlockSprites().size(); ++i) {
			
			int x = l.getBlockSprites().get(i).getLogicObject().getX();
			int y = l.getBlockSprites().get(i).getLogicObject().getY();
			
			g.drawImage(l.getBlockSprites().get(i).getImg(), x * Sprite.TILE_SIZE, y * Sprite.TILE_SIZE, null);
		}
		
		// disegna player...
		for(int i = 0; i < l.playerSprites.size(); ++i) {
			
			g.drawImage(l.playerSprites.get(i).getAnimation().getSprite(), l.world.getPlayer().getX() * Sprite.TILE_SIZE,
				l.world.getPlayer().getY() * Sprite.TILE_SIZE, null);
		}
		
		for(int i = 0; i < l.enemySprites.size(); ++i) {
			// disegna i nemici...
			g.drawImage(l.enemySprites.get(i).getAnimation().getSprite(), 
					l.world.getEnemies().get(i).getX() * Sprite.TILE_SIZE,
					l.world.getEnemies().get(i).getY() * Sprite.TILE_SIZE, null);
		}
	}
}
