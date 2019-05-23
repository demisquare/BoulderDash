package logic;

import java.util.ArrayList;

import graphics.Sprite;

//classe che contiene sia il player che la mappa
public class World {

	Player player;
	ArrayList<Enemy> enemies;
	Map map;	
	
	//dimensione grafica...
	int width = 0;
	int height = 0;
	
	// costruttore di default
	public World() {

		//sostituire con il filename
		map = new Map("levelmap");
		
		//dimensione grafica...
		width = map.getDimX() * Sprite.TILE_SIZE;
		height = map.getDimY() * Sprite.TILE_SIZE;
		
		//da definire la posizione di partenza (e il criterio di scelta)
		player = new Player(15, 15, 1);
		enemies = new ArrayList<Enemy>();
		enemies.add(new Enemy(2, 10, 1));
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
	
	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	//questa funzione dovrebbe in automatico aggiornare gli stati di 
	//Player, Enemy, etc, ogni tick del timer
	public void update() {
		
		for(int i = 0; i < map.dimX; ++i)
		for(int j = 0; j < map.dimY; ++j)
			map.getTile(i, j).update(false);
		for(int i = 0; i < enemies.size(); ++i)
		{
			enemies.get(i).ls.movePose(Living.DOWN);
			enemies.get(i).move(Living.DOWN);
			
			enemies.get(i).ls.getAnimation().update();
		}
			
	
		//flag di vittoria qui?
	}
}
