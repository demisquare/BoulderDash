package ai;

import model.GameObject;

//TODO
class MovementAlgorithms {
	
	private MovementAlgorithms() {}
	
	public static int manatthanDistance(Agent e) {
	
		if(e instanceof IntelligentEnemy) {
			
			GameObject player = e.getEnvironment().getPlayer();
			GameObject enemy  = (GameObject) e;
			
			int dx = player.getX() - enemy.getX();
			int dy = player.getY() - enemy.getY();
			
			if(dx >= dy) {
				if(dx >= 0)	return GameObject.RIGHT;
				else		return GameObject.LEFT;
			} else {
				if(dy >= 0) return GameObject.DOWN;
				else		return GameObject.UP;
			}
		}
		
		return -1;
	}

	public static int greedyWalk(Agent e) {
		
		if(e instanceof IntelligentEnemy) {
			
			GameObject player = e.getEnvironment().getPlayer();
			GameObject enemy  = (GameObject) e;
		}
		
		return -1;
	}
}
