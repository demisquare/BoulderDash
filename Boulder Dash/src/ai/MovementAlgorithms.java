package ai;

import java.util.Random;

import model.EmptyBlock;
import model.GameObject;

//TODO
class MovementAlgorithms {
	
	private MovementAlgorithms() {}
	
	private static double norm2D(double dx, double dy, double p) {
		return 
			Math.pow(Math.pow(Math.abs(dx), p) + Math.pow(Math.abs(dy), p), 1d/p);
	}
	
	public static int greedyPath(Agent e) {
	
		if(e instanceof IntelligentEnemy) {
			
			GameObject player = e.getEnvironment().getPlayer();
			GameObject enemy  = (GameObject) e;
			
			double min = e.getEnvironment().getDimX() + e.getEnvironment().getDimY(); 
			int ret    = (new Random()).nextInt(4);
			for(int i = 0; i < 4; ++i) {
				
				int new_x = enemy.getX() + GameObject.dmap[i][0];
				int new_y = enemy.getY() + GameObject.dmap[i][1];
				
				double temp = norm2D(player.getX()-new_x, player.getY()-new_y, 2);
				
				if(temp < min && e.getEnvironment().getTile(new_x, new_y) instanceof EmptyBlock) {
					
					min = temp;
					ret = i;
				}
			}
			
			return ret;
		}
		
		return 0;
	}

	public static int optimalPath(Agent e) {
		
		if(e instanceof IntelligentEnemy) {
			
			GameObject player = e.getEnvironment().getPlayer();
			GameObject enemy  = (GameObject) e;
		}
		
		return 0;
	}
}
