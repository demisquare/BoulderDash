//AUTORE: Davide Caligiuri
package model;

public class Ground extends GameObject {
	
	public Ground(int x, int y) { super(x, y); }
	
	@Override public boolean update() 		 { return false; }
	@Override public boolean update(int dir) { return false; }
}
