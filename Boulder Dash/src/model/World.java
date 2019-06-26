package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import view.Sprite;

//classe che contiene sia il player che la mappa
public class World implements Runnable {

	//contiene la "matrice logica" del gioco (l'implementazione non � una matrice)
	GameMap map;	
	//true se il world � stato aggiornato
	private boolean hasChanged;

	//dimensione grafica...
	int width  = 0;	
	int height = 0;
	//	Graph<Integer, Integer> g= new SimpleGraph(int.class);

	private int FPS;
	
	// costruttore di default
	public World(int FPS) {
		
		//sostituire con il filename
		map = new GameMap("levelmap");
		
		this.FPS = FPS;
		//default a true (per evitare un aggiornamento immediato)
		hasChanged = true;
		
		//dimensione grafica...
		width = map.getDimX() * Sprite.TILE_SIZE;
		height = map.getDimY() * Sprite.TILE_SIZE;
		
		//da definire la posizione di partenza (e il criterio di scelta)
		//stack.add((2*map.getDimX())+10);
	}
	
	// getter e setter
	
	public GameObject getPlayer() {
		return map.getPlayer();
	}
	
	public GameObject getHost() {
		return map.getHost();
	}

	public GameMap getMap() {
		return map;
	}

	public void setMap(GameMap map) {
		this.map = map;
	}
	
	public ArrayList<GameObject> getEnemies() {
		return map.getEnemies();
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public boolean isChanged() {
		return hasChanged;
	}
	
	public void reset() {

		Collection<GameObject> temp = map.getBlocks().values();
		for(GameObject e : temp)
			e.processed = false;
		
		temp = map.getDiamondsMap().values();
		for(GameObject e : temp)
			e.processed = false;
		
		temp = map.getEmptyBlocksMap().values();
		for(GameObject e : temp)
			e.processed = false;
		
		temp = map.getGroundMap().values();
		for(GameObject e : temp)
			e.processed = false;
		
		temp = map.getRocksMap().values();
		for(GameObject e : temp)
			e.processed = false;
	
		hasChanged = false;
	}

	//questa funzione dovrebbe in automatico aggiornare gli stati di 
	//Player, Enemy, etc, ogni tick del timer
	public void update() {
		
		//aggiorna gli stati di ogni casella
		try {
			Collection<GameObject> temp = map.getBlocks().values();
			for(GameObject e : temp)
				if(e.processed == false)
					e.update();
			
			temp = map.getDiamondsMap().values();
			for(GameObject e : temp)
				if(e.processed == false)
					e.update();
			
			temp = map.getEmptyBlocksMap().values();
			for(GameObject e : temp)
				if(e.processed == false)
					e.update();
			
			temp = map.getGroundMap().values();
			for(GameObject e : temp)
				if(e.processed == false)
					e.update();
			
			temp = map.getRocksMap().values();
			for(GameObject e : temp)
				if(e.processed == false)
					e.update();
			
		} catch(ConcurrentModificationException e1) {
			e1.printStackTrace();
			
		}
		
		//flag di vittoria qui?
		hasChanged = true;
	}
	
	@Override
	public void run() {
	
		while(true) {
			try {
				
				synchronized(this) {
					update();
				}
				
				Thread.sleep(200/FPS + 1);
				
			} catch (InterruptedException e) {
				return;
			}
		}

	}

	public void setChanged(boolean b) {
		hasChanged = b;
	}
}
