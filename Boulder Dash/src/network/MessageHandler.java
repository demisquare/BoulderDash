package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import model.Host;
import network.packet.*;

public class MessageHandler {

	private static Socket socket;
	public static void setSocket(Socket socket) throws IOException {
		MessageHandler.socket = socket;
	}


	public static void sendObject(Packet pkg) throws IOException{
		try {
			ObjectOutputStream write = new ObjectOutputStream(socket.getOutputStream());
			write.writeObject(pkg);
			write.flush();

		} catch (IOException e) {
			throw new IOException();
		}
	}

	public static Packet receiveObject() throws IOException {
		Packet pkg = null;
		try {
			ObjectInputStream read = new ObjectInputStream(socket.getInputStream());
			pkg = (Packet)read.readObject();

		} catch (IOException | ClassNotFoundException e) {
			throw new IOException();
		}
		return pkg;
	}

	public static void HandlePacket(Packet pkg, Host host) {

		if (pkg instanceof PacketMove) {
			// TODO: operazioni per muovere player/nemici...
		}

		else if (pkg instanceof PacketDie) {
			// TODO: operazioni per uccidere player/nemici...
		}
	}
}
