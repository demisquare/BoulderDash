package logic;


public class Ground extends Block implements Destructible {

	public Ground(int x, int y) {
		super(x, y);
		loadSprite("ground");
		
	}

	@Override
	public void toDo() {
		
	}

}
