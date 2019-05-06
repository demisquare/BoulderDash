package logic;

interface Behaviour {

	public void behave(); 
}

interface Destructible {

	//definita per gli oggetti che possono essere distrutti durante la partita
	//usata da: Ground, Diamond
	static final EmptyBlock emptyTile = new EmptyBlock();
	public abstract void destroy(boolean condition);
}

interface Gravity {

	//definita per gli oggetti che cadono se il tile sottostante è vuoto
	//usata da: Rock, Diamond, Player
	public abstract void gravity();
}