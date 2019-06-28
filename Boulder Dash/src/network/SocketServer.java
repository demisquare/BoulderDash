package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayDeque;

import model.Host;
import model.Player;
import network.packet.Packet;
import network.packet.PacketDie;
import network.packet.PacketMove;
import view.Game;

public class SocketServer {

	private Thread t1;
	private Thread t2;

	private boolean closeRun;
	private ServerSocket listener;
	private Socket socket;

	Game game;
	Player player;
	Host host;

	private ArrayDeque<Packet> packets;

	public SocketServer(Game game) {
		this.game = game;
		player = null;
		host = null;
		socket = null;
		closeRun = false;
		t1 = null;
		t2 = null;
		packets = new ArrayDeque<Packet>();
	}

	public void connect() {
		try {
			listener = new ServerSocket(8000);
			System.out.println("[SERVER] Server attivo. In attesa di connessioni...");
			socket = listener.accept();
			MessageHandler.setSocket(socket);
			System.out.println("[SERVER] Connessione stabilita con " + socket.getInetAddress().getHostAddress() + ":"
					+ socket.getLocalPort());

			t1 = new Thread(new Runnable() {

				@Override
				public void run() {
					System.out.println("[SERVER] Avvio thread invio...");
					while (socket.isConnected() && !socket.isClosed()) {

						player = (Player) game.level.getWorld().getPlayer();

						if (player.hasMoved()) {
							Packet move = new PacketMove(player.getX(), player.getY(), 0);
							if (!packets.contains(move)) {
								packets.add(move);
								try {
									MessageHandler.sendObject(packets.getLast());
								} catch (IOException e) {
									// TODO Auto-generated catch block
									System.out.println("[SERVER] Client disconnesso...");
									close();
								}
								// packets.remove();
								System.out.println("[SERVER] Invio al client: " + move.toString());
							}
						}

						if (player.isDead()) {
							Packet die = new PacketDie(player.getX(), player.getY());
							if (!packets.contains(die)) {
								packets.add(die);
								try {
									MessageHandler.sendObject(packets.getLast());
								} catch (IOException e) {
									// TODO Auto-generated catch block
									System.out.println("[SERVER] Client disconnesso...");
									close();
								}
								// packets.remove();
								System.out.println("[SERVER] Invio al client: " + die.toString());
							}
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

			});
			t1.start();

			t2 = new Thread(new Runnable() {

				@Override
				public void run() {
					System.out.println("[SERVER] Avvio thread ricezione...");
					while (socket.isConnected() && !socket.isClosed()) {
						synchronized (this) {
							player = (Player) game.level.getWorld().getPlayer();
							System.out.println("[SERVER] In ascolto...");
							Packet pkg;
							try {
								pkg = MessageHandler.receiveObject();
							} catch (IOException e) {
								System.out.println("[SERVER] Client disconnesso...");
								close();
								return;
							}

							if (pkg != null)
								System.out.println("[SERVER] ricevo dal client: " + pkg.toString());
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
			t2.start();

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
}
