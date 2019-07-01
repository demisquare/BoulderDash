//AUTORE: Davide Caligiuri
package model;

import java.util.Random;

public class Enemy extends GameObject implements Living {
	
	protected Random r;
	protected int delayMovement;
	protected int speed;
	protected int lastDir;
	
	public Enemy(int x, int y, int s) {
		
		super(x, y);
		speed = s;
		r = new Random();
		lastDir = 0;
		delayMovement = 0;
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
	protected boolean move(int dir) {
		
		int i = x + dmap[dir][0];
		int j = y + dmap[dir][1];

		if(!(i < 0 || i >= GameMap.dimX) && !(j < 0 || j >= GameMap.dimY)) {
				
			if(map.getTile(i, j) instanceof Player) {
				
				((Player)map.getTile(i, j)).respawn();
					
				return true;
			}
		}
			
		return super.move(dir);
	}

	protected boolean calculateDirection() {
		lastDir = r.nextInt(4);
		return true;
	}
	
	public int getSpeed() 	{ return speed; }
	public int getLastDir() { return lastDir; }
}
