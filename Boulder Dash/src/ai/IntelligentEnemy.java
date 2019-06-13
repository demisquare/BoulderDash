package ai;

import model.Enemy;

public class IntelligentEnemy extends Enemy {

/*
	Come gestisco il livello di difficoltà?
	
	abbiamo tre livelli:
	- Paradiso		->		distanza di Manatthan
	- Purgatiorio	->		distanza di Manatthan ricorsiva
	- Inferno		->		cammino minimo
	
*/
	
	public IntelligentEnemy(int x, int y, int s) {
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
