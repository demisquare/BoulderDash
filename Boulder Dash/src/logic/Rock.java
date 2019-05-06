package logic;

import java.util.Random;

public class Rock extends Block implements Gravity {

	private static Random r = new Random();

	public Rock(int x, int y) {
		super(x, y);
		sprite = spritesheet.getSprite(r.nextInt(3), r.nextInt(3));
	}

	@Override
	private void gravity(){

	}

	@Override
    public abstract void update(){
		gravity();
	}

	@Override
	public abstract byte getType(){
		return ROCK;
	}
}
