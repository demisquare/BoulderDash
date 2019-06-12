package model;

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
					
					map.getTile(x, y+1).destroy();
					isFalling = false;
					swap(x, y+1);
					return true;
				
				} else if(map.getTile(x, y-1) instanceof Rock) {
					
					map.getTile(x, y+1).destroy();
					swap(x, y+1);
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
