//AUTORE: Maria De Miglio
package network.packet;

public class PacketDie extends Packet {

	private static final long serialVersionUID = 5685846674369289480L;

	public PacketDie(int x, int y, int dest) {
		super(x, y, dest);
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "[die: " + super.toString() + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof PacketDie)
			return super.equals(obj);
		return false;
	}
	
}