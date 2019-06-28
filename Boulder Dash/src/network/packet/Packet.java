package network.packet;

import java.io.Serializable;

public abstract class Packet implements Serializable{

	private static final long serialVersionUID = 3559409846577267961L;
	
	private int x;
	private int y;

	public Packet(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return " : ("+x+" - "+y+")";
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(obj instanceof Packet) 
			return this.x == ((Packet)obj).x && this.y == ((Packet)obj).y;
		
		return false;
	}
}
