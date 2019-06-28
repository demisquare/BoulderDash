package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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
	private MessageHandler msg;

	Game game;
	Player player;
	Host host;

	public SocketServer(Game game) {
		this.game = game;
		player = (Player) game.level.getWorld().getPlayer();
		host = (Host) game.level.getWorld().getHost();
		socket = null;
		closeRun = false;
		t1 = null;
		t2 = null;
		msg = new MessageHandler();
	}

	/*
	 * costruttore di ObjectInputStream: BLOCCANTE si sblocca quando riceve un
	 * pacchetto da un OutputStream
	 * 
	 * quindi: x[SERVER] new InputStream x[CLIENT] new OutputStream x[CLIENT] invia
	 * pacchetto x[SERVER] InputStream riceve, si sblocca x[CLIENT] nel frattempo
	 * new InputStream x[SERVER] new OutputStream (può farlo quando vuoi) x[SERVER]
	 * invia pacchetto x[CLIENT] riceve pacchetto, si sblocca InputStream fine
	 * 
	 */

	public void connect() {
		try {
			listener = new ServerSocket(8000);
			System.out.println("[SERVER] Server attivo. In attesa di connessioni...");
			socket = listener.accept();

			msg.setSocket(socket);
			msg.initInput();
			msg.receiveObject();
			msg.initOutput();
			msg.sendObject(new PacketMove(0, 0, 0));

			System.out.println("[SERVER] Connessione stabilita con " + socket.getInetAddress().getHostAddress() + ":"
					+ socket.getLocalPort());

			t1 = new Thread(new Runnable() {

				@Override
				public void run() {
					System.out.println("[SERVER] Avvio thread invio...");
					while (socket.isConnected() && !socket.isClosed()) {


						if (player.hasMoved()) {
							Packet move = new PacketMove(player.getX(), player.getY(), player.getLastDir());

							try {
								msg.sendObject(move);
							} catch (IOException e) {

								System.out.println("[SERVER] Client disconnesso...");
								close();
							}

							System.out.println("[SERVER] Invio al client: " + move.toString());

							player.setMoved(false);
						}

						if (player.isRespawned()) {
							Packet die = new PacketDie(player.getX(), player.getY());

							try {
								msg.sendObject(die);
							} catch (IOException e) {

								System.out.println("[SERVER] Client disconnesso...");
								close();
							}

							System.out.println("[SERVER] Invio al client: " + die.toString());

						}
						player.setRespawned(false);
					}

					try {
						Thread.sleep(34);
					} catch (InterruptedException e) {

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
							System.out.println("[SERVER] In ascolto...");
							Packet pkg;
							try {
								pkg = (Packet) msg.receiveObject();
							} catch (IOException e) {
								System.out.println("[SERVER] Client disconnesso...");
								close();
								return;
							}

							if (pkg != null) {
								msg.HandlePacket(pkg, host);
								System.out.println("[SERVER] ricevo dal client: " + pkg.toString());
								//host.update(GameObject.DOWN);
							}
						}
						try {
							Thread.sleep(34);
						} catch (InterruptedException e) {

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
