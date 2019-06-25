package network;

import java.io.IOException;
import java.net.Socket;

import menu.Options;
import model.Player;
import network.packet.PacketMove;
import view.Game;

public class SocketClient implements Runnable {

	private Thread t;
	private boolean closeRun;
	private Socket socket;
	Player host;

	public SocketClient(Game game) {
		host = (Player) game.level.getWorld().getPlayer();
		socket = null;
		closeRun = false;
		t = null;
		Options.multiplayer = true;
	}

	public void connect() {
		try {
			System.out.println("[CLIENT] Connessione al server...");
			socket = new Socket("localhost", 8000);
			System.out.println("[CLIENT] Connesso!");
			MessageHandler.setSocket(socket);
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
			
			if(obj!=null)
				System.out.println("[CLIENT] ricevo dal server: " + obj.toString());
			
			PacketMove move = new PacketMove(0, 0, 0, 0);
			MessageHandler.sendObject(move);
			System.out.println("[CLIENT] Invio al server: " + move.toString());
			
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
