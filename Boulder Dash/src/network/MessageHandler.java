package network;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

import model.World;
import view.Game;

public class MessageHandler implements Runnable {
	private Socket socket;
	private Game game;

	public MessageHandler(Socket socket, Game game) {
		this.socket = socket;
		this.game = game;
	}

	@Override
	public void run() {
		try {
			// get the input stream from the connected socket
			InputStream inputStream = socket.getInputStream();
			// create a DataInputStream so we can read data from it.
			ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

			while (true) {
				{
					game.level.setWorld((World) objectInputStream.readObject());
				}
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return;
		}
	}
}
