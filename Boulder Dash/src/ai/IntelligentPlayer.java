package ai;

import model.Player;

public class IntelligentPlayer extends Player {
	
/*
 	Un livello di intelligenza dovrebbe essere sufficente

	Resta da decidere l'algoritmo esatto
*/
	
	public IntelligentPlayer(int x, int y, int s) {
		super(x, y, s);
	}

	@Override
	public boolean update() {
		return false;
	}
	
	@Override
	public boolean update(int dir) {
		return false;
	}
}
