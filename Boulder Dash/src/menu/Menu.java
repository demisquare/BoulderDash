package menu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import audio.Music;

public class Menu extends JPanel{

	private static final long serialVersionUID = 1L;
	Long en = 2L;
	BufferedImage background;

	Image START;
	Image MULTI;
	Image OPTIONS;
	Image CREDITS;
	Image EXIT;
	Image START_SELECTED;
	Image MULTI_SELECTED;
	Image OPTIONS_SELECTED;
	Image CREDITS_SELECTED;
	Image EXIT_SELECTED;

	// fattore di scala: risoluzione/1.5 approssimato per eccesso
	JLabel START_scaled;
	JLabel MULTI_scaled;
	JLabel OPTIONS_scaled;
	JLabel CREDITS_scaled;
	JLabel EXIT_scaled;

	JPanel menu_choices;

	public boolean start_selected = false;
	public boolean credits_selected = false;
	public boolean options_selected = false;
	public boolean multi_selected = false;

	public Menu() {
		
		Music.backgroundMusic = Music.menuSong;
		
		try {
			background = ImageIO.read(
					new File("." + File.separator + "resources" + File.separator + "assets" + File.separator + "Menu"
							+ File.separator + "MainMenuPage" + File.separator + "MainMenuPage_Background.png"));

			START = ImageIO
					.read(new File("." + File.separator + "resources" + File.separator + "assets" + File.separator
							+ "Menu" + File.separator + "MainMenuPage" + File.separator + "MainMenuPage_START.png"))
					.getScaledInstance(191, 55, Image.SCALE_SMOOTH);

			MULTI = ImageIO
					.read(new File("." + File.separator + "resources" + File.separator + "assets" + File.separator
							+ "Menu" + File.separator + "MainMenuPage" + File.separator + "MainMenuPage_MULTI.png"))
					.getScaledInstance(423, 55, Image.SCALE_SMOOTH);

			OPTIONS = ImageIO
					.read(new File("." + File.separator + "resources" + File.separator + "assets" + File.separator
							+ "Menu" + File.separator + "MainMenuPage" + File.separator + "MainMenuPage_OPTIONS.png"))
					.getScaledInstance(272, 54, Image.SCALE_SMOOTH);

			CREDITS = ImageIO
					.read(new File("." + File.separator + "resources" + File.separator + "assets" + File.separator
							+ "Menu" + File.separator + "MainMenuPage" + File.separator + "MainMenuPage_CREDITS.png"))
					.getScaledInstance(267, 55, Image.SCALE_SMOOTH);

			EXIT = ImageIO
					.read(new File("." + File.separator + "resources" + File.separator + "assets" + File.separator
							+ "Menu" + File.separator + "MainMenuPage" + File.separator + "MainMenuPage_EXIT.png"))
					.getScaledInstance(141, 50, Image.SCALE_SMOOTH);

			START_SELECTED = ImageIO.read(
					new File("." + File.separator + "resources" + File.separator + "assets" + File.separator + "Menu"
							+ File.separator + "MainMenuPage" + File.separator + "MainMenuPage_START_SELECTED.png"))
					.getScaledInstance(191, 55, Image.SCALE_SMOOTH);

			MULTI_SELECTED = ImageIO.read(
					new File("." + File.separator + "resources" + File.separator + "assets" + File.separator + "Menu"
							+ File.separator + "MainMenuPage" + File.separator + "MainMenuPage_MULTI_SELECTED.png"))
					.getScaledInstance(423, 55, Image.SCALE_SMOOTH);

			OPTIONS_SELECTED = ImageIO.read(
					new File("." + File.separator + "resources" + File.separator + "assets" + File.separator + "Menu"
							+ File.separator + "MainMenuPage" + File.separator + "MainMenuPage_OPTIONS_SELECTED.png"))
					.getScaledInstance(272, 54, Image.SCALE_SMOOTH);

			CREDITS_SELECTED = ImageIO.read(
					new File("." + File.separator + "resources" + File.separator + "assets" + File.separator + "Menu"
							+ File.separator + "MainMenuPage" + File.separator + "MainMenuPage_CREDITS_SELECTED.png"))
					.getScaledInstance(267, 55, Image.SCALE_SMOOTH);

			EXIT_SELECTED = ImageIO.read(
					new File("." + File.separator + "resources" + File.separator + "assets" + File.separator + "Menu"
							+ File.separator + "MainMenuPage" + File.separator + "MainMenuPage_EXIT_SELECTED.png"))
					.getScaledInstance(141, 50, Image.SCALE_SMOOTH);

			START_scaled = new JLabel(new ImageIcon(START));
			MULTI_scaled = new JLabel(new ImageIcon(MULTI));
			OPTIONS_scaled = new JLabel(new ImageIcon(OPTIONS));
			CREDITS_scaled = new JLabel(new ImageIcon(CREDITS));
			EXIT_scaled = new JLabel(new ImageIcon(EXIT));

			START_scaled.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					Music.playTone("hover");
					START_scaled.setIcon(new ImageIcon(START_SELECTED));
					revalidate();
					repaint();
				}

				@Override
				public void mouseExited(MouseEvent e) {
					START_scaled.setIcon(new ImageIcon(START));
					revalidate();
					repaint();
				}

				@Override
				public void mousePressed(MouseEvent e) {
					Music.playTone("select");
					start_selected = true;
					START_scaled.setIcon(new ImageIcon(START));
					revalidate();
					repaint();
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
				}
			});
			MULTI_scaled.addMouseListener(new MouseListener() {

				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					Music.playTone("select");
					multi_selected = true;
					MULTI_scaled.setIcon(new ImageIcon(MULTI));
					revalidate();
					repaint();
				}

				@Override
				public void mouseExited(MouseEvent e) {
					MULTI_scaled.setIcon(new ImageIcon(MULTI));
					revalidate();
					repaint();
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					Music.playTone("hover");
					MULTI_scaled.setIcon(new ImageIcon(MULTI_SELECTED));
					revalidate();
					repaint();
				}

				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub

				}
			});
			OPTIONS_scaled.addMouseListener(new MouseListener() {

				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mousePressed(MouseEvent e) {
					Music.playTone("select");
					options_selected = true;
					OPTIONS_scaled.setIcon(new ImageIcon(OPTIONS));
					revalidate();
					repaint();
				}

				@Override
				public void mouseExited(MouseEvent e) {
					OPTIONS_scaled.setIcon(new ImageIcon(OPTIONS));
					revalidate();
					repaint();
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					Music.playTone("hover");
					OPTIONS_scaled.setIcon(new ImageIcon(OPTIONS_SELECTED));
					revalidate();
					repaint();
				}

				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub

				}
			});
			CREDITS_scaled.addMouseListener(new MouseListener() {

				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mousePressed(MouseEvent e) {
					Music.playTone("select");
					credits_selected = true;
					CREDITS_scaled.setIcon(new ImageIcon(CREDITS));
					revalidate();
					repaint();
				}

				@Override
				public void mouseExited(MouseEvent e) {
					CREDITS_scaled.setIcon(new ImageIcon(CREDITS));
					revalidate();
					repaint();
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					Music.playTone("hover");
					CREDITS_scaled.setIcon(new ImageIcon(CREDITS_SELECTED));
					revalidate();
					repaint();
				}

				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub

				}
			});
			EXIT_scaled.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					Music.playTone("hover");
					EXIT_scaled.setIcon(new ImageIcon(EXIT_SELECTED));
					revalidate();
					repaint();
				}

				@Override
				public void mouseExited(MouseEvent e) {
					EXIT_scaled.setIcon(new ImageIcon(EXIT));
					revalidate();
					repaint();
				}

				@Override
				public void mousePressed(MouseEvent e) {
					Music.playTone("select");
					System.exit(0);
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
				}
			});

			menu_choices = new JPanel();
			menu_choices.setLayout(new BoxLayout(menu_choices, BoxLayout.Y_AXIS));

			menu_choices.add(START_scaled);
			menu_choices.add(MULTI_scaled);
			menu_choices.add(OPTIONS_scaled);
			menu_choices.add(CREDITS_scaled);
			menu_choices.add(EXIT_scaled);

			this.setLayout(null);
			this.add(menu_choices);
			menu_choices.setBackground(new Color(0, 0, 0, 0));
			menu_choices.setBounds((1280 / 2 - 430 / 2), 250, 430, 300);

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
