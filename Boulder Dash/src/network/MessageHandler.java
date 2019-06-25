package network;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import network.packet.*;

public class MessageHandler {
	private static Socket socket;
	
	public static void setSocket(Socket socket) {
		if (socket.isConnected())
			MessageHandler.socket = socket;
	}

	public static void sendObject(Object obj) {
		OutputStream outputStream;
		ObjectOutputStream objectOutputStream;
		try {
			outputStream = socket.getOutputStream();
			objectOutputStream = new ObjectOutputStream(outputStream);

			objectOutputStream.writeObject(obj);
			objectOutputStream.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Object receiveObject() {
		InputStream inputStream;
		ObjectInputStream objectInputStream;

		Object obj = new Object();
		try {
			inputStream = socket.getInputStream();
			objectInputStream = new ObjectInputStream(inputStream);

			obj = objectInputStream.readObject();
			objectInputStream.close();

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
