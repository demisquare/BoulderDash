package network.packet;

public class PacketMove extends Packet {
	
	private static final long serialVersionUID = -4739418266360642653L;
	
	int dir;
	
	public PacketMove(int x, int y, int dir) {
		super(x, y);
		this.dir = dir;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "[move: " + super.toString() + " - " + dir + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof PacketMove)
			return super.equals(obj) && this.dir == ((PacketMove)obj).dir;
		return false;
	}

}