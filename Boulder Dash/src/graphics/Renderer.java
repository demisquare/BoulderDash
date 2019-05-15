package graphics;

import java.awt.Graphics;

import logic.*;

public class Renderer {

	static void render(Graphics g, World world)
	{
		g.drawImage(world.getPlayer().ls.getAnimation().getSprite(), world.getPlayer().getX(), world.getPlayer().getY(), null);
		
		for(int i = 0; i < world.getMap().getDimX(); i++)
			for(int j = 0; j < world.getMap().getDimY(); j++)
				g.drawImage(world.getMap().getTile(i, j).getSprite(), i*32, j*32, null);
	}
	
}
