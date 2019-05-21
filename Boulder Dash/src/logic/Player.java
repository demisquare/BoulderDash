package logic;

import graphics.LivingSprite;

public class Player extends Living {

	private int diamondCount;
	public LivingSprite ls;
	
	public Player(int x, int y, int speed) {
		super(x, y, speed);
		diamondCount = 0;
		ls = new LivingSprite("playerSpriteSheet", speed);	
	}

	//le posizioni del player vengono aggiornate a seconda della direzione
	//dopodiché vengono ridotte per evitare che sfori
	//ATTENZIONE: in questo modo la mappa e' chiusa in ogni direzione (toroidale)
	@Override
	public boolean move(int dir) {
		
		int i = (x + dmap[dir][0]);
		int j = (y + dmap[dir][1]);
		
		if((i < 0 || i >= map.dimX) || (j < 0 || j >= map.dimY))
			return false;
		
		if(map.getTile(i, j).getType() == Block.EMPTY_BLOCK) {
			
			x = i;
			y = j;
			
			return true;
		
		} else if(map.getTile(i, j).getType() == Block.DIAMOND) {
		
			x = i;
			y = j;
			
			map.getTile(i, j).update(true);
			++diamondCount;
			
			return true;
		}
		
		return false;
	}
}
