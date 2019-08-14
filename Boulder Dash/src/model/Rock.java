//AUTORE: Davide Caligiuri
package model;

import audio.Music;
import it.unical.mat.embasp.languages.Id;

@Id("rock")
public class Rock extends GameObject implements Sliding {
	
	public Rock(int x, int y) { super(x, y); }
	
	@Override
	public boolean update() { 
		if(fall()) {
			Music.playTone("falling_rock");
			return true;
		}
		return false; 
	}
	
	@Override public boolean update(int dir)	{ return false; }

	@Override
	protected boolean fall() {
		
		try {		
			if(map.getTile(x, y+1) instanceof Living) {
				
				if(isFalling) {
					if(map.getTile(x, y+1) instanceof Player) {

						((Player)map.getTile(x, y+1)).respawn();
						isFalling = false;
					
					} else if(map.getTile(x, y+1) instanceof Enemy) {
						
						map.getTile(x, y+1).destroy();
						isFalling = false;
					
					}
					return true;
					
				} else if(map.getTile(x, y-1) instanceof Rock) {
					
					if(map.getTile(x, y+1) instanceof Player) {

						((Player)map.getTile(x, y+1)).respawn();
						isFalling = false;
						
					} else if(map.getTile(x, y+1) instanceof Enemy) {
						
						map.getTile(x, y+1).destroy();
						isFalling = false;
					}
					
					return true;
					
				}
			}  else if(map.getTile(x, y+1) instanceof Sliding) {
				
				if(map.getTile(x+1, y+1) instanceof EmptyBlock
						&& (map.getTile(x+1, y) instanceof EmptyBlock 
								|| map.getTile(x+1, y) instanceof Living)) {

					if(isFalling) {
						/*
						if(map.getTile(x+1, y) instanceof Player) {
						
							((Player)map.getTile(x+1, y)).respawn();
							isFalling = false;
					
						} else if(map.getTile(x+1, y) instanceof Enemy) {
						
							map.getTile(x+1, y).destroy();
							isFalling = false;
						}
					*/} else {
						isFalling = true;
						swap(x+1, y+1);
						return true;
					}
				} else if(map.getTile(x-1, y+1) instanceof EmptyBlock 
						&& (map.getTile(x-1, y) instanceof EmptyBlock
								|| map.getTile(x-1, y) instanceof Living)) {

					if(isFalling) {
						if(map.getTile(x+1, y) instanceof Player) {
						
							((Player)map.getTile(x+1, y)).respawn();
							isFalling = false;
						
						} else if(map.getTile(x+1, y) instanceof Enemy) {
							map.getTile(x+1, y).destroy();
							isFalling = false;
						}
					} else {
						isFalling = true;
						swap(x-1, y+1);
						return true;
					}
				}
			}
			
		} catch(NullPointerException e) {
			e.printStackTrace();
		}
		
		return super.fall();
	}
}
