package ai;

import model.Enemy;
import model.GameMap;

import java.util.LinkedList;

import menu.Options;
import menu.Options.Difficulty;

public class IntelligentEnemy extends Enemy implements Agent {

	private int[] tryAgain;
	private LinkedList<Integer> directions;
	private int counter;
	
	public IntelligentEnemy(int x, int y, int s) {
		super(x, y, s);
		tryAgain = new int[]{0, 0, 0, 0};
		directions = null;
		counter = 5;
	}

	@Override public boolean update(int dir) { return false; }
	
	@Override
	public boolean update() {
		
		++delayMovement;
		if(delayMovement >= 3) {
			
			delayMovement = 0;
			if(calculateDirection()) {
				return tryMove();
			}
		}
		
		moved = false;
		return false;
	}
	
	@Override public GameMap getEnvironment() { return map; }
	
	@Override
	protected boolean calculateDirection() {
		
		boolean isWorking = false;
		
		if(Options.difficulty == Difficulty.paradiso) {
			
			super.calculateDirection();
			
		} else if(Options.difficulty == Difficulty.purgatorio) {
			
			lastDir = MovementAlgorithms.greedyPath(this);
			
		} else if(Options.difficulty == Difficulty.inferno) {
			
			if(counter >= 5 || directions == null || directions.isEmpty()) {
				directions = MovementAlgorithms.optimalPath(this);
				counter = 0;
			
			} else {
				++counter;
			}
			
			if(directions != null && !directions.isEmpty()) {
				System.out.println(directions.getFirst());
				lastDir = directions.getFirst();
				directions.removeFirst();
			}
		}
		
		isWorking = (lastDir >= 0 && lastDir < 4);
		
		tryAgain[0] = lastDir;
		tryAgain[1] = (lastDir + 1) % 4;
		tryAgain[2] = (lastDir + 3) % 4;
		tryAgain[3] = (lastDir + 2) % 4;
		
		return isWorking;
	}
	
	private boolean tryMove() {
		for(int i = 0; i < 4; ++i)
			if(move(tryAgain[i])) {
				return true;
			}
		return false;
	}	
}
