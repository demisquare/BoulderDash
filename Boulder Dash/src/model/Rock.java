package model;

//TODO
public class Rock extends GameObject implements Sliding {
	
	public Rock(int x, int y) {
		super(x, y);
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
