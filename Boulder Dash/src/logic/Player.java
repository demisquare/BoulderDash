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
	//ATTENZIONE: in questo modo la mappa è chiusa in ogni direzione (toroidale)
	@Override
	public boolean move(int dir) {
		
		x += dmap[dir][0] * speed;
		//x %= map.dimX;
		
		y += dmap[dir][1] * speed;
		//y %= map.dimY;
		
		return true;
	}
}
