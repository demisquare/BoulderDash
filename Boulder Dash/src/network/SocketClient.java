package network;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import javax.swing.JOptionPane;

import model.Host;
import model.Player;
import network.packet.Packet;
import network.packet.PacketDie;
import network.packet.PacketMove;
import network.packet.PacketStand;
import view.Game;

public class SocketClient {

	private Thread t1;
	private Thread t2;
	private Socket socket;
	private MessageHandler msg;
	private boolean connected;
	Player player;
	Host host;
	Game game;

	public SocketClient(Game game) {
		this.game = game;
		player = (Player) game.level.getWorld().getPlayer();
		host = (Host) game.level.getWorld().getHost();
		connected = false;
		socket = null;
		t1 = null;
		t2 = null;
		msg = new MessageHandler();
	}

	public void connect() {
		try {
			try {
				System.out.println("[CLIENT] Connessione al server...");
				socket = new Socket("localhost", 8000);
				// socket.setSoTimeout(10000);

				msg.setSocket(socket);
				msg.initOutput();
				msg.sendObject(new PacketMove(0, 0, 0, 0));
				msg.initInput();
				msg.receiveObject();

				connected = true;
				System.out.println("[CLIENT] Connesso!");
			} catch (ConnectException c) {
				// System.err.println("[CLIENT] Errore! Il server non ha risposto.");
				JOptionPane.showMessageDialog(null,
						"Failed to connect to server. Please check first if you have previously created a game.",
						"Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			t1 = new Thread(new Runnable() {

				@Override
				public void run() {
					System.out.println("[CLIENT] Avvio thread invio...");
					while (socket.isConnected() && !socket.isClosed()) {

						if (player.hasMoved() && player.getLastDir() != -1) {
							Packet move = new PacketMove(player.getX(), player.getY(), player.getLastDir(), -1);

							try {
								msg.sendObject(move);
							} catch (IOException e) {

								// System.out.println("[CLIENT] Server disconnesso...");
								JOptionPane.showMessageDialog(null, "Connection Timeout.", "Error",
										JOptionPane.ERROR_MESSAGE);
								close();
							}

							System.out.println("[CLIENT] Invio al server: " + move.toString());

							player.setMoved(false);
						}

						if (game.level.isMouseReleased() && player.getLastDir() != -1) {
							Packet stand = new PacketStand(player.getX(), player.getY(), player.getLastDir(), -1);

							try {
								msg.sendObject(stand);
							} catch (IOException e) {

								//System.err.println("[SERVER] Client disconnesso...");
								JOptionPane.showMessageDialog(null, "Connection Timeout.", "Error",
										JOptionPane.ERROR_MESSAGE);
								close();
							}

							System.out.println("[SERVER] Invio al client: " + stand.toString());

							game.level.setMouseReleased(false);
						}

						if (player.hasRespawned()) {
							Packet die = new PacketDie(player.getX(), player.getY(), -1);

							try {
								msg.sendObject(die);
							} catch (IOException e) {

								// System.out.println("[CLIENT] Server disconnesso...");
								JOptionPane.showMessageDialog(null, "Connection Timeout.", "Error",
										JOptionPane.ERROR_MESSAGE);
								close();
							}

							System.out.println("[CLIENT] Invio al server: " + die.toString());

							player.setRespawned(false);
						}
					}

					try {
						Thread.sleep(5);
					} catch (InterruptedException e) {
						close();
						// e.printStackTrace();
					}

				}

			});
			t1.start();

			t2 = new Thread(new Runnable() {

				@Override
				public void run() {
					System.out.println("[CLIENT] Avvio thread ricezione...");
					while (socket.isConnected() && !socket.isClosed()) {

						System.out.println("[CLIENT] In ascolto...");
						Packet pkg;
						try {
							pkg = (Packet) msg.receiveObject();
						} catch (IOException e) {
							// System.out.println("[CLIENT] Server disconnesso...");
							JOptionPane.showMessageDialog(null, "Connection Timeout.", "Error",
									JOptionPane.ERROR_MESSAGE);
							close();
							return;
						}

						if (pkg != null) {

							msg.HandlePacket(pkg, game.level);

							System.out.println("[CLIENT] ricevo dal server: " + pkg.toString());
							// host.update(GameObject.DOWN);
						}

						try {
							Thread.sleep(5);
						} catch (InterruptedException e) {
							close();
							//e.printStackTrace();
						}

					}
				}
			});
			t2.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isConnected() {
		return connected;
	}

	public void close() {
		try {
			msg.close();
			socket.close();
			System.out.println("[CLIENT] Socket chiuso.");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			connected = false;

			t1.interrupt();
			t2.interrupt();
		}
	}
}
