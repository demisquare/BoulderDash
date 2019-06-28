package network;

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
	private MessageHandler msg;
	Player player;
	Host host;
	Game game;

	public SocketClient(Game game) {
		this.game = game;
		player = (Player) game.level.getWorld().getPlayer();
		host = (Host) game.level.getWorld().getHost();
		player = null;
		socket = null;
		closeRun = false;
		t1 = null;
		t2 = null;
		msg = new MessageHandler();
	}

	public void connect() {
		try {
			System.out.println("[CLIENT] Connessione al server...");
			socket = new Socket("localhost", 8000);
			
			msg.setSocket(socket);
			msg.initOutput();
			msg.sendObject(new PacketMove(0, 0, 0));
			msg.initInput();
			msg.receiveObject();
			
			System.out.println("[CLIENT] Connesso!");
			
			t1 = new Thread(new Runnable() {

				@Override
				public void run() {
					System.out.println("[CLIENT] Avvio thread invio...");
					while (socket.isConnected() && !socket.isClosed()) {

						if (player.hasMoved()) {
							Packet move = new PacketMove(player.getX(), player.getY(), player.getLastDir());

							try {
								msg.sendObject(move);
							} catch (IOException e) {

								System.out.println("[CLIENT] Server disconnesso...");
								close();
							}

							System.out.println("[CLIENT] Invio al server: " + move.toString());

							player.setMoved(false);
						}

						if (player.isRespawned()) {
							Packet die = new PacketDie(player.getX(), player.getY());

							try {
								msg.sendObject(die);
							} catch (IOException e) {

								System.out.println("[CLIENT] Server disconnesso...");
								close();
							}

							System.out.println("[CLIENT] Invio al server: " + die.toString());

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
					System.out.println("[CLIENT] Avvio thread ricezione...");
					while (socket.isConnected() && !socket.isClosed()) {
						synchronized (this) {
							
							System.out.println("[CLIENT] In ascolto...");
							Packet pkg;
							try {
								pkg = (Packet) msg.receiveObject();
							} catch (IOException e) {
								System.out.println("[CLIENT] Server disconnesso...");
								close();
								return;
							}

							if (pkg != null) {
								if(host == null)
									System.out.println("rcodi");
								
								Host htemp = host;
								msg.HandlePacket(pkg, htemp);
								
								System.out.println("[CLIENT] ricevo dal server: " + pkg.toString());
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
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void close() {
		System.out.println("[CLIENT] Socket chiuso.");
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeRun = true;
		}
	}
}
