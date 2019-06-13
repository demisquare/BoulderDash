package model;

public class Player extends GameObject implements Living {

	private int diamondCount;
	private int speed;
	
	public Player(int x, int y, int s) {
		super(x, y);
		diamondCount = 0;
		speed = s;
	}
	
	@Override
	public boolean update() {
		return false;
	}

	@Override
	protected boolean move(int dir) {
		int i = (x + dmap[dir][0]);
		int j = (y + dmap[dir][1]);
		
		if(!(i < 0 || i >= map.dimX) && !(j < 0 || j >= map.dimY)) {
		
			if(map.getTile(i, j) instanceof EmptyBlock) {
				
				System.out.println("si muove...");
				swap(i, j);
				return true;
			
			} else if(map.getTile(i, j) instanceof Door) {
				
				map.setTile(x, y, new EmptyBlock(x, y));
				x = i;
				y = j;
				map.setTile(x, y, this);
				
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
			if(g.destroy()) {
				return move(dir);
			}
		
		} else if(g instanceof Ground) {
			
			System.out.println("scava...");
			if(g.destroy()) {
				return move(dir);
			}
		
		} else if(g instanceof Rock && (dir == LEFT || dir == RIGHT)) {
			
			if(g.move(dir)) {
				return move(dir);
			}
		
			//variare il parametro da 1 in su per semplificare (meno diamanti da raccogliere)
		} else if(g instanceof Door && diamondCount == map.numDiamonds/1) {
			
			return move(dir);
		}
		
		return super.move(dir);
	}

	public int getSpeed() { return speed; }
}
