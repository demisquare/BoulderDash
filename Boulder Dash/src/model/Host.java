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
			lifes = 0;
			destroy();
		}
	}
}