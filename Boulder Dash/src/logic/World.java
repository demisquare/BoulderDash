package logic;


//classe che contiene sia il player che la mappa
public class World {

	Player player;
	
	Map map;

	
	// costruttore di default
	public World() {
		
		player = new Player(10, 10, 3);
		//sostituire con il filename
		map= new Map("filename"); 
	}
	
	// getter e setter
	
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}
	
	
		
}
