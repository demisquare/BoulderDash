package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import view.Sprite;

//classe che contiene sia il player che la mappa
public class World {

	//contiene la "matrice logica" del gioco (l'implementazione non � una matrice)
	GameMap map;	
	//true se il world � stato aggiornato
	private boolean hasChanged;

	private Thread t;
	
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
		
		t = null;
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
		for(GameObject e : temp) {
			e.clearFlags();
		}
		
		temp = map.getDiamondsMap().values();
		for(GameObject e : temp) {
			e.clearFlags();
		}
		
		temp = map.getEmptyBlocksMap().values();
		for(GameObject e : temp) {
			e.clearFlags();
		}
		
		temp = map.getGroundMap().values();
		for(GameObject e : temp) {
			e.clearFlags();
		}
		
		temp = map.getRocksMap().values();
		for(GameObject e : temp) {
			e.clearFlags();
		}
		
		hasChanged = false;
	}

	//questa funzione dovrebbe in automatico aggiornare gli stati di 
	//Player, Enemy, etc, ogni tick del timer
	public void update() {
		
		if(hasChanged)
			return;
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
	
	public void launchThread() {
		
		if(t != null && t.isAlive())
			t.interrupt();
		
		t = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					synchronized(this) {
						update();
					}
					try {
						Thread.sleep(150);
					} catch (InterruptedException e) {
						return;
					}
				}
			}
		});
		
		t.start();
	}
	
	public void setChanged(boolean b) {
		hasChanged = b;
	}

	public void closeThread() {
		if(t != null && t.isAlive())
			t.interrupt();
	}
}
