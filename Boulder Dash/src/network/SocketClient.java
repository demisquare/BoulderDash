package network;

import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

import model.Host;
import model.Player;
import network.packet.Packet;
import network.packet.PacketDie;
import network.packet.PacketMove;
import view.Game;

public class SocketClient {

	private Thread t1;
	private Thread t2;
	private boolean closeRun;
	private Socket socket;
	Player player;
	// Host host;
	Game game;

	public SocketClient(Game game) {
		//player = (Player) game.level.getWorld().getPlayer();
		// host = (Host) game.level.getWorld().getHost();
		this.game = game;
		player = null;
		socket = null;
		closeRun = false;
		t1 = null;
		t2 = null;
	}

	public void connect() {
		try {
			System.out.println("[CLIENT] Connessione al server...");
			socket = new Socket("localhost", 8000);
			System.out.println("[CLIENT] Connesso!");
			MessageHandler.setSocket(socket);
			t1 = new Thread(new Runnable() {

				@Override
				public void run() {
					System.out.println("[CLIENT] Avvio thread invio...");
					while (socket.isConnected() && !socket.isClosed()) {
						synchronized (this) {
							player = (Player) game.level.getWorld().getPlayer();
							
							if (player.hasMoved()) {
								Packet move = new PacketMove(player.getX(), player.getY(), 0);
								MessageHandler.sendObject(move);
								System.out.println("[CLIENT] Invio al server: " + move.toString());
							}

							else if (player.isDead()) {
								Packet die = new PacketDie(player.getX(), player.getY());
								MessageHandler.sendObject(die);
								System.out.println("[CLIENT] Invio al server: " + die.toString());
							}
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
			});
			//t1.start();

			t2 = new Thread(new Runnable() {

				@Override
				public void run() {
					System.out.println("[CLIENT] Avvio thread ricezione...");
					while (socket.isConnected() && !socket.isClosed()) {
						synchronized (this) {
							player = (Player) game.level.getWorld().getPlayer();
							System.out.println("[CLIENT] In ascolto...");
							Packet obj;
							try {
								obj = MessageHandler.receiveObject();
							} catch (EOFException e1) {
								System.out.println("[CLIENT] Server disconnesso...");
								close();
								return;
							}

							if (obj != null)
								System.out.println("[CLIENT] ricevo dal server: " + obj.toString());

							if (closeRun)
								return;
						}
						try {
							Thread.sleep(34);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			});
			//t2.start();

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
}
