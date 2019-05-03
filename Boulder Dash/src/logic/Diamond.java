package logic;

import java.util.Random;

public class Diamond extends Block implements Destructible, Gravity{

	public Diamond(int x, int y) {
		super(x, y);
		Random r = new Random();
		sprite = spritesheet.getSprite(3, r.nextInt(3));
	}

	@Override
	public void toDo(){

	}
}
