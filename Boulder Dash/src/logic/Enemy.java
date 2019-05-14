package logic;

class Enemy extends Living {

	public Enemy(int x, int y, int speed) {
		super(x, y, speed);
	}

	//verifica che le condizioni richieste siano soddisfatte per la direzione scelta
	@Override
	public boolean move(int p) {
		//per pulizia rinomino i valori mappati per la direzione p
		int i = dmap[p][0];
		int j = dmap[p][1];
		int x1 = (x+j)%map.dimX;
		int x2 = (x-j)%map.dimX;
		int y1 = (y+i)%map.dimY;
		int y2 = (y-i)%map.dimY;
		
		if(map.getTile(i, j).getType() == Block.EMPTY_BLOCK) {
			if((map.getTile(x1, y1).getType() != Block.EMPTY_BLOCK)||
			   (map.getTile(x1, y2).getType() != Block.EMPTY_BLOCK)||
			   (map.getTile(x2, y1).getType() != Block.EMPTY_BLOCK)||
			   (map.getTile(x2, y2).getType() != Block.EMPTY_BLOCK)) {
				
				x += i * speed;
				x %= map.dimX;
				
				y += j * speed;
				y %= map.dimY;
				
				return true;
			}
		}
		return false;
	}
}
