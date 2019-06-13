package network;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import model.World;
import view.Game;

public class Server extends Game implements Runnable {

	private static final long serialVersionUID = 1L;

	private static int PORT = 8000;
	ServerSocket ss;
	private Socket socket;
	InputStream inputStream;
	ObjectInputStream objectInputStream;

	public Server(String serverAddress) {

		// Setup networking
		super();
		try {
			ss = new ServerSocket(PORT);
			System.out.println("ServerSocket awaiting connections...");
			Socket socket = ss.accept();
			System.out.println("Connection from " + socket + "!");
			
			// get the input stream from the connected socket
	        inputStream = socket.getInputStream();
	        // create a DataInputStream so we can read data from it.
	        objectInputStream = new ObjectInputStream(inputStream);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void run() {
		while (true) {
			try {
				System.out.println("Reading messages from the Client");
				level.setWorld((World) objectInputStream.readObject());
				
				super.level.run();

				System.out.println("Closing sockets.");
				ss.close();
				socket.close();
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
				
			}

		}
	}

}
