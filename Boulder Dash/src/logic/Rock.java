package logic;

import java.util.Random;

public class Rock extends Block implements Gravity {

	public Rock(int x, int y) {
		super(x, y);
		
		Random r = new Random();
		sprite = spritesheet.getSprite(r.nextInt(3), r.nextInt(3));
	}

	@Override
	public void toDo(){

	}

}
