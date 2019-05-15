package logic;

import graphics.LivingSprite;

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
		
		int i = (x + dmap[dir][0] * speed + map.dimX)%map.dimX;
		int j = (y + dmap[dir][1] * speed + map.dimY)%map.dimY;
		
		if(map.getTile(i, j).getType() == Block.EMPTY_BLOCK) {
			
			x = i;
			y = j;
			
			return true;
		}
		return false;
	}
}
