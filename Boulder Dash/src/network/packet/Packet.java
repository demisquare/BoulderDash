package network.packet;

import java.io.Serializable;

public abstract class Packet implements Serializable{

	private static final long serialVersionUID = 3559409846577267961L;
	
	int id;
	int x;
	int y;

	public Packet(int id, int x, int y) {
		this.id = id;
		
		this.x = x;
		this.y = y;
	}
}
