package logic;

public class Enemy extends Living {
	
	public Enemy(int x, int y, int speed) {
		super(x, y, speed);
	}

	//verifica che le condizioni richieste siano soddisfatte per la direzione scelta
	//Da rielaborare
	@Override
	public boolean move(int p) {
		
		
		//per pulizia rinomino i valori mappati per la direzione p
		int i = dmap[p][0];
		int j = dmap[p][1];
		int x1 = (x+j >= 0 && x+j < map.dimX) ? x+j : -1;
		int x2 = (x-j >= 0 && x-j < map.dimX) ? x-j : -1;
		int y1 = (y+i >= 0 && y+i < map.dimY) ? y+i : -1;
		int y2 = (y-i >= 0 && y-i < map.dimY) ? y-i : -1;
		
		if(map.getTile(x+i, y+j) instanceof EmptyBlock) {
			if((x1 != -1 && y1 != -1 && !(map.getTile(x1, y1) instanceof EmptyBlock)) ||
			   (x1 != -1 && y2 != -1 && !(map.getTile(x1, y2) instanceof EmptyBlock)) ||
			   (x2 != -1 && y1 != -1 && !(map.getTile(x2, y1) instanceof EmptyBlock)) ||
			   (x2 != -1 && y2 != -1 && !(map.getTile(x2, y2) instanceof EmptyBlock))) {
				
				x += i;
				y += j;
				return true;
			}
		}
		return false;
		
		
	}
}
