package model;

//TODO
public class EmptyBlock extends GameObject {
	
	public EmptyBlock(int x, int y) {
		super(x, y);
	}
	
	@Override
	public boolean update() {
		return false;
	}

	@Override
	public boolean update(int dir) {
		// TODO Auto-generated method stub
		return false;
	}
}
