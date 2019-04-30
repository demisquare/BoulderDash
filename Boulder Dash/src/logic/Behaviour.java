package logic;

public interface Behaviour {

	public void behave(); 
}

public interface Destructible {

	//definita per gli oggetti che possono essere distrutti durante la partita
	//usata da: Ground, Diamond
	public void toDo();
}

public interface Gravity {

	//definita per gli oggetti che cadono se il tile sottostante è vuoto
	//usata da: Rock, Diamond, Player
	public void toDo();
}