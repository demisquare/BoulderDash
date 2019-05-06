package logic;

public class Ground extends Block implements Destructible {

	public Ground(int x, int y) {
		super(x, y);
		sprite = spritesheet.getSprite(0, 0);
		
	}

	@Override
	private void destructible() {
		
	}

	//gli aggiornamenti previsti dalle varie interfacce vengnono richiamati tramite update, evitando di dover castare
	@Override
    public abstract void update(){
		destructible();
	}

	@Override
	public abstract byte getType(){
		return 2;
	}
}
