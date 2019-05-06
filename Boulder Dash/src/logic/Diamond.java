package logic;

import java.util.Random;

public class Diamond extends Block implements Destructible, Gravity{

	public Diamond(int x, int y) {
		super(x, y);
		Random r = new Random();
		sprite = spritesheet.getSprite(3, r.nextInt(3));
	}

	@Override
	private void destructible(){

	}

	@Override
	private void gravity(){

	}

	//gli aggiornamenti previsti dalle varie interfacce vengnono richiamati tramite update, evitando di dover castare
	@Override
    public abstract void update(){
		destructible();
		gravity();
	}

	@Override
	public abstract byte getType(){
		return DIAMOND;
	}
}
