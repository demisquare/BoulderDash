package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import network.packet.*;
import view.Game;

public class SocketServer implements Runnable {

	private Thread t;
	private boolean closeRun;
	private ServerSocket listener;
	private Socket socket;
	private Game game;

	public SocketServer(Game game) {
		this.game = game;
		socket = null;
		t = null;
		closeRun = false;
	}

	public void connect() {
		try {
			listener = new ServerSocket(8000);
			System.out.println("[SERVER] Server attivo. In attesa di connessioni...");
			socket = listener.accept();
			MessageHandler.setSocket(socket);
			System.out.println("[SERVER] Connessione stabilita con " + socket.getInetAddress() + ":" + socket.getLocalPort());
			t = new Thread(this);
			t.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {

			listener.close();
			if (socket != null)
				socket.close();

			System.out.println("[SERVER] Server chiuso.");

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
			MessageHandler.sendObject(new PacketMove(0, 0, 0, 0));
			System.out.println("[SERVER] Invio variazioni al client...");

			if (closeRun)
				return;
		}
	}
}
