//AUTORE: Maria De Miglio
package network.packet;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("move")
public class PacketMove extends Packet {
	
	private static final long serialVersionUID = -4739418266360642653L;
	
	@Param(0)
	private int dir;
	
	public PacketMove(int x, int y, int dir, int dest) {
		super(x, y, dest);
		this.dir = dir;
	}
	
	public int getDir() {
		return dir;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "[move: " + super.toString() + " - " + dir +"]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof PacketMove)
			return super.equals(obj) && this.dir == ((PacketMove)obj).dir;
		return false;
	}

}