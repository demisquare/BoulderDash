package graphics;

import java.awt.Graphics;

import logic.World;

public class Renderer {
	
	//dimensioni viewport
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;
	
	static void init(World world)
	{
		Camera.offsetMaxX = world.getWidth() - WINDOW_WIDTH;
		Camera.offsetMaxY = world.getHeight() - WINDOW_HEIGHT;
	}
	
	static void render(Graphics g, World world)
	{	
		Camera.x = world.getPlayer().getX() - (WINDOW_WIDTH / 2);
		Camera.y = world.getPlayer().getY() - (WINDOW_HEIGHT / 2);
		
		Camera.set(g);
		
		//disegna mappa...
		for(int i = 0; i < world.getMap().getDimX(); i++)
			for(int j = 0; j < world.getMap().getDimY(); j++)
				g.drawImage(world.getMap().getTile(i, j).getSprite(), i*Sprite.TILE_SIZE, j*Sprite.TILE_SIZE, null);
		
		//disegna player...
		g.drawImage(world.getPlayer().ls.getAnimation().getSprite(), world.getPlayer().getX()*Sprite.TILE_SIZE, world.getPlayer().getY()*Sprite.TILE_SIZE, null);
		
	}
	
}
