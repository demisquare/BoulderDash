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
import view.Game;

public class You_Win extends JPanel {

	private static final long serialVersionUID = 1L;

	BufferedImage background;
	Image Menu;
	Image Menu_SELECTED;

	JLabel MENU_scaled;

	private void menu(JFrame frame, Menu menu) {
		frame.remove(this);
		frame.setContentPane(menu);
		frame.revalidate();
		frame.repaint();
	}

	public void check_resize() {
		Toolkit tk = Toolkit.getDefaultToolkit();
		double xSize = tk.getScreenSize().getWidth();
		double ySize = tk.getScreenSize().getHeight();
		
		MENU_scaled.setIcon(new ImageIcon(Scaling.get(Menu, 250, 60, Options.full_screen)));
		
		Scaling.set(MENU_scaled, 490, 560, 250, 60, Options.full_screen);
	}
	
	public You_Win(JFrame frame, Game game, Menu menu) {
		try {
			background = ImageIO.read(new File("." + File.separator + "resources" + File.separator + "assets"
					+ File.separator + "Menu" + File.separator + "YouWinPage" + File.separator + "background.png"));

			Menu = ImageIO
					.read(new File("." + File.separator + "resources" + File.separator + "assets" + File.separator
							+ "Menu" + File.separator + "YouWinPage" + File.separator + "Menu.png")).getScaledInstance(250, 60, Image.SCALE_SMOOTH);

			Menu_SELECTED = ImageIO
					.read(new File("." + File.separator + "resources" + File.separator + "assets" + File.separator
							+ "Menu" + File.separator + "YouWinPage" + File.separator + "Menu_SELECTED.png")).getScaledInstance(250, 60, Image.SCALE_SMOOTH);
			
			

			MENU_scaled = new JLabel(new ImageIcon(Menu));


			MENU_scaled.addMouseListener(new MouseListener() {

				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mousePressed(MouseEvent e) {
					Music.playTone("select");
					menu(frame, menu);
					MENU_scaled.setIcon(new ImageIcon(Scaling.get(Menu, 250, 60, Options.full_screen)));
					revalidate();
					repaint();
				}

				@Override
				public void mouseExited(MouseEvent e) {
					MENU_scaled.setIcon(new ImageIcon(Scaling.get(Menu, 250, 60, Options.full_screen)));
					revalidate();
					repaint();
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					Music.playTone("hover");
					MENU_scaled.setIcon(new ImageIcon(Scaling.get(Menu_SELECTED, 250, 60, Options.full_screen)));
					revalidate();
					repaint();
				}

				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub

				}
			});

			this.setLayout(null);
			this.add(MENU_scaled);
			
			
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
