package network;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import view.Game;

public class SocketClient implements Runnable {

	private static int PORT = 8000;
	private Socket socket;
	OutputStream outputStream;
	ObjectOutputStream objectOutputStream;
	Game game;

	public SocketClient(Game game) {

		// Setup networking
		game = new Game();
	}
	
	public void connect() {
		try {
			socket = new Socket("localhost", PORT);
			System.out.println("Connected!");
			outputStream = socket.getOutputStream();
			objectOutputStream = new ObjectOutputStream(outputStream);
			new Thread(this).start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void close() {
		System.out.println("Closing socket and terminating program.");
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				System.out.println("Sending messages to the ServerSocket");
				objectOutputStream.writeObject(game.level.getWorld());
				
				game.level.run();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

}
