package logic;

public class Ground extends Block implements Destructible {

	public Ground(int x, int y) {
		super(x, y);
		sprite = spritesheet.getSprite(0, 0);	
	}

	@Override
	public void destroy(boolean condition) {
		try{
			if(condition)
				map.set(x, y, emptyTile);
		} catch(NullPointerException e){
			e.printStackTrace();
		}
	}

	//gli aggiornamenti previsti dalle varie interfacce vengnono richiamati tramite update, evitando di dover castare
	@Override
 	public void update(){
		destroy(false);
	}

	@Override
	public byte getType(){
		return GROUND;
	}
}
