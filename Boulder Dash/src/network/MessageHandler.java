package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import network.packet.*;

public class MessageHandler {

	private static Socket socket;
	public static void setSocket(Socket socket) throws IOException {
		MessageHandler.socket = socket;
	}


	public static void sendObject(Object obj) {
		try {
			ObjectOutputStream write = new ObjectOutputStream(socket.getOutputStream());
			write.writeObject(obj);
			write.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Object receiveObject() {
		Object obj = new Object();
		try {
			ObjectInputStream read = new ObjectInputStream(socket.getInputStream());
			obj = read.readObject();

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return obj;
	}

	public void HandlePacket(Object pkg) {

		if (pkg instanceof PacketMove) {
			// TODO: operazioni per muovere player/nemici...
		}

		else if (pkg instanceof PacketDie) {
			// TODO: operazioni per uccidere player/nemici...
		}
	}
}
