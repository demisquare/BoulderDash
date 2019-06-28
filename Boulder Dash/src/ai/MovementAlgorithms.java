package ai;

import java.util.Random;

import model.GameObject;

//TODO
class MovementAlgorithms {
	
	private MovementAlgorithms() {}
	
	public static int manatthanDistance(Agent e) {
	
		if(e instanceof IntelligentEnemy) {
			
			GameObject player = e.getEnvironment().getPlayer();
			GameObject enemy  = (GameObject) e;
			
			double min = e.getEnvironment().getDimX() + e.getEnvironment().getDimY(); 
			int ret = (new Random()).nextInt(4);
			for(int i = 0; i < 4; ++i) {
				
				double temp = 
						Math.sqrt(
								Math.pow(player.getX() - enemy.getX() - GameObject.dmap[i][0], 2) + 
								Math.pow(player.getY() - enemy.getY() - GameObject.dmap[i][1], 2));
				
				if(temp < min) {
					min = temp;
					ret = i;
				}
			}
			
			return ret;
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
