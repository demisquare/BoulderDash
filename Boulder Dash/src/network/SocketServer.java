package network;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import view.Game;

public class SocketServer implements Runnable {

	private ServerSocket server;
	private Socket socket = null;
	private Game game;

	public SocketServer(Game game) {
		this.game = game;
	}

	public void connect() {
		try {
			server = new ServerSocket(8000);
			System.out.println("[SERVER] Server attivo. In attesa di connessioni...");
			socket = server.accept();
			System.out.println("[SERVER] Connessione stabilita con " + socket.getInetAddress() + ":" + socket.getLocalPort());
			new Thread(this).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			server.close();
			if(socket != null)
				socket.close();
			
			System.out.println("[SERVER] Server chiuso.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@Override
	public void run() {
		try {
			OutputStream outputStream = socket.getOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

			while (socket.isConnected()) {
				 System.out.println("[SERVER] Invio il world al client...");
				 objectOutputStream.writeObject(game.level.getWorld());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
