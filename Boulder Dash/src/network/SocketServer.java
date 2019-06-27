package network;

import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.stream.Collectors;

import model.GameObject;
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
	// Host host;

	// private ArrayList<Packet> packets;

	public SocketServer(Game game) {
		this.game = game;
		player = null;
		// host = (Host) game.level.getWorld().getHost();
		socket = null;
		closeRun = false;
		t1 = null;
		t2 = null;
		// packets = new ArrayList<Packet>();
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
						
							System.out.println("connesso");
							//game.level.getWorld().getPlayer();
							GameObject.lock.lock();
							if(GameObject.lock.isHeldByCurrentThread())
								System.out.println("Pls");
							
							while (!game.level.getWorld().getPlayer().hasMoved()) {
								//System.out.println("godverdomme");
								//game.level.getWorld().getPlayer().hasMoved.await();
							}
							if(GameObject.lock.isHeldByCurrentThread())
								System.out.println("Pls");
							Packet move = new PacketMove(game.level.getWorld().getPlayer().getX(), game.level.getWorld().getPlayer().getY(), 0);
							MessageHandler.sendObject(move);
							System.out.println("[SERVER] Invio al client: " + move.toString());
							game.level.getWorld().getPlayer();
							GameObject.lock.unlock();
						}

						
						/*
						 * else if (player.isDead()) { }
						 * 
						 */
					

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
							Packet obj;
							try {
								obj = MessageHandler.receiveObject();
							} catch (EOFException e1) {
								System.out.println("[SERVER] Client disconnesso...");
								close();
								return;
							}

							if (obj != null)
								System.out.println("[SERVER] ricevo dal client: " + obj.toString());
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
			// t2.start();

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
