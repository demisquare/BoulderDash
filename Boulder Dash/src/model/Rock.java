package model;

import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;

//TODO
public class Rock extends GameObject implements Sliding {
	
	public Rock(int x, int y) {
		super(x, y);
	}

	@Override
	protected boolean fall() {
		
		try {
			
			if(map.getTile(x, y+1) instanceof Living) {
				
				if(isFalling) {
					if(map.getTile(x, y+1) instanceof Player) {

						ConcurrentHashMap<Integer, GameObject> temp = map.getEmptyBlocksMap();
						GameObject g = temp.get(Collections.min(temp.keySet()));
						map.getTile(x, y+1).swap(g.getX(), g.getY());
						
						isFalling = false;
					
					} else if(map.getTile(x, y+1) instanceof Enemy) {
						map.getTile(x, y+1).destroy();
						isFalling = false;
					
					}
					return true;
					
				} else if(map.getTile(x, y-1) instanceof Rock) {
					
					if(map.getTile(x, y+1) instanceof Player) {

						ConcurrentHashMap<Integer, GameObject> temp = map.getEmptyBlocksMap();
						GameObject g = temp.get(Collections.min(temp.keySet()));
						map.getTile(x, y+1).swap(g.getX(), g.getY());
						
					} else if(map.getTile(x, y+1) instanceof Enemy) {
						map.getTile(x, y+1).destroy();
					}
					
					return true;
					
				}
			}  else if(map.getTile(x, y+1) instanceof Sliding) {
				
				if(map.getTile(x+1, y+1) instanceof EmptyBlock
						&& (map.getTile(x+1, y) instanceof EmptyBlock 
								|| map.getTile(x+1, y) instanceof Living)) {

					isFalling = true;
					
					if(map.getTile(x+1, y) instanceof Player) {
						
						ConcurrentHashMap<Integer, GameObject> temp = map.getEmptyBlocksMap();
						GameObject g = temp.get(Collections.min(temp.keySet()));
						map.getTile(x+1, y).swap(g.getX(), g.getY());
						
					} else if(map.getTile(x+1, y) instanceof Enemy) {
						map.getTile(x+1, y).destroy();
					}
					
					swap(x+1, y+1);
					return true;
					
				} else if(map.getTile(x-1, y+1) instanceof EmptyBlock 
						&& (map.getTile(x-1, y) instanceof EmptyBlock
								|| map.getTile(x-1, y) instanceof Living)) {

					isFalling = true;
					
					if(map.getTile(x+1, y) instanceof Player) {
						
						ConcurrentHashMap<Integer, GameObject> temp = map.getEmptyBlocksMap();
						GameObject g = temp.get(Collections.min(temp.keySet()));
						map.getTile(x+1, y).swap(g.getX(), g.getY());
						
					} else if(map.getTile(x+1, y) instanceof Enemy) {
						map.getTile(x+1, y).destroy();
					}
					
					swap(x-1, y+1);
					return true;
				}
			}
			
		} catch(NullPointerException e) {
			e.printStackTrace();
		}
		
		return super.fall();
	}
	
	@Override
	public boolean update() {
		return fall();
	}

	
	@Override
	public boolean update(int dir) {
		// TODO Auto-generated method stub
		return false;
	}
}
