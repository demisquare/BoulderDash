//AUTORE: Davide Caligiuri
package model;

import it.unical.mat.embasp.languages.Id;

@Id("emptyblock")
public class EmptyBlock extends GameObject {
	
	public EmptyBlock(int x, int y) 			{ super(x, y); }
	
	@Override public boolean update() 			{ return false; }
	@Override public boolean update(int dir) 	{ return false; }
}
