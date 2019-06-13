package network;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import view.Game;

public class Client extends Game implements Runnable {

	private static final long serialVersionUID = 1L;

	private static int PORT = 8000;
	private Socket socket;
	OutputStream outputStream;
	ObjectOutputStream objectOutputStream;

	public Client(String serverAddress) {

		// Setup networking
		super();
		try {
			socket = new Socket("localhost", PORT);
			System.out.println("Connected!");
			outputStream = socket.getOutputStream();
			objectOutputStream = new ObjectOutputStream(outputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void run() {
		while (true) {
			try {
				System.out.println("Sending messages to the ServerSocket");
				objectOutputStream.writeObject(level.getWorld());
				
				super.level.run();
				
				System.out.println("Closing socket and terminating program.");
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

}
