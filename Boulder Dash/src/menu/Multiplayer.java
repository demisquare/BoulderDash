//AUTORE: Davide Gena

package menu;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import audio.Music;
import network.SocketClient;
import network.SocketServer;
import view.Game;

public class Multiplayer extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final String MultiPagePath = 
			"." + File.separator + 
			"resources" + File.separator + 
			"assets" + File.separator + 
			"Menu" + File.separator + 
			"MultiPage" + File.separator;

	BufferedImage background;
	Image arrow_back;
	Image arrow_back_SELECTED;
	Image create_game;
	Image create_game_SELECTED;
	Image join_game;
	Image join_game_SELECTED;

	JLabel ARROW_BACK_scaled;
	JLabel CREATE_GAME_scaled;
	JLabel JOIN_GAME_scaled;

	private SocketServer socketServer;
	private SocketClient socketClient;
	
	public Multiplayer(JFrame frame, Game game, Menu menu) {
		super();
		
		image_init();
		label_init();
		MouseListener_init(frame, game, menu);
		panel_init();
	}

	public void check_resize() {
		Scaling.set(ARROW_BACK_scaled, 5, 5, 146, 97, Options.full_screen);
		Scaling.set(CREATE_GAME_scaled, 300, 200, 698, 79, Options.full_screen);
		Scaling.set(JOIN_GAME_scaled, 300, 400, 540, 79, Options.full_screen);
		
		ARROW_BACK_scaled.setIcon(new ImageIcon(Scaling.get(arrow_back, 120, 80, Options.full_screen)));
		CREATE_GAME_scaled.setIcon(new ImageIcon(Scaling.get(create_game, 698, 79, Options.full_screen)));
		JOIN_GAME_scaled.setIcon(new ImageIcon(Scaling.get(join_game, 540, 79, Options.full_screen)));
	}
	
	private void turn_back(JFrame frame, Menu menu) throws InterruptedException {

		if (Options.multiplayer) {
			if (Options.host && socketClient.isConnected()) {
				socketClient.close();
				Options.host = false;
			} else if(socketServer.isConnected())
				socketServer.close();
			Options.multiplayer = false;
		}

		frame.remove(this);
		frame.setContentPane(menu);
		frame.revalidate();
		frame.repaint();
	}

	private void client_selected(JFrame frame, Menu menu, Game game) throws InterruptedException {

		Options.multiplayer = true;
		Options.host = true;

		game.launchGame();

		socketClient = new SocketClient(game);
		socketClient.connect();

		if (socketClient.isConnected()) {

			synchronized (this) {
				Music.setSong(Music.gameSong);
			}

			frame.remove(this);
			frame.setContentPane(game);
			game.level.requestFocusInWindow();
			game.score.setMulti(this);
			frame.revalidate();
			frame.repaint();
		}

	}

	public SocketServer getSocketServer() {
		return socketServer;
	}

	public SocketClient getSocketClient() {
		return socketClient;
	}

	private void server_selected(JFrame frame, Menu menu, Game game) throws InterruptedException {

		Options.multiplayer = true;
		Options.host = false;

		game.launchGame();

		socketServer = new SocketServer(game);
		socketServer.connect();

		if (socketServer.isConnected()) {

			synchronized (this) {
				Music.setSong(Music.gameSong);
			}

			frame.remove(this);
			frame.setContentPane(game);
			game.level.requestFocusInWindow();
			game.score.setMulti(this);
			frame.revalidate();
			frame.repaint();
		}

	}

	private void image_init() {
		try {
			background = ImageIO.read(new File(MultiPagePath + "background.png"));
			arrow_back = ImageIO.read(new File(MultiPagePath + "arrow_back.png")).getScaledInstance(120, 80,
					Image.SCALE_SMOOTH);
			arrow_back_SELECTED = ImageIO.read(new File(MultiPagePath + "arrow_back_SELECTED.png"))
					.getScaledInstance(120, 80, Image.SCALE_SMOOTH);
			create_game = ImageIO.read(new File(MultiPagePath + "create_game.png")).getScaledInstance(698, 79,
					Image.SCALE_SMOOTH);
			create_game_SELECTED = ImageIO.read(new File(MultiPagePath + "create_game_SELECTED.png"))
					.getScaledInstance(698, 79, Image.SCALE_SMOOTH);
			join_game = ImageIO.read(new File(MultiPagePath + "join_game.png")).getScaledInstance(540, 79,
					Image.SCALE_SMOOTH);
			join_game_SELECTED = ImageIO.read(new File(MultiPagePath + "join_game_SELECTED.png")).getScaledInstance(540,
					79, Image.SCALE_SMOOTH);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void label_init() {
		ARROW_BACK_scaled = new JLabel(new ImageIcon(arrow_back));
		CREATE_GAME_scaled = new JLabel(new ImageIcon(create_game));
		JOIN_GAME_scaled = new JLabel(new ImageIcon(join_game));
	}
	
	private void MouseListener_init(JFrame frame, Game game, Menu menu) {
		ARROW_BACK_scaled.addMouseListener(new MouseListener() {

			@Override
			public void mousePressed(MouseEvent e) {

				synchronized (this) {
					Music.playTone("select");
				}

				try {
					turn_back(frame, menu);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

				ARROW_BACK_scaled.setIcon(new ImageIcon(Scaling.get(arrow_back, 120, 80, Options.full_screen)));
				revalidate();
				repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				ARROW_BACK_scaled.setIcon(new ImageIcon(Scaling.get(arrow_back, 120, 80, Options.full_screen)));
				revalidate();
				repaint();
			}

			@Override
			public void mouseEntered(MouseEvent e) {

				synchronized (this) {
					Music.playTone("hover");
				}

				ARROW_BACK_scaled.setIcon(new ImageIcon(Scaling.get(arrow_back_SELECTED, 120, 80, Options.full_screen)));
				revalidate();
				repaint();
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}
		});

		CREATE_GAME_scaled.addMouseListener(new MouseListener() {

			@Override
			public void mousePressed(MouseEvent e) {

				synchronized (this) {
					Music.playTone("select");
				}

				try {
					server_selected(frame, menu, game);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

				CREATE_GAME_scaled.setIcon(new ImageIcon(Scaling.get(create_game, 698, 79, Options.full_screen)));
				revalidate();
				repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				CREATE_GAME_scaled.setIcon(new ImageIcon(Scaling.get(create_game, 698, 79, Options.full_screen)));
				revalidate();
				repaint();
			}

			@Override
			public void mouseEntered(MouseEvent e) {

				synchronized (this) {
					Music.playTone("hover");
				}

				CREATE_GAME_scaled.setIcon(new ImageIcon(Scaling.get(create_game_SELECTED, 698, 79, Options.full_screen)));
				revalidate();
				repaint();
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}
		});

		JOIN_GAME_scaled.addMouseListener(new MouseListener() {

			@Override
			public void mousePressed(MouseEvent e) {

				synchronized (this) {
					Music.playTone("select");
				}

				try {
					client_selected(frame, menu, game);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

				JOIN_GAME_scaled.setIcon(new ImageIcon(Scaling.get(join_game, 540, 79, Options.full_screen)));
				revalidate();
				repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				JOIN_GAME_scaled.setIcon(new ImageIcon(Scaling.get(join_game, 540, 79, Options.full_screen)));
				revalidate();
				repaint();
			}

			@Override
			public void mouseEntered(MouseEvent e) {

				synchronized (this) {
					Music.playTone("hover");
				}

				JOIN_GAME_scaled.setIcon(new ImageIcon(Scaling.get(join_game_SELECTED, 540, 79, Options.full_screen)));
				revalidate();
				repaint();
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}
		});
	}
	
	private void panel_init() {
		this.setLayout(null);
		this.add(ARROW_BACK_scaled);
		this.add(CREATE_GAME_scaled);
		this.add(JOIN_GAME_scaled);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), null);
	}
}
