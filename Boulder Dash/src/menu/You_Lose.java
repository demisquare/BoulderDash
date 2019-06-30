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

public class You_Lose extends JPanel {

	private static final long serialVersionUID = 1L;

	private static final String YouLosePath =
			"." + File.separator + 
			"resources" + File.separator + 
			"assets" + File.separator + 
			"Menu" + File.separator + 
			"YouLosePage" + File.separator;
	
	BufferedImage background;
	Image Menu;
	Image Menu_SELECTED;
	Image Retry;
	Image Retry_SELECTED;

	JLabel MENU_scaled;
	JLabel RETRY_scaled;

	private void menu(JFrame frame, Menu menu) {
		frame.remove(this);
		frame.setContentPane(menu);
		frame.revalidate();
		frame.repaint();
	}
	private void retry(JFrame frame, Game game) {
		Music.setSong(Music.gameSong);
		frame.remove(this);
		frame.setContentPane(game);
		game.level.requestFocusInWindow();
		frame.revalidate();
		frame.repaint();
	}

	public You_Lose(JFrame frame, Game game, Menu menu) {
		try {
			background = ImageIO.read(new File(YouLosePath + "background.png"));
			Menu = ImageIO.read(new File(YouLosePath + "Menu.png")).getScaledInstance(698, 79, Image.SCALE_SMOOTH);
			Menu_SELECTED = ImageIO.read(new File(YouLosePath + "Menu_SELECTED.png")).getScaledInstance(698, 79, Image.SCALE_SMOOTH);
			Retry = ImageIO.read(new File(YouLosePath + "Retry.png")).getScaledInstance(540, 79, Image.SCALE_SMOOTH);
			Retry_SELECTED = ImageIO.read(new File(YouLosePath + "Retry_SELECTED.png")).getScaledInstance(540, 79, Image.SCALE_SMOOTH);

			MENU_scaled = new JLabel(new ImageIcon(Menu));
			RETRY_scaled = new JLabel(new ImageIcon(Retry));

			MENU_scaled.addMouseListener(new MouseListener() {

				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mousePressed(MouseEvent e) {
					Music.playTone("select");
					menu(frame, menu);
					MENU_scaled.setIcon(new ImageIcon(Menu));
					revalidate();
					repaint();
				}

				@Override
				public void mouseExited(MouseEvent e) {
					MENU_scaled.setIcon(new ImageIcon(Menu));
					revalidate();
					repaint();
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					Music.playTone("hover");
					MENU_scaled.setIcon(new ImageIcon(Menu_SELECTED));
					revalidate();
					repaint();
				}

				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub

				}
			});
			RETRY_scaled.addMouseListener(new MouseListener() {

				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mousePressed(MouseEvent e) {
					Music.playTone("select");
					retry(frame, game);
					RETRY_scaled.setIcon(new ImageIcon(Retry));
					revalidate();
					repaint();
				}

				@Override
				public void mouseExited(MouseEvent e) {
					RETRY_scaled.setIcon(new ImageIcon(Retry));
					revalidate();
					repaint();
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					Music.playTone("hover");
					RETRY_scaled.setIcon(new ImageIcon(Retry_SELECTED));
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
			this.add(RETRY_scaled);
			
			Toolkit tk = Toolkit.getDefaultToolkit();
			double xSize = tk.getScreenSize().getWidth();
			double ySize = tk.getScreenSize().getHeight();
			
			new Thread(new Runnable() {
				@Override
				public void run() {
					while (true) {
						if(!Options.full_screen) {
							MENU_scaled.setBounds(200, 200, 698, 79);
							RETRY_scaled.setBounds(300, 200, 698, 79);
						}
						else if(Options.full_screen) {
							MENU_scaled.setBounds((int)(200*(xSize/1280)), (int)(200*(ySize/720)), 698, 79);
							RETRY_scaled.setBounds((int)(300*(xSize/1280)), (int)(200*(ySize/720)), 698, 79);
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
