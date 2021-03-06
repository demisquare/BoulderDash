//AUTORE: Maria De Miglio
package network.packet;

public class PacketStand extends Packet {
	
	private static final long serialVersionUID = 2139418266360642653L;
	
	private int dir;
	
	public PacketStand(int x, int y, int dir, int dest) {
		super(x, y, dest);
		this.dir = dir;
	}
	
	public int getDir() {
		return dir;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "[stand: " + super.toString() + " - " + dir + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof PacketStand)
			return super.equals(obj) && this.dir == ((PacketStand)obj).dir;
		return false;
	}

}