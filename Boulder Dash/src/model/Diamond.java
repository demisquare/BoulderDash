package model;

//TODO
public class Diamond extends GameObject implements Sliding {
	
	public Diamond(int x, int y) {
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
