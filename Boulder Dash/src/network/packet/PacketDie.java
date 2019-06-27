package network.packet;

public class PacketDie extends Packet {

	private static final long serialVersionUID = 5685846674369289480L;

	public PacketDie(int x, int y) {
		super(x, y);
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