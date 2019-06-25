package model;

import menu.Options;
import network.MessageHandler;
import network.packet.PacketDie;
import network.packet.PacketMove;

public class Player extends GameObject implements Living {
	
	//metodo semplice per aggiungere un delay al movimento
	//ATTENZIONE: � dipendente dal framerate (non dovrebbe)
	private int movingCounter;
	//metodo semplice per aggiungere un delay sulla spinta delle rocce
	//ATTENZIONE: � dipendente dal framerate (non dovrebbe)
	private int pushRockCounter;
	//conta il numero di diamanti raccolti
	int diamondCount;
	//variabile inutile, viene usata per le animazioni ma si pu� sostituire
	private int speed;
	
	public Player(int x, int y, int s) {
		super(x, y);
		
		diamondCount = 0;
		pushRockCounter = 0;
		movingCounter = 0;
		speed = s;
	}
	
	@Override
	public boolean update() {
		return false;
	}

	@Override
	protected boolean move(int dir) {
		int i = x + dmap[dir][0];
		int j = y + dmap[dir][1];
		
		if(!(i < 0 || i >= map.dimX) && !(j < 0 || j >= map.dimY)) {
		
			if(map.getTile(i, j) instanceof EmptyBlock) {
				
				System.out.println("si muove...");
				swap(i, j);
				
				if(Options.multiplayer)
					MessageHandler.sendObject(new PacketMove(this.hashCode(), x, y, dir));
				
				return true;
			
			} else if(map.getTile(i, j) instanceof Door) {
				
				System.out.println("VITTORIA");
				
				destroy();
				
				return true;
			
			} else if(map.getTile(i, j) instanceof Enemy) {
				
				destroy();
				
				if(Options.multiplayer)
					MessageHandler.sendObject(new PacketDie(this.hashCode(), x, y));
				
				return true;
			}
			
		}
		
		return false;
	}
	
	@Override
	public boolean update(int dir) {
		
		int i = (x + dmap[dir][0]);
		int j = (y + dmap[dir][1]);
		
		GameObject g = map.getTile(i, j);
		
		if(g instanceof Diamond) {
			
			++diamondCount;
			++movingCounter;
			pushRockCounter = 0;
			
			if(movingCounter == 2) {
				
				movingCounter = 0;
				if(g.destroy()) {
					return move(dir);
				}
			}
			return false;
		
		} else if(g instanceof Ground) {
			
			System.out.println("scava...");
			++movingCounter;
			pushRockCounter = 0;
			
			if(movingCounter == 2) {
				
				movingCounter = 0;
				if(g.destroy()) {
					return move(dir);
				}
			}
			return false;
		
		} else if(g instanceof Rock && (dir == LEFT || dir == RIGHT)) {
			
			++pushRockCounter;
			movingCounter = 0;
			
			if(pushRockCounter == 6) {
				
				pushRockCounter = 0;
				movingCounter = 1;
				if(g.move(dir)) {
					return move(dir);
				}
			}
			return false;
			
			//variare il parametro da 1 in su per semplificare (meno diamanti da raccogliere)
		} else if(g instanceof Door && diamondCount >= map.numDiamonds/1) {
			
			++movingCounter;
			pushRockCounter = 0;
			
			if(movingCounter == 2) {
				
				movingCounter = 0;
				return move(dir);
			}
			return false;
		}
		
		//this is for EmptyBlock
		
		++movingCounter;
		pushRockCounter = 0;
		
		if(movingCounter == 2) {
			
			movingCounter = 0;
			return super.move(dir);
		}
		return false;
	}

	public int getSpeed() { return speed; }
}
