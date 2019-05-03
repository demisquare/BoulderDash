package logic;

public class Ground extends Block implements Destructible {

	public Ground(int x, int y) {
		super(x, y);
		sprite = spritesheet.getSprite(0, 0);
		
	}

	@Override
	public void toDo() {
		
	}

}
