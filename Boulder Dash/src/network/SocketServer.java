package network;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import javax.swing.JOptionPane;

import model.Enemy;
import model.GameObject;
import model.Host;
import model.Player;
import network.packet.Packet;
import network.packet.PacketDie;
import network.packet.PacketMove;
import network.packet.PacketStand;
import view.Game;

public class SocketServer {

	private Thread t1;
	private Thread t2;
	private boolean connected;
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
		connected = false;
		socket = null;
		t1 = null;
		t2 = null;
		msg = new MessageHandler();
	}

	/*
	 * costruttore di ObjectInputStream: BLOCCANTE si sblocca quando riceve un
	 * pacchetto da un OutputStream...
	 * 
	 * quindi: x[SERVER] new InputStream x[CLIENT] new OutputStream x[CLIENT] invia
	 * pacchetto x[SERVER] InputStream riceve, si sblocca x[CLIENT] nel frattempo
	 * new InputStream x[SERVER] new OutputStream (può farlo quando vuoi) x[SERVER]
	 * invia pacchetto x[CLIENT] riceve pacchetto, si sblocca InputStream fine
	 */

	public void connect(){
		try {
			try {
				listener = new ServerSocket(8000);  
				listener.setSoTimeout(10000);
				
				System.out.println("[SERVER] Server attivo. In attesa di connessioni...");
							
				socket = listener.accept();
				socket.setSoTimeout(0);

				msg.setSocket(socket);
				msg.initInput();
				msg.receiveObject();
				msg.initOutput();
				msg.sendObject(new PacketMove(0, 0, 0, 0));

				connected = true;
				
				System.out.println("[SERVER] Connessione stabilita con " + socket.getInetAddress().getHostAddress() + ":"
						+ socket.getLocalPort());
			} catch (BindException b) {
				// TODO Auto-generated catch block
				//System.err.println("[SERVER] Errore! Stai cercando di aprire due server!");
				JOptionPane.showMessageDialog(
				        null, "Failed to create a game. Please check first if you have previously created another game.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			catch (SocketTimeoutException s) {
				//System.err.println("[SERVER] Timeout. Server chiuso.");
				JOptionPane.showMessageDialog(
				        null, "Connection Timeout.", "Error", JOptionPane.ERROR_MESSAGE);
				listener.close();
				return;
			}
			

			t1 = new Thread(new Runnable() {

				@Override
				public void run() {
					System.out.println("[SERVER] Avvio thread invio...");
					while (socket.isConnected() && !socket.isClosed()) {
						synchronized (this) {

							if (player.hasMoved() && player.getLastDir() != -1) {
								Packet move = new PacketMove(player.getX(), player.getY(), player.getLastDir());

								try {
									msg.sendObject(move);
								} catch (IOException e) {

									//System.err.println("[SERVER] Client disconnesso...");
									JOptionPane.showMessageDialog(
									        null, "Connection Timeout.", "Error", JOptionPane.ERROR_MESSAGE);
									close();
									return;
								}

								System.out.println("[SERVER] Invio al client: " + move.toString());

								player.setMoved(false);
							}
							
							if (game.level.isMouseReleased() && player.getLastDir() != -1) {
								Packet stand = new PacketStand(player.getX(), player.getY(), player.getLastDir());
							
							for(GameObject enemy:game.level.getWorld().getEnemies())
							{
								if (enemy.hasMoved()) {
									Packet move = new PacketMove(((Enemy) enemy).getX(), ((Enemy) enemy).getY(), ((Enemy) enemy).getLastDir(), game.level.getWorld().getEnemies().indexOf(enemy));

								try {
									msg.sendObject(stand);
								} catch (IOException e) {

									System.err.println("[SERVER] Client disconnesso...");
									close();
								}

								System.out.println("[SERVER] Invio al client: " + stand.toString());

								game.level.setMouseReleased(false);
							}

									try {
										msg.sendObject(move);
									} catch (IOException e) {

										//System.err.println("[SERVER] Client disconnesso...");
										JOptionPane.showMessageDialog(
										        null, "Connection Timeout.", "Error", JOptionPane.ERROR_MESSAGE);
										close();
										return;
									}

									System.out.println("[SERVER] Invio al client: " + move.toString());

									((Enemy) enemy).setMoved(false);
								}
							}

							if (player.isRespawned()) {
								Packet die = new PacketDie(player.getX(), player.getY(), -1);

								try {
									msg.sendObject(die);
								} catch (IOException e) {

									//System.err.println("[SERVER] Client disconnesso...");
									JOptionPane.showMessageDialog(
									        null, "Connection Timeout.", "Error", JOptionPane.ERROR_MESSAGE);
									close();
									return;
								}

								System.out.println("[SERVER] Invio al client: " + die.toString());

								player.setRespawned(false);
							}
						}
					}

					try {
						Thread.sleep(5);
					} catch (InterruptedException e) {
						close();
						//e.printStackTrace();
						
					}
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
							Object temp;
							Packet pkg = null;
							try {
								temp = msg.receiveObject();
								if (temp instanceof Packet)
									pkg = (Packet) temp;
								else
									System.out.println(":^)");
							} catch (IOException e) {
								//System.err.println("[SERVER] Client disconnesso...");
								JOptionPane.showMessageDialog(
								        null, "Connection Timeout.", "Error", JOptionPane.ERROR_MESSAGE);
								close();
								return;
							}

							if (pkg != null) {
								msg.HandlePacket(pkg, game.level);
								System.out.println("[SERVER] ricevo dal client: " + pkg.toString());
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
				}
			});
			t2.start();

		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	public boolean isConnected() {
		return connected;
	}
	
	public void close() {
		try {

			listener.close();
			if (socket != null) {
				msg.close();
				socket.close();
			}

			System.out.println("[SERVER] Server chiuso.");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} finally {
			connected = false;
			
			t1.interrupt();
			t2.interrupt();
		}

	}
}
