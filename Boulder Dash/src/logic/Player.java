package logic;

import graphics.LivingSprite;
import graphics.Renderer;
import graphics.Sprite;

public class Player extends Living {

	public LivingSprite ls;
	
	public Player(int x, int y, int speed) {
		super(x, y, speed);
		ls = new LivingSprite("playerSpriteSheet", speed);	
	}

	//le posizioni del player vengono aggiornate a seconda della direzione
	//dopodiché vengono ridotte per evitare che sfori
	//ATTENZIONE: in questo modo la mappa e' chiusa in ogni direzione (toroidale)
	@Override
	public boolean move(int dir) {
		
		//int i = (x + dmap[dir][0] * speed + map.dimX*Sprite.TILE_SIZE)%(map.dimX*Sprite.TILE_SIZE);
		//int j = (y + dmap[dir][1] * speed + map.dimY*Sprite.TILE_SIZE)%(map.dimY*Sprite.TILE_SIZE);
		
		int i = (x + dmap[dir][0]);
		int j = (y + dmap[dir][1]);
		
		if((i < 0 || i >= map.dimX) || (j < 0 || j >= map.dimY))
			return false;
		
		if(map.getTile(i, j).getType() == Block.EMPTY_BLOCK) {
			
			if(movecount < speed)	
				++movecount;
			else {
				movecount = 0;
				x = i;
				y = j;
			}
			return true;
		}
		return false;
	}
}
