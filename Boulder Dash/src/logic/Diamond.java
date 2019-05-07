package logic;

import java.util.Random;

public class Diamond extends Block implements Destructible, Gravity{

	private static Random r = new Random();	

	public Diamond(int x, int y) {
		super(x, y);
		sprite = spritesheet.getSprite(3, r.nextInt(3));
	}

	@Override
	private void destroy(boolean condition){
		try{
			if(condition)
				mapRef[x][y] = emptyTile;
		} catch(NullPointerException e){
			e.printStackTrace();
		}
	}

	@Override
	private void gravity(){

	}

	//gli aggiornamenti previsti dalle varie interfacce vengnono richiamati tramite update, evitando di dover castare
	@Override
  	public abstract void update(){
		destroy(false);
		gravity();
	}

	@Override
	public abstract byte getType(){
		return DIAMOND;
	}
}
