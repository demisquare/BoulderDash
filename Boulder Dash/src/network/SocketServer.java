package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import menu.Options;
import model.Player;
import network.packet.PacketMove;
import view.Game;

public class SocketServer implements Runnable {

	private Thread t;
	private boolean closeRun;
	private ServerSocket listener;
	private Socket socket;
	//Player player;
	//Host host;

	public SocketServer(Game game) {
		//player = (Player) game.level.getWorld().getPlayer();
		//host = (Host) game.level.getWorld().getHost();
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
			System.out.println(
					"[SERVER] Connessione stabilita con " + socket.getInetAddress().getHostAddress() + ":" + socket.getLocalPort());
			t = new Thread(this);
			t.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {

			listener.close();
			if (socket != null) {
				socket.close();

			}

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
		if (socket.isConnected() && !socket.isClosed()) {
			PacketMove first = new PacketMove(0, 0, 0, 0);
			MessageHandler.sendObject(first);
			System.out.println("[SERVER] Invio al client: " + first.toString());
		}

		while (socket.isConnected() && !socket.isClosed()) {
			Object obj = MessageHandler.receiveObject();

			if (obj != null) {
				System.out.println("[SERVER] ricevo dal client: " + obj.toString());
				PacketMove move = new PacketMove(0, 0, 0, 0);
				MessageHandler.sendObject(move);
				System.out.println("[SERVER] Invio al client: " + move.toString());
			}
			try {
				Thread.sleep(34);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (closeRun)
				return;
		}
	}
}
