package logic;

public class Diamond extends Block implements Destructible, Gravity{

	public Diamond(int x, int y) {
		super(x, y);
		loadSprite("diamond");
	}

	@Override
	public void toDo(){

	}
}
