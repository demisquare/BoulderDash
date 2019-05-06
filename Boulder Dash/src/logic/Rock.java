package logic;

import java.util.Random;

public class Rock extends Block implements Gravity {

	public Rock(int x, int y) {
		super(x, y);
		
		Random r = new Random();
		sprite = spritesheet.getSprite(r.nextInt(3), r.nextInt(3));
	}

	@Override
	private void gravity(){

	}

	//gli aggiornamenti previsti dalle varie interfacce vengnono richiamati tramite update, evitando di dover castare
	@Override
    public abstract void update(){
		gravity();
	}

	@Override
	public abstract byte getType(){
		return types.ROCK;
	}
}
