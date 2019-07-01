package model;

import java.util.Random;

import menu.Options;

//TODO
public class Enemy extends GameObject implements Living {
	
	//manca anche qui un delay sul movimento: con un counter oppure usando ScheduledExecutorService?
	protected int delayMovement;
	
	protected Random r;
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
	protected boolean move(int dir) {
		
		int i = x + dmap[dir][0];
		int j = y + dmap[dir][1];
//		int x1 = (x+j >= 0 && x+j < map.dimX) ? x+j : -1;
//		int x2 = (x-j >= 0 && x-j < map.dimX) ? x-j : -1;
//		int y1 = (y+i >= 0 && y+i < map.dimY) ? y+i : -1;
//		int y2 = (y-i >= 0 && y-i < map.dimY) ? y-i : -1;
/*		
		if((x1 != -1 && y1 != -1 && !(map.getTile(x1, y1) instanceof EmptyBlock)) ||
		   (x1 != -1 && y2 != -1 && !(map.getTile(x1, y2) instanceof EmptyBlock)) ||
		   (x2 != -1 && y1 != -1 && !(map.getTile(x2, y1) instanceof EmptyBlock)) ||
		   (x2 != -1 && y2 != -1 && !(map.getTile(x2, y2) instanceof EmptyBlock))) {
*/		
			if(!(i < 0 || i >= map.dimX) && !(j < 0 || j >= map.dimY)) {
				
				if(map.getTile(i, j) instanceof Player) {
					
					//map.getTile(i, j).destroy();
					//swap(i, j);
					((Player)map.getTile(i, j)).respawn();
					
					return true;
				}
			}
			
			return super.move(dir);
		//}
		
		//return false;
	}

	protected boolean calculateDirection() {
		lastDir = r.nextInt(4);
		return true;
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
	
	public int getSpeed() 	{ return speed; }
	public int getLastDir() { return lastDir; }
}
