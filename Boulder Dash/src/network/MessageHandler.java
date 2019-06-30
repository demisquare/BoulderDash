package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.Socket;

import model.Host;

import network.packet.*;

import view.Level;

public class MessageHandler {

	private Socket socket;
	private ObjectInputStream read;
	private ObjectOutputStream write;

	public MessageHandler() {
		socket = null;
		read = null;
		write = null;
	}

	public void close() throws IOException {
		write.close();
		read.close();
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

	public void HandlePacket(Packet pkg, Level level) {

		if (pkg instanceof PacketMove) {
			// TODO: operazioni per muovere player/nemici...
			if (((PacketMove) pkg).getDir() != -1) {
				if (((PacketMove) pkg).getDest() == -1) {
					synchronized (this) {
						level.updateHostOnPressing(((PacketMove) pkg).getDir());
					}
				} else {
					//muovi nemici...
					
				}
				
			}
		}

		else if (pkg instanceof PacketDie) {
			// TODO: operazioni per uccidere player/nemici...
			synchronized (this) {
				((Host) level.getWorld().getHost()).respawn(((PacketDie) pkg).getX(), ((PacketDie) pkg).getY());
			}
		}
	}
}
