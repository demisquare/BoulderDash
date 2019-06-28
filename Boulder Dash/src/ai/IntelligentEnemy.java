package ai;

import model.Enemy;
import model.GameMap;
import menu.Options;
import menu.Options.Difficulty;

public class IntelligentEnemy extends Enemy implements Agent {

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
			System.out.println("parafiso");
			
		} else if(Options.difficulty == Difficulty.purgatorio) {
			lastDir = MovementAlgorithms.manatthanDistance(this);
			System.out.println("purgafiso");
			
		} else if(Options.difficulty == Difficulty.inferno) {
			lastDir = MovementAlgorithms.greedyWalk(this);
			System.out.println("infiso");
			
		}
	}
	
	@Override
	public boolean update() {
		
		++delayMovement;
		if(delayMovement >= 3) {
			
			delayMovement = 0;
			calculateDirection();
			return move(lastDir);
		}
		
		return false;
	}

	
	@Override
	public boolean update(int dir) {
		return false;
	}

	@Override
	public GameMap getEnvironment() {
		return map;
	}
}
