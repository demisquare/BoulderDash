package model;

//definisce un player che viene mosso dall'altra istanza attraverso la rete...
public class Host extends Player {
	public Host(int x, int y, int s) {
		super(x, y, s);
	}
	
	public void respawn(int x, int y) {
		
		if(lifes > 1) {
			respawned = true;
			
			swap(x, y);
			--lifes;
		
		} else {
			moved = false;
			lifes = 0;
			destroy();
		}
	}
	
	@Override
	protected boolean move(int dir) {
		
		int i = x + dmap[dir][0];
		int j = y + dmap[dir][1];

		if (!(i < 0 || i >= map.dimX) && !(j < 0 || j >= map.dimY)) {

			if (map.getTile(i, j) instanceof EmptyBlock) {

				moved = true;
				//System.out.println("si muove...");
				swap(i, j);
					
				return true;

			} else if (map.getTile(i, j) instanceof Door) {

				moved = true;
				map.setWinCon(true);

				//System.out.println("VITTORIA");

				destroy();
				
				return true;

			} else if (map.getTile(i, j) instanceof Enemy) {

				moved = true;
				
				respawn();
				
				return true;
			}
		}
		
		moved = false;
		return moved;
	}
}