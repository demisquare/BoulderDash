//AUTORE: Davide Caligiuri
package model;

import audio.Music;

public class Player extends GameObject implements Living {

// 	metodo semplice per aggiungere un delay al movimento
// 	ATTENZIONE: e' dipendente dal framerate
	private int movingCounter;
// 	metodo semplice per aggiungere un delay sulla spinta delle rocce
// 	ATTENZIONE: e' dipendente dal framerate
	private int pushRockCounter;
// 	conta il numero di diamanti raccolti
	int diamondCount;
// 	variabile inutile, viene usata per le animazioni ma si puo' sostituire
	private int speed;

	private int lastDir;
	
	protected int lifes;

	public Player(int x, int y, int s) {
		super(x, y);

		diamondCount = 0;
		pushRockCounter = 0;
		movingCounter = 0;
		speed = s;
		lifes = 3;
		lastDir = -1;
	}

	@Override public boolean update() { return false; }
	
	@Override
	public boolean update(int dir) {
		
		int i = (x + dmap[dir][0]);
		int j = (y + dmap[dir][1]);

		GameObject g = map.getTile(i, j);

		if (g instanceof Diamond) {

			++movingCounter;
			pushRockCounter = 0;
			if (movingCounter >= 1) {
				movingCounter = 0;
				if (g.destroy()) {
					
					++diamondCount;
					Music.playTone("diamond");
					
					return move(dir);
				}
			}
			return false;

		} else if (g instanceof Ground) {

			//System.out.println("scava...");
			++movingCounter;
			pushRockCounter = 0;
			if(movingCounter >= 1) {
				movingCounter = 0;
				if(g.destroy()) {
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
		} else if (g instanceof Door && diamondCount >= map.getNumDiamonds() / 1) {

			++movingCounter;
			pushRockCounter = 0;
			if (movingCounter >= 1) {
				movingCounter = 0;
				return move(dir);
			}
			return false;
		
		} else if (g instanceof Enemy) {
			
			moved = true;
			respawn();
			return true;
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

	@Override
	protected boolean move(int dir) {
		
		int i = x + dmap[dir][0];
		int j = y + dmap[dir][1];
	
		if (!(i < 0 || i >= GameMap.dimX) && !(j < 0 || j >= GameMap.dimY)) {
	
			if (map.getTile(i, j) instanceof EmptyBlock) {
	
				moved = true;
				swap(i, j);	
				return true;
	
			} else if (map.getTile(i, j) instanceof Door) {
	
				moved = true;
				map.setWinCon(true);
				destroy();
				return true;
	
			}
		}
			
		moved = false;
		return moved;
	}
	
	public void respawn() {	
		
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		};
		
		if(lifes > 1) {
			
			respawned = true;
			swap(3, 2);
			--lifes;
			//inserisci suono di sconfitta
			
		} else {
			moved = false;
			lifes = 0;
			destroy();
		}
	}

	public int getLifes() 						{ return lifes; }

	public int getLastDir() 					{ return lastDir; }
	public void setLastDir(int lastDir) 		{ this.lastDir = lastDir; }

	public int getSpeed() 						{ return speed; }
}
