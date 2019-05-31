package logic;

public class Player extends Living {

	private int diamondCount;
	
	public Player(int x, int y, int speed) {
		super(x, y, speed);
		diamondCount = 0;
	}

	//le posizioni del player vengono aggiornate a seconda della direzione
	//dopodiché vengono ridotte per evitare che sfori
	//ATTENZIONE: in questo modo la mappa e' chiusa in ogni direzione (toroidale)
	@Override
	public boolean move(int dir) {
		
		int i = (x + dmap[dir][0]);
		int j = (y + dmap[dir][1]);
		
		if( (i < 0 || i >= map.dimX) || (j < 0 || j >= map.dimY) )
			return false;
		
		if(map.getTile(i, j) instanceof EmptyBlock) {
			
			x = i;
			y = j;
			
			return true;
		}
		
		//raccogli diamanti...
		else if(map.getTile(i, j) instanceof Diamond) {
		
			x = i;
			y = j;
			
			map.getTile(i, j).update(true);
			++diamondCount;
			
			return true;
		}
		
		//scava la terra...
		else if(map.getTile(i, j) instanceof Ground) {
		
			x = i;
			y = j;
			
			map.getTile(i, j).update(true);
			
			return true;
		}
		
		return false;
	}
}
