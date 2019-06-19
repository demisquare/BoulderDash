package menu;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
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

	private void turn_back(JFrame frame, Menu menu) {
		frame.remove(this);
		frame.setContentPane(menu);
		frame.revalidate();
		frame.repaint();
	}
	private void client_selected(JFrame frame, Menu menu, Game game) {
		Music.setSong(Music.gameSong);
		socketClient = new SocketClient(game);
		socketClient.connect();
		frame.remove(this);
		frame.setContentPane(game);
		game.level.requestFocusInWindow();
		frame.revalidate();
		frame.repaint();
	}
	private void server_selected(JFrame frame, Menu menu, Game game) {
		Music.setSong(Music.gameSong);
		socketServer = new SocketServer(game);
		socketServer.connect();
		frame.remove(this);
		frame.setContentPane(game);
		game.level.requestFocusInWindow();
		frame.revalidate();
		frame.repaint();
	}

	public Multiplayer(JFrame frame, Game game, Menu menu) {
		try {
			background = ImageIO.read(new File("." + File.separator + "resources" + File.separator + "assets"
					+ File.separator + "Menu" + File.separator + "MultiPage" + File.separator + "background.png"));

			arrow_back = ImageIO
					.read(new File("." + File.separator + "resources" + File.separator + "assets" + File.separator
							+ "Menu" + File.separator + "MultiPage" + File.separator + "arrow_back.png"))
					.getScaledInstance(120, 80, Image.SCALE_SMOOTH);

			arrow_back_SELECTED = ImageIO
					.read(new File("." + File.separator + "resources" + File.separator + "assets" + File.separator
							+ "Menu" + File.separator + "MultiPage" + File.separator + "arrow_back_SELECTED.png"))
					.getScaledInstance(120, 80, Image.SCALE_SMOOTH);

			create_game = ImageIO
					.read(new File("." + File.separator + "resources" + File.separator + "assets" + File.separator
							+ "Menu" + File.separator + "MultiPage" + File.separator + "create_game.png"))
					.getScaledInstance(698, 79, Image.SCALE_SMOOTH);

			create_game_SELECTED = ImageIO
					.read(new File("." + File.separator + "resources" + File.separator + "assets" + File.separator
							+ "Menu" + File.separator + "MultiPage" + File.separator + "create_game_SELECTED.png"))
					.getScaledInstance(698, 79, Image.SCALE_SMOOTH);

			join_game = ImageIO
					.read(new File("." + File.separator + "resources" + File.separator + "assets" + File.separator
							+ "Menu" + File.separator + "MultiPage" + File.separator + "join_game.png"))
					.getScaledInstance(540, 79, Image.SCALE_SMOOTH);

			join_game_SELECTED = ImageIO
					.read(new File("." + File.separator + "resources" + File.separator + "assets" + File.separator
							+ "Menu" + File.separator + "MultiPage" + File.separator + "join_game_SELECTED.png"))
					.getScaledInstance(540, 79, Image.SCALE_SMOOTH);

			ARROW_BACK_scaled = new JLabel(new ImageIcon(arrow_back));
			CREATE_GAME_scaled = new JLabel(new ImageIcon(create_game));
			JOIN_GAME_scaled = new JLabel(new ImageIcon(join_game));

			ARROW_BACK_scaled.addMouseListener(new MouseListener() {

				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mousePressed(MouseEvent e) {
					Music.playTone("select");
					turn_back(frame, menu);
					ARROW_BACK_scaled.setIcon(new ImageIcon(arrow_back));
					revalidate();
					repaint();
				}

				@Override
				public void mouseExited(MouseEvent e) {
					ARROW_BACK_scaled.setIcon(new ImageIcon(arrow_back));
					revalidate();
					repaint();
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					Music.playTone("hover");
					ARROW_BACK_scaled.setIcon(new ImageIcon(arrow_back_SELECTED));
					revalidate();
					repaint();
				}

				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub

				}
			});
			CREATE_GAME_scaled.addMouseListener(new MouseListener() {

				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mousePressed(MouseEvent e) {
					Music.playTone("select");
					server_selected(frame, menu, game);
					CREATE_GAME_scaled.setIcon(new ImageIcon(create_game));
					revalidate();
					repaint();
				}

				@Override
				public void mouseExited(MouseEvent e) {
					CREATE_GAME_scaled.setIcon(new ImageIcon(create_game));
					revalidate();
					repaint();
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					Music.playTone("hover");
					CREATE_GAME_scaled.setIcon(new ImageIcon(create_game_SELECTED));
					revalidate();
					repaint();
				}

				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub

				}
			});
			JOIN_GAME_scaled.addMouseListener(new MouseListener() {

				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mousePressed(MouseEvent e) {
					Music.playTone("select");
					client_selected(frame, menu, game);
					JOIN_GAME_scaled.setIcon(new ImageIcon(join_game));
					revalidate();
					repaint();
				}

				@Override
				public void mouseExited(MouseEvent e) {
					JOIN_GAME_scaled.setIcon(new ImageIcon(join_game));
					revalidate();
					repaint();
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					Music.playTone("hover");
					JOIN_GAME_scaled.setIcon(new ImageIcon(join_game_SELECTED));
					revalidate();
					repaint();
				}

				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub

				}
			});

			this.setLayout(null);
			this.add(ARROW_BACK_scaled);
			this.add(CREATE_GAME_scaled);
			this.add(JOIN_GAME_scaled);
			
			Toolkit tk = Toolkit.getDefaultToolkit();
			double xSize = tk.getScreenSize().getWidth();
			double ySize = tk.getScreenSize().getHeight();
			
			new Thread(new Runnable() {
				@Override
				public void run() {
					while (true) {
						if(!Options.full_screen) {
							ARROW_BACK_scaled.setBounds(5, 5, 146, 97);
							CREATE_GAME_scaled.setBounds(300, 200, 698, 79);
							JOIN_GAME_scaled.setBounds(300, 400, 540, 79);
						}
						else if(Options.full_screen) {
							ARROW_BACK_scaled.setBounds((int)(5*(xSize/1280)), (int)(5*(ySize/720)), 146, 97);
							CREATE_GAME_scaled.setBounds((int)(300*(xSize/1280)), (int)(200*(ySize/720)), 698, 79);
							JOIN_GAME_scaled.setBounds((int)(300*(xSize/1280)), (int)(400*(ySize/720)), 540, 79);
						}
						try {
							Thread.sleep(34);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}).start();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), null);
	}
}
