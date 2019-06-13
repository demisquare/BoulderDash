package network;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import model.World;
import view.Game;

public class SocketServer extends Game implements Runnable {

	private static final long serialVersionUID = 1L;

	private static int PORT = 8000;
	ServerSocket ss;
	private Socket socket;
	InputStream inputStream;
	ObjectInputStream objectInputStream;
	Game game;

	public SocketServer(Game game) {

		// Setup networking
		game = new Game();
	}
	
	public void connect() {
		try {
			ss = new ServerSocket(PORT);
			System.out.println("ServerSocket awaiting connections...");
			Socket socket = ss.accept();
			System.out.println("Connection from " + socket + "!");
	        inputStream = socket.getInputStream();
	        objectInputStream = new ObjectInputStream(inputStream);
	        new Thread(this).start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void close() {
		System.out.println("Closing sockets.");
		try {
			ss.close();
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
				System.out.println("Reading messages from the Client");
				game.level.setWorld((World) objectInputStream.readObject());
				
				game.level.run();
		
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
				
			}

		}
	}

}
