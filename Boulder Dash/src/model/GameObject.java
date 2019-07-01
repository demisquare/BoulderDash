//AUTORE: Davide Caligiuri
package model;

public abstract class GameObject {
//	costanti usate per le direzioni di movimento
	public static final int DOWN  = 0;
	public static final int LEFT  = 1;
	public static final int RIGHT = 2;
	public static final int UP    = 3;	
//	mappa costante direzione -> variazione coordinate
	public static final int dmap[][] = { { 0,  1},
			               				 {-1,  0},
			               				 { 1,  0},
			               				 { 0, -1} };
//	riferimento alla mappa di gioco
	public static GameMap map = null;
//	costanti usate per la lettura da file
	static final char EMPTY_BLOCK = '0';
	static final char WALL	 	  = '1';
	static final char DIAMOND 	  = '2';
	static final char GROUND 	  = '3';
	static final char ROCK 		  = '4';
	static final char DOOR 		  = 'D';
	static final char PLAYER	  = 'P';
	static final char HOST	 	  = 'H';
	static final char ENEMY		  = 'E';
//	coordinate dell'oggetto
	protected int x;
	protected int y;
//	verifica se l'oggetto ha subito un aggiornamento (tramite update())
	protected boolean processed;
//	verifica se l'oggetto necessita di essere eliminato
	protected boolean dead;
//	verifica se l'oggetto sta cadendo
	protected boolean isFalling;
//	verifica se l'oggetto si sta muovendo
	protected boolean moved;
//	verifica se l'oggetto è respawnato
	protected boolean respawned;
//	nel caso l'oggetto vada sostituito, 
//	corrisponde all'oggetto con cui sostituirlo
	protected GameObject successor;
	
	public GameObject(int x, int y) {
		
		this.x = x;
		this.y = y;
		processed = false;
		isFalling = false;
		moved = false;
		dead = false;
		respawned = false;		
		successor = null;
	}
	
	public abstract boolean update();
	public abstract boolean update(int dir);

//	Primo metodo fondamentale: gestisce la distruzione di un oggetto
//	(diamanti, terreno, nemici...) sostituendolo nella map con un EmptyBlock,
//	conservato in successor per facilita' di recupero nella fase
//	di aggiornamento della grafica ( si veda Level.updateGraphics() )
	protected final boolean destroy() {
	
		try {
				successor = new EmptyBlock(x, y);
				
				map.setTile(x, y, successor);
				
				successor.processed = false;		
				processed = true;
				dead = true;
				
				if(map.containsKey(x*GameMap.dimX+y) && map.getTile(x, y).equals(successor)) {
					//System.err.println("viene distrutto... " + this);
				
				} else {
					throw new NullPointerException();
				}
				
				return true;
			
		} catch(NullPointerException e) {
			System.err.println("I'm in destroy(): " + this);
			e.printStackTrace();
		}
		
		return false;
	}
	
//	 Secondo metodo fondamentale: gestisce il movimento di oggetti tramite lo
//	 "swap" (scambio) delle posizioni in termini di coordinate sulla mappa logica,
//	 garantendo che i dati di ogni oggetto siano coerenti in ogni fase di esecuzione.
	protected final void swap(int i, int j) {
		
		GameObject temp = map.getTile(i, j);
		
		if(!map.getTile(x, y).equals(this))	
			System.err.println("in model.GameObject.swap(): " + this + " != " + map.getTile(x, y));
		
		map.setTile(x, y, temp);
		temp.x = x;
		temp.y = y;
		temp.processed = false;
	
		if(!map.getTile(i, j).equals(temp))
			System.err.println("in model.GameObject.swap(): " + temp + " != " + map.getTile(i, j));
	
		map.setTile(i, j, this);
		x = i;
		y = j;
		processed = true;

	}
	
//	implementazione generica della gravità:
//	qualora la casella inferiore sia vuota,
//	l'oggetto scambia la sua posizione con quello sottostante
//	(simulando una caduta)
	protected boolean fall() {
		
		if(!(y+1 < 0 || y+1 >= GameMap.dimY)) {
			
			try { 
				if(map.getTile(x, y+1) instanceof EmptyBlock) {

					isFalling = true;
					swap(x, y+1);
					return true;
				
				}
			} catch(NullPointerException e) {
				e.printStackTrace();
			}
		}
		
		isFalling = false;
		return false;
	}

//	questo metodo gestisce il movimento in una determinata direzione:
//	se la posizione nella direzione data è libera, l'ogetto scambia
//	la sua posizione con quella sottostante
//	(simulando una caduta)
	protected boolean move(int dir) {
		
		int i = (x + dmap[dir][0]);
		int j = (y + dmap[dir][1]);
		
		if(!(i < 0 || i >= GameMap.dimX) && !(j < 0 || j >= GameMap.dimY)) {
		
			if(map.getTile(i, j) instanceof EmptyBlock) {
				
				moved = true;
				swap(i, j);
				return true;
			}
		}
		moved = false;
		return false;
	}

	public void clearFlags()					{ processed = false; }
	
	public int getX() 							{ return x;   }	
	public void setX(int x) 					{ this.x = x; }
	
	public int getY() 							{ return y;   }
	public void setY(int y) 					{ this.y = y; }	
	
	public boolean hasChanged()  				{ return processed; }
	
	public boolean isDead() 					{ return dead; }
	public void setDead(boolean x) 				{ dead = x; }
	
	public boolean hasMoved() 					{ return moved; }
	public boolean isMoved() 					{ return moved; }
	public void setMoved(boolean moved) 		{ this.moved = moved; }

	public boolean isRespawned() 				{ return respawned; }
	public void setRespawned(boolean respawned) { this.respawned = respawned; }
	
	public GameObject getSuccessor()			{ return successor; }

	@Override
	public int hashCode() {
		return 
			x*GameMap.dimX + y;
	}
	
	@Override
	public boolean equals(Object obj) {
		return 
			obj.getClass().isInstance(this) && obj.hashCode() == hashCode();
	}
	
	@Override
	public String toString() {
		return
			this.getClass().getCanonicalName() + " at [" + x + ", " + y + "]";
	}
}