package model;

import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;

public class Player extends GameObject implements Living {

	// metodo semplice per aggiungere un delay al movimento
	// ATTENZIONE: � dipendente dal framerate (non dovrebbe)
	private int movingCounter;
	// metodo semplice per aggiungere un delay sulla spinta delle rocce
	// ATTENZIONE: � dipendente dal framerate (non dovrebbe)
	private int pushRockCounter;
	// conta il numero di diamanti raccolti
	int diamondCount;
	// variabile inutile, viene usata per le animazioni ma si pu� sostituire
	private int speed;
	
	protected int lifes;
	
	public int getLifes() {
		return lifes;
	}

	private int lastDir;

	public int getLastDir() {
		return lastDir;
	}

	public void setLastDir(int lastDir) {
		this.lastDir = lastDir;
	}

	public Player(int x, int y, int s) {
		super(x, y);

		diamondCount = 0;
		pushRockCounter = 0;
		movingCounter = 0;
		speed = s;
		lifes = 3;
		lastDir = -1;
	}

	@Override
	public boolean update() {
		return false;
	}

	public int getSpeed() {
		return speed;
	}
	
	@Override
	public boolean update(int dir) {
		
		int i = (x + dmap[dir][0]);
		int j = (y + dmap[dir][1]);

		GameObject g = map.getTile(i, j);

		if (g instanceof Diamond) {

			++diamondCount;
			++movingCounter;
			pushRockCounter = 0;
			if (movingCounter >= 1) {
				movingCounter = 0;
				if (g.destroy()) {
					return move(dir);
				}
			}
			return false;

		} else if (g instanceof Ground) {

			//System.out.println("scava...");
			++movingCounter;
			pushRockCounter = 0;
			if (movingCounter >= 1) {
				movingCounter = 0;
				if (g.destroy()) {
					return move(dir);
				}
			}
			return false;

		} else if (g instanceof Rock && (dir == LEFT || dir == RIGHT)) {

			++pushRockCounter;
			movingCounter = 0;
			if (pushRockCounter >= 5) {
				pushRockCounter = 0;
				movingCounter = 1;
				if (g.move(dir)) {
					return move(dir);
				}
			}
			return false;

			// variare il parametro da 1 in su per semplificare (meno diamanti da
			// raccogliere)
		} else if (g instanceof Door && diamondCount >= map.numDiamonds / 1) {

			++movingCounter;
			pushRockCounter = 0;
			if (movingCounter >= 1) {
				movingCounter = 0;
				return move(dir);
			}
			return false;
		}

		// this is for EmptyBlock
		++movingCounter;
		pushRockCounter = 0;
		if (movingCounter >= 1) {
			movingCounter = 0;
			return super.move(dir);
		}
		
		return false;
	}
	
	public void respawn() {	
		
		if(lifes > 1) {
			respawned = true;
			
			ConcurrentHashMap<Integer, GameObject> temp = map.getEmptyBlocksMap();
			GameObject g = temp.get(Collections.min(temp.keySet()));
			swap(g.getX(), g.getY());
			--lifes;
		
		} else {
			lifes = 0;
			destroy();
		}
	}
	
	@Override
	protected boolean move(int dir) {
		
			int i = x + dmap[dir][0];
			int j = y + dmap[dir][1];
	
			if (!(i < 0 || i >= map.dimX) && !(j < 0 || j >= map.dimY)) {
	
				if (map.getTile(i, j) instanceof EmptyBlock) {
	
					moved = true;
					//System.out.println("si muove...");
					swap(i, j);
						
					return true;
	
				} else if (map.getTile(i, j) instanceof Door) {
	
					moved = true;
					map.winCon = true;
	
					//System.out.println("VITTORIA");
	
					destroy();
					
					return true;
	
				} else if (map.getTile(i, j) instanceof Enemy) {
	
					moved = true;
					
					respawn();
					
					return true;
				}
			}
			
			moved = false;
			return moved;
		}
}
