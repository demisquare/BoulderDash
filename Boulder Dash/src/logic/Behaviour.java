package logic;

public interface Behaviour {

	private void behave(); 
}

public interface Destructible {

	//definita per gli oggetti che possono essere distrutti durante la partita
	//usata da: Ground, Diamond
	private void destructible();
}

public interface Gravity {

	//definita per gli oggetti che cadono se il tile sottostante è vuoto
	//usata da: Rock, Diamond, Player
	private void gravity();
}