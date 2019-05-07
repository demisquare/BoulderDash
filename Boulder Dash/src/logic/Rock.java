package logic;

import java.util.Random;

public class Rock extends Block implements Gravity {

	private static Random r = new Random();

	public Rock(int x, int y) {
		super(x, y);
		sprite = spritesheet.getSprite(r.nextInt(3), r.nextInt(3));
	}

	@Override
	public void gravity(){

	}

	@Override
    public void update(){
		gravity();
	}

	@Override
	public byte getType(){
		return ROCK;
	}
}
