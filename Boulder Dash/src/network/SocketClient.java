package network;

import java.io.IOException;
import java.net.Socket;

import view.Game;

public class SocketClient implements Runnable {
	
	private Thread t;
	private boolean closeRun; 
	private Socket socket;
	Game game;

	public SocketClient(Game game) {

		// Setup networking
		this.game = game;
		socket = null;
		closeRun = false;
		t = null;
	}

	public void connect() {
		try {
			System.out.println("[CLIENT] Connessione al server...");
			socket = new Socket("localhost", 8000);
			System.out.println("[CLIENT] Connesso!");
			t = new Thread(this);
			t.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void close() {
		System.out.println("[CLIENT] Socket chiuso.");
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeRun = true;
		}
	}

	@Override
	public void run() {
		while (socket.isConnected() && !socket.isClosed()) {
			Object obj = MessageHandler.receiveObject();
			System.out.println("[CLIENT] ricevo variazioni dal server: " + obj.toString());

			if (closeRun)
				return;
		}
	}
}
