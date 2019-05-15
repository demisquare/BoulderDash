package logic;

import java.util.ArrayList;

//classe che contiene sia il player che la mappa
public class World {

	Player player;
	ArrayList<Enemy> enemies;
	Map map;
	
	// costruttore di default
	public World() {
		
		//da definire la posizione di partenza (e il criterio di scelta)
		player = new Player(10, 10, 5);
		enemies = new ArrayList<Enemy>();
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
