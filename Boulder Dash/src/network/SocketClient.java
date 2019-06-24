package network;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

import model.World;
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
		try {
			InputStream inputStream = socket.getInputStream();
			ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

			while (socket.isConnected() && !socket.isClosed()) {
				
				System.out.println("[CLIENT] Ricevo il world dal server...");
				
				//MARIA deve preparare i pacchetti
				//game.level.setWorld((World) objectInputStream.readObject());
			
				if(closeRun) return;
			}

		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
