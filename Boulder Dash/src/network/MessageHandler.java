package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import model.Host;
import network.packet.*;

public class MessageHandler {

	private Socket socket;
	private ObjectInputStream read;
	private ObjectOutputStream write;

	public MessageHandler() {
		socket = null;
		read = null;
		write = null;
	}

	public void initInput() throws IOException {
		read = new ObjectInputStream(socket.getInputStream());
	}

	public void initOutput() throws IOException {
		write = new ObjectOutputStream(socket.getOutputStream());
	}

	public void setSocket(Socket socket) throws IOException {
		this.socket = socket;
	}

	public void sendObject(Packet pkg) throws IOException {
	
			write.writeObject(pkg);
			write.flush();
	}

	public Packet receiveObject() throws IOException {
		Packet pkg = null;
		try {
			pkg = (Packet) read.readObject();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return pkg;
	}

	public void HandlePacket(Packet pkg, Host host) {
		
		if(host == null)
			System.out.println("rcodi2");
		
		if (pkg instanceof PacketMove) {
			// TODO: operazioni per muovere player/nemici...
			if(((PacketMove)pkg).getDir() != -1) {
				synchronized(this) {
					host.update(((PacketMove)pkg).getDir());
				}
			}
		}

		else if (pkg instanceof PacketDie) {
			// TODO: operazioni per uccidere player/nemici...
			synchronized(this) {
				host.respawn();
			}
		}
	}
}
