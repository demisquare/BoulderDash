package ai;

import model.Enemy;
import model.GameMap;
import menu.Options;
import menu.Options.Difficulty;

public class IntelligentEnemy extends Enemy implements Agent {

	int[] tryAgain;
/*
	Come gestisco il livello di difficoltà?
	
	abbiamo tre livelli:
	- Paradiso		->		random
	- Purgatiorio	->		distanza di Manatthan 
	- Inferno		->		cammino minimo (sempre con Manatthan?)
	
*/
	public IntelligentEnemy(int x, int y, int s) {
		super(x, y, s);
		tryAgain = new int[]{0, 0, 0, 0};
	}
	
	@Override
	protected void calculateDirection() {
		
		if(Options.difficulty == Difficulty.paradiso) {
			
			//System.out.println("parafiso");
			
			super.calculateDirection();
			
		} else if(Options.difficulty == Difficulty.purgatorio) {
			
			//System.out.println("purgafiso");
			
			lastDir = MovementAlgorithms.euclideanDistance(this);
			
		} else if(Options.difficulty == Difficulty.inferno) {
			
			//System.out.println("infiso");
			
			lastDir = MovementAlgorithms.greedyWalk(this);
		}
		
		tryAgain[0] = lastDir;
		tryAgain[1] = (lastDir + 1) % 4;
		tryAgain[2] = (lastDir + 3) % 4;
		tryAgain[3] = (lastDir + 2) % 4;
	}
	
	@Override
	public boolean update() {
		
		++delayMovement;
		if(delayMovement >= 3) {
			
			delayMovement = 0;
			calculateDirection();
			return tryMove();
		}
		
		return false;
	}

	
	private boolean tryMove() {
		for(int i = 0; i < 4; ++i)
			if(move(tryAgain[i]))
				return true;
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
