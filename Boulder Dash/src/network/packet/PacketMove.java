package network.packet;

public class PacketMove extends Packet {
	
	private static final long serialVersionUID = -4739418266360642653L;
	
	int dir;
	
	public PacketMove(int id, int x, int y, int dir) {
		super(id, x, y);
		this.dir = dir;
	}

}