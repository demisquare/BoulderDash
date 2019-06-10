package model;

//TODO
public class Door extends GameObject implements GameTraversable {
	
	public Door(int x, int y) {
		super(x, y);
	}
	
	@Override
	public boolean update() {
		return false;
	}

	@Override
	public boolean update(int dir) {
		return false;
	}
}
