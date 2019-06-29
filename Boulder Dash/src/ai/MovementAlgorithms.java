package ai;

import java.util.Random;

import model.EmptyBlock;
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
						Math.abs(player.getX() - enemy.getX() - GameObject.dmap[i][0]) +
						Math.abs(player.getY() - enemy.getY() - GameObject.dmap[i][1]);
				
				if(temp < min && 
						e.getEnvironment().getTile(
								enemy.getX() + GameObject.dmap[i][0], 
								enemy.getY() + GameObject.dmap[i][1]) 
						instanceof EmptyBlock) {
					
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
