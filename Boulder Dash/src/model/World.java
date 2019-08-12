//AUTORE: Davide Caligiuri
package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.ConcurrentModificationException;

import view.Sprite;

//classe che contiene sia il player che la mappa
public class World {

	//true se il world � stato aggiornato
	private boolean hasChanged;
	private boolean isComplete;
	private Thread t;

	//contiene la "matrice logica" del gioco (l'implementazione non � una matrice)
	GameMap map;		
	//dimensione grafica...
	final int width;	
	final int height;
	
	// costruttore di default
	public World(int stage) {
		
		isComplete = false;
		//sostituire con il filename
		map = new GameMap("level" + 1);
		
		//default a true (per evitare un aggiornamento immediato)
		hasChanged = true;
		
		t = null;
		//dimensione grafica..
		width = map.getDimX() * Sprite.TILE_SIZE;
		height = map.getDimY() * Sprite.TILE_SIZE;
		
		isComplete = true;
	}

//	questa funzione aggiorna in automatico gli stati di 
//	Player, Enemy, etc, ogni tick del timer
	public synchronized void update() {
		
		if(hasChanged) return;
		
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

		hasChanged = true;
	}
		
	public synchronized void reset() {

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
	
//gestione multithreading	
	
	public void launchThread() {
		
		if(t != null && t.isAlive())
			t.interrupt();
		
		t = new Thread(new Runnable() {
			@Override
			public void run() {
				
				while(!isComplete) {
					try {
						Thread.sleep(5);
					} catch(InterruptedException e) {
						e.printStackTrace();
					}
				}
				
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

	public void closeThread() {
		if(t != null && t.isAlive())
			t.interrupt();
	}
	
// getter e setter
	public boolean isComplete() 				{ return isComplete; }
	
	public GameMap getMap() 					{ return map; }
	public void setMap(GameMap map) 			{ this.map = map; }
	
	public boolean getWinCon() 					{ return map.getWinCon(); }
	
	public GameObject getPlayer() 				{ return map.getPlayer(); }	
	public GameObject getHost() 				{ return map.getHost(); }
	public ArrayList<GameObject> getEnemies() 	{ return map.getEnemies(); }
		
	public int getWidth() 						{ return width; }
	public int getHeight() 						{ return height; }
		
	public boolean isChanged() 					{ return hasChanged; }
	public void setChanged(boolean b) 			{ hasChanged = b; }
}
