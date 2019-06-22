package model;

//TODO
public class Diamond extends GameObject implements Sliding {
	
	public Diamond(int x, int y) {
		super(x, y);
	}
	
	@Override
	protected boolean fall() {
		
		if(!(y+1 < 0 || y+1 >= map.dimY)) {
			
			try { 
				if(map.getTile(x, y+1) instanceof EmptyBlock) {

					isFalling = true;
					swap(x, y+1);
					return true;
				
				} else if(map.getTile(x, y+1) instanceof Player) {
					
					++((Player) map.getTile(x, y+1)).diamondCount;
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
