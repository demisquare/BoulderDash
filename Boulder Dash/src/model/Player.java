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
			
			if(g.destroy()) {
				return move(dir);
			}
		
		} else if(g instanceof Rock && (dir == LEFT || dir == RIGHT)) {
			
			if(g.move(dir)) {
				return move(dir);
			}
		}
		
		return super.move(dir);
	}

	public int getSpeed() { return speed; }
}
