package model;

//definisce un player che viene mosso dall'altra istanza attraverso la rete...
public class Host extends Player {
	public Host(int x, int y, int s) {
		super(x, y, s);
	}
	
	public void respawn(int x, int y) {
		
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		};
		
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
}