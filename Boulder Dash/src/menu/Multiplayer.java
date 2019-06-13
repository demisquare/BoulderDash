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
import javax.swing.JLabel;
import javax.swing.JPanel;

import audio.Music;

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

	public boolean turn_back = false;
	public boolean client_selected = false;
	public boolean server_selected = false;

	public Multiplayer() {
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
					.getScaledInstance(297, 61, Image.SCALE_SMOOTH);

			create_game_SELECTED = ImageIO
					.read(new File("." + File.separator + "resources" + File.separator + "assets" + File.separator
							+ "Menu" + File.separator + "MultiPage" + File.separator + "create_game_SELECTED.png"))
					.getScaledInstance(297, 61, Image.SCALE_SMOOTH);

			join_game = ImageIO
					.read(new File("." + File.separator + "resources" + File.separator + "assets" + File.separator
							+ "Menu" + File.separator + "MultiPage" + File.separator + "join_game.png"))
					.getScaledInstance(313, 61, Image.SCALE_SMOOTH);

			join_game_SELECTED = ImageIO
					.read(new File("." + File.separator + "resources" + File.separator + "assets" + File.separator
							+ "Menu" + File.separator + "MultiPage" + File.separator + "join_game_SELECTED.png"))
					.getScaledInstance(313, 61, Image.SCALE_SMOOTH);

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
					turn_back = true;
					ARROW_BACK_scaled.setIcon(new ImageIcon(arrow_back));
					revalidate();
					repaint();
				}

				@Override
				public void mouseExited(MouseEvent e) {
					Music.playTone("hover");
					ARROW_BACK_scaled.setIcon(new ImageIcon(arrow_back));
					revalidate();
					repaint();
				}

				@Override
				public void mouseEntered(MouseEvent e) {
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
					server_selected = true;
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
					server_selected = true;
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
			ARROW_BACK_scaled.setBounds(100, 90, 350, 150);;
			ARROW_BACK_scaled.setBounds(5, 5, 146, 97);
			ARROW_BACK_scaled.setBounds(5, 5, 146, 97);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0, 0, 1280, 720, null);
	}
}
