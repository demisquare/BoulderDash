package ai;

import model.Enemy;
import menu.Options;
import menu.Options.Difficulty;

public class IntelligentEnemy extends Enemy {

/*
	Come gestisco il livello di difficoltà?
	
	abbiamo tre livelli:
	- Paradiso		->		random
	- Purgatiorio	->		distanza di Manatthan 
	- Inferno		->		cammino minimo (sempre con Manatthan?)
	
*/
	public IntelligentEnemy(int x, int y, int s) {
		super(x, y, s);
	}
	
	@Override
	protected void calculateDirection() {
		
		if(Options.difficulty == Difficulty.paradiso) {
			super.calculateDirection();
			
		} else if(Options.difficulty == Difficulty.purgatorio) {
			lastDir = MovementAlgorithms.manatthanDistance();
			
		} else if(Options.difficulty == Difficulty.inferno) {
			lastDir = MovementAlgorithms.greedyWalk();
			
		}
	}
	
	@Override
	public boolean update() {
		
		++delayMovement;
		if(delayMovement >= 3) {
			
			delayMovement = 0;
		}
		
		return false;
	}

	
	@Override
	public boolean update(int dir) {
		return false;
	}
}
