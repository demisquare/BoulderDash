//AUTORE: Davide Caligiuri
package model;

import audio.Music;
import it.unical.mat.embasp.languages.Id;

@Id("diamond")
public class Diamond extends GameObject implements Sliding {
	
	public Diamond(int x, int y) { super(x, y); }

	@Override public boolean update() 			{ return fall(); }
	@Override public boolean update(int dir) 	{ return false; }

	@Override
	protected boolean fall() {
		
		if(!(y+1 < 0 || y+1 >= GameMap.dimY)) {
			
			try { 
				if(map.getTile(x, y+1) instanceof EmptyBlock) {

					isFalling = true;
					swap(x, y+1);
					return true;
				
				} else if(map.getTile(x, y+1) instanceof Player) {
					
					Music.playTone("diamond");
					((Player) map.getTile(x, y+1)).diamondCount += 1;
					isFalling = false;
					destroy();
					return true;
				}
				
			} catch(NullPointerException e) {
				e.printStackTrace();
			}
		}
		
		isFalling = false;
		return false;
	}
}
