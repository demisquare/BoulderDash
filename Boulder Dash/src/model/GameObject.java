package model;
//import java.util.concurrent.locks.Condition;
//import java.util.concurrent.locks.ReentrantLock;

public abstract class GameObject {

	static final char EMPTY_BLOCK = '0';
	static final char WALL	 	  = '1';
	static final char DIAMOND 	  = '2';
	static final char GROUND 	  = '3';
	static final char ROCK 		  = '4';
	static final char DOOR 		  = 'D';
	static final char PLAYER	  = 'P';
	static final char HOST	 	  = 'H';
	static final char ENEMY		  = 'E';
	
	public static final int DOWN  = 0;
	public static final int LEFT  = 1;
	public static final int RIGHT = 2;
	public static final int UP    = 3;
		
	static final int dmap[][] = { { 0,  1},
			                      {-1,  0},
			                      { 1,  0},
			                      { 0, -1} };
	
	static GameMap map = null;
	
	protected int x;
	protected int y;
	
	protected boolean processed;
	protected boolean dead;
	protected boolean isFalling;
	public boolean moved;
	
	//public static ReentrantLock lock;
	//public static Condition hasMoved;
	
	protected GameObject successor;
	
	public GameObject(int x, int y) {
		
		this.x = x;
		this.y = y;
		processed = false;
		isFalling = false;
		moved = false;
		dead = false;
		
		//lock = new ReentrantLock();
		//hasMoved = lock.newCondition();
		
		successor = null;
	}
	
	@Override
	public int hashCode() {
		return x*map.dimX + y;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj.getClass().isInstance(this) && 
				obj.hashCode() == hashCode();
	}
	
	@Override
	public String toString() {
		return this.getClass().getCanonicalName() + " at [" + x + ", " + y + "]";
	}
	
	public int getX() 		{ return x;   }	
	public void setX(int x) { this.x = x; }
	
	public int getY() 		{ return y;   }
	public void setY(int y) { this.y = y; }	
	
	public boolean hasChanged()  { return processed; }
	
	public boolean isDead() { return dead; }
	
	public boolean hasMoved() { return moved; }
	
	public GameObject getSuccessor() { return successor; }
	
	public void setDead(boolean x) {dead = x; }
	
	public abstract boolean update();
	public abstract boolean update(int dir);
	/*
	 * Primo metodo fondamentale: gestisce la distruzione di un oggetto
	 * (diamanti, terreno, nemici...) sostituendolo nella map con un EmptyBlock,
	 * conservato in successor per facilit� di recupero nella fase
	 * di aggiornamento della grafica ( si veda Level.updateGraphics() )
	 * */
	protected final boolean destroy() {
	
		try {
				successor = new EmptyBlock(x, y);
				
				map.setTile(x, y, successor);
				
				successor.processed = false;		
				processed = true;
				dead = true;
				
				if(map.containsKey(x*map.dimX+y) && map.getTile(x, y).equals(successor)) {
					System.out.println("viene distrutto... " + this);
				
				} else {
					throw new NullPointerException();
				}
				
				return true;
			
		} catch(NullPointerException e) {
			System.out.println("I'm in destroy(): " + this);
			e.printStackTrace();
		}
		
		return false;
	}
	/*
	 * Secondo metodo fondamentale: gestisce il movimento di oggetti tramite lo
	 * "swap" (scambio) delle posizioni in termini di coordinate sulla mappa logica,
	 * garantendo che i dati di ogni oggetto siano coerenti in ogni fase di esecuzione.
	 * */
	protected final void swap(int i, int j) {
		
		GameObject temp = map.getTile(i, j);
		boolean error = false;
		
		//if(temp.processed == false) {
			
			if(!map.getTile(x, y).equals(this)) {
			
				System.out.println("in model.GameObject.swap(): " + this + " != " + map.getTile(x, y));
				error = true;
			}
		
			map.setTile(x, y, temp);
			temp.x = x;
			temp.y = y;
			temp.processed = true;
	
			if(!map.getTile(i, j).equals(temp)) {
		
				System.out.println("in model.GameObject.swap(): " + temp + " != " + map.getTile(i, j));
				error = true;
			}
	
			map.setTile(i, j, this);
			x = i;
			y = j;
			processed = true;
			
			/*if(!error)
				System.out.println("scambia i tiles...");*/
		//}
	}
	
	protected boolean fall() {
		
		if(!(y+1 < 0 || y+1 >= map.dimY)) {
			
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
	
	protected boolean move(int dir) {
		
		int i = (x + dmap[dir][0]);
		int j = (y + dmap[dir][1]);
		
		if(!(i < 0 || i >= map.dimX) && !(j < 0 || j >= map.dimY)) {
		
			if(map.getTile(i, j) instanceof EmptyBlock) {
				
				moved = true;
				System.out.println("si muove...");
				swap(i, j);
				return true;
			}
		}
		moved = false;
		return false;
	}
}