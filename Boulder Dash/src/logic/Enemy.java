package logic;

import graphics.LivingSprite;

public class Enemy extends Living {
	
	public Enemy(int x, int y, int speed) {
		super(x, y, speed);
		ls = new LivingSprite("enemySpriteSheet", speed);
	}

	//verifica che le condizioni richieste siano soddisfatte per la direzione scelta
	//Da rielaborare
	@Override
	public boolean move(int p) {
		//per pulizia rinomino i valori mappati per la direzione p
		int i = dmap[p][0];
		int j = dmap[p][1];
		int x1 = (x+j) >= 0 && (x+j) < map.dimX ? (x+j) : -1;
		int x2 = (x-j) >= 0 && (x-j) < map.dimX ? (x-j) : -1;
		int y1 = (y+i) >= 0 && (y+i) < map.dimY ? (y+i) : -1;
		int y2 = (y-i) >= 0 && (y-i) < map.dimY ? (y-i) : -1;
		
		if(map.getTile(x+i, y+j).getType() == Block.EMPTY_BLOCK) {
			if((x1!=-1 && y1!=-1 && map.getTile(x1, y1).getType() != Block.EMPTY_BLOCK)||
			   (x1!=-1 && y2!=-1 && map.getTile(x1, y2).getType() != Block.EMPTY_BLOCK)||
			   (x2!=-1 && y1!=-1 && map.getTile(x2, y1).getType() != Block.EMPTY_BLOCK)||
			   (x2!=-1 && y2!=-1 && map.getTile(x2, y2).getType() != Block.EMPTY_BLOCK)) {
				
				x += i;
				y += j;
				return true;
			}
		}
		return false;
	}
}
