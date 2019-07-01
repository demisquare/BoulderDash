//AUTORE: Maria De Miglio
package network.packet;

import java.io.Serializable;

public abstract class Packet implements Serializable{

	private static final long serialVersionUID = 3559409846577267961L;
	
	private int x;
	private int y;
	
	//destinazione del pacchetto:
		// -1 = Player
		// -2 = Host
		// >0 = Enemies
		
		private int dest;

	public Packet(int x, int y, int dest) {
		this.x = x;
		this.y = y;
		this.dest = dest;
	}
	
	public int getDest() {
		return dest;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		if(dest == -1)
			return " : ("+x+" - "+y+") @Player";
		else
			return " : ("+x+" - "+y+") @Enemy["+ dest + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(obj instanceof Packet) 
			return this.x == ((Packet)obj).x && this.y == ((Packet)obj).y && this.dest == ((Packet)obj).dest;
		
		return false;
	}

	public int getX() {
		return x;
	}
	
	public int getY() {
		return x;
	}
}
