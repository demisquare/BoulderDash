package model;

public abstract class GameObject {

	static final char EMPTY_BLOCK = '0';
	static final char WALL	 	  = '1';
	static final char DIAMOND 	  = '2';
	static final char GROUND 	  = '3';
	static final char ROCK 		  = '4';
	static final char DOOR 		  = 'D';
	static final char PLAYER	  = 'P';
	static final char ENEMY		  = 'E';
	
	public static final int DOWN  = 0;
	public static final int LEFT  = 1;
	public static final int RIGHT = 2;
	public static final int UP    = 3;
	
	static final int dmap[][] = { { 0,  1},
			                      {-1,  0},
			                      { 1,  0},
			                      { 0, -1} };
	
	static GameMap map = null;
	
	protected int x;
	protected int y;
	
	protected boolean processed;
	protected boolean dead;
	protected boolean isFalling;
	
	public abstract boolean update();
	public abstract boolean update(int dir);
	
	protected boolean destroy() {
	
		try {
			map.setTile(x, y, new EmptyBlock(x, y));
			processed = true;
			dead = true;
			map.getTile(x, y).processed = true;
			return true;
			
		} catch(NullPointerException e) {
			System.out.println("I'm in destroy()");
			e.printStackTrace();
		
		}
		
		return false;
	}
	
	protected void swap(int i, int j) {
		
		GameObject temp = map.getTile(i, j);
		
		if(!map.getTile(x, y).equals(this)) {
			
			System.out.println("MANNAJA");
		}
		
		map.setTile(x, y, temp);
		
		temp.x = x;
		temp.y = y;
		temp.processed = true;
		
		if(!map.getTile(i, j).equals(temp)) {
			
			System.out.println("AJANNAM");
		}
		
		map.setTile(i, j, this);
		
		x = i;
		y = j;
		processed = true;
		
	}
	
	protected boolean fall() {
		
		if(!(y+1 < 0 || y+1 >= map.dimY)) {
			
			try { 
				if(map.getTile(x, y+1) instanceof EmptyBlock) {

					isFalling = true;
					swap(x, y+1);
					return true;
				
				} else if(map.getTile(x, y+1) instanceof Sliding) {
				
					if(map.getTile(x+1, y+1) instanceof EmptyBlock && map.getTile(x+1, y) instanceof EmptyBlock) {

						isFalling = true;
						swap(x+1, y+1);
						return true;
						
					} else if(map.getTile(x-1, y+1) instanceof EmptyBlock && map.getTile(x-1, y) instanceof EmptyBlock) {

						isFalling = true;
						swap(x-1, y+1);
						return true;
					
					}
				}
			} catch(NullPointerException e) {
				e.printStackTrace();
			}
		}
		
		isFalling = false;
		return false;
	}
	
	protected boolean move(int dir) {
		
		int i = (x + dmap[dir][0]);
		int j = (y + dmap[dir][1]);
		
		if(!(i < 0 || i >= map.dimX) && !(j < 0 || j >= map.dimY)) {
		
			if(map.getTile(i, j) instanceof EmptyBlock) {
				
				swap(i, j);
				return true;
			}
		}
		
		return false;
	}

	public GameObject(int x, int y) {
		this.x = x;
		this.y = y;
		processed = false;
		isFalling = false;
		dead = false;
	}
	
	//uso non corretto di un hashCode?
	@Override
	public int hashCode() {
		return x*map.dimX + y;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj.getClass().isInstance(this) && 
				obj.hashCode() == hashCode();
	}
	
	public int getX() 		{ return x;   }	
	public void setX(int x) { this.x = x; }
	
	public int getY() 		{ return y;   }
	public void setY(int y) { this.y = y; }	
	
	public boolean hasChanged()  { return processed; }
	
	public boolean isDead() { return dead; }
}