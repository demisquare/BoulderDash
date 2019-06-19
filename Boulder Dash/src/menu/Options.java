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

public class Options extends JPanel {

	private static final long serialVersionUID = 1L;

	BufferedImage background;
	Image arrow_back;
	Image arrow_back_SELECTED;
	Image paradiso;
	Image paradiso_SELECTED;
	Image purgatorio;
	Image purgatorio_SELECTED;
	Image inferno;
	Image inferno_SELECTED;
	Image windowed;
	Image windowed_SELECTED;
	Image fullscreen;
	Image fullscreen_SELECTED;
	Image music_unchecked;
	Image music_checked;

	JLabel ARROW_BACK_scaled;
	JLabel PARADISO_scaled;
	JLabel PURGATORIO_scaled;
	JLabel INFERNO_scaled;
	JLabel WINDOWED_scaled;
	JLabel FULLSCREEN_scaled;
	JLabel MUSIC_check;

	private void turn_back(JFrame frame, Menu menu) {
		frame.remove(this);
		frame.setContentPane(menu);
		frame.revalidate();
		frame.repaint();
	}
	private void full_screen(JFrame frame) {
		frame.dispose();
		if (!frame.isUndecorated())
			frame.setUndecorated(true);
		frame.setVisible(true);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}
	
	private void windowed(JFrame frame) {
		frame.dispose();
		if (frame.isUndecorated())
			frame.setUndecorated(false);
		frame.setVisible(true);
		frame.setSize(1280, 749);
	}
	
	public static boolean music = true;
	public static boolean full_screen = false;
	

	enum Difficulty {
		paradiso, purgatorio, inferno;
	}

	public Difficulty difficulty = Difficulty.purgatorio;

	public Options(JFrame frame, Menu menu) {
		try {
			background = ImageIO.read(new File("." + File.separator + "resources" + File.separator + "assets"
					+ File.separator + "Menu" + File.separator + "OptionsPage" + File.separator + "background.png"));

			arrow_back = ImageIO
					.read(new File("." + File.separator + "resources" + File.separator + "assets" + File.separator
							+ "Menu" + File.separator + "OptionsPage" + File.separator + "arrow_back.png"))
					.getScaledInstance(120, 80, Image.SCALE_SMOOTH);

			paradiso = ImageIO
					.read(new File("." + File.separator + "resources" + File.separator + "assets" + File.separator
							+ "Menu" + File.separator + "OptionsPage" + File.separator + "Paradiso.png"))
					.getScaledInstance(297, 61, Image.SCALE_SMOOTH);

			purgatorio = ImageIO
					.read(new File("." + File.separator + "resources" + File.separator + "assets" + File.separator
							+ "Menu" + File.separator + "OptionsPage" + File.separator + "Purgatorio.png"))
					.getScaledInstance(313, 61, Image.SCALE_SMOOTH);

			inferno = ImageIO
					.read(new File("." + File.separator + "resources" + File.separator + "assets" + File.separator
							+ "Menu" + File.separator + "OptionsPage" + File.separator + "Inferno.png"))
					.getScaledInstance(235, 61, Image.SCALE_SMOOTH);

			windowed = ImageIO
					.read(new File("." + File.separator + "resources" + File.separator + "assets" + File.separator
							+ "Menu" + File.separator + "OptionsPage" + File.separator + "Windowed.png"))
					.getScaledInstance(265, 41, Image.SCALE_SMOOTH);

			fullscreen = ImageIO
					.read(new File("." + File.separator + "resources" + File.separator + "assets" + File.separator
							+ "Menu" + File.separator + "OptionsPage" + File.separator + "FullScreen.png"))
					.getScaledInstance(305, 41, Image.SCALE_SMOOTH);

			arrow_back_SELECTED = ImageIO
					.read(new File("." + File.separator + "resources" + File.separator + "assets" + File.separator
							+ "Menu" + File.separator + "OptionsPage" + File.separator + "arrow_back_SELECTED.png"))
					.getScaledInstance(120, 80, Image.SCALE_SMOOTH);

			paradiso_SELECTED = ImageIO
					.read(new File("." + File.separator + "resources" + File.separator + "assets" + File.separator
							+ "Menu" + File.separator + "OptionsPage" + File.separator + "Paradiso_SELECTED.png"))
					.getScaledInstance(297, 61, Image.SCALE_SMOOTH);

			purgatorio_SELECTED = ImageIO
					.read(new File("." + File.separator + "resources" + File.separator + "assets" + File.separator
							+ "Menu" + File.separator + "OptionsPage" + File.separator + "Purgatorio_SELECTED.png"))
					.getScaledInstance(313, 61, Image.SCALE_SMOOTH);

			inferno_SELECTED = ImageIO
					.read(new File("." + File.separator + "resources" + File.separator + "assets" + File.separator
							+ "Menu" + File.separator + "OptionsPage" + File.separator + "Inferno_SELECTED.png"))
					.getScaledInstance(235, 61, Image.SCALE_SMOOTH);

			windowed_SELECTED = ImageIO
					.read(new File("." + File.separator + "resources" + File.separator + "assets" + File.separator
							+ "Menu" + File.separator + "OptionsPage" + File.separator + "Windowed_SELECTED.png"))
					.getScaledInstance(265, 41, Image.SCALE_SMOOTH);

			fullscreen_SELECTED = ImageIO
					.read(new File("." + File.separator + "resources" + File.separator + "assets" + File.separator
							+ "Menu" + File.separator + "OptionsPage" + File.separator + "FullScreen_SELECTED.png"))
					.getScaledInstance(305, 41, Image.SCALE_SMOOTH);

			music_unchecked = ImageIO
					.read(new File("." + File.separator + "resources" + File.separator + "assets" + File.separator
							+ "Menu" + File.separator + "OptionsPage" + File.separator + "unchecked.png"))
					.getScaledInstance(50, 50, Image.SCALE_SMOOTH);

			music_checked = ImageIO
					.read(new File("." + File.separator + "resources" + File.separator + "assets" + File.separator
							+ "Menu" + File.separator + "OptionsPage" + File.separator + "checked.png"))
					.getScaledInstance(50, 50, Image.SCALE_SMOOTH);

			ARROW_BACK_scaled = new JLabel(new ImageIcon(arrow_back));
			PARADISO_scaled = new JLabel(new ImageIcon(paradiso));
			PURGATORIO_scaled = new JLabel(new ImageIcon(purgatorio_SELECTED));
			INFERNO_scaled = new JLabel(new ImageIcon(inferno));
			WINDOWED_scaled = new JLabel(new ImageIcon(windowed_SELECTED));
			FULLSCREEN_scaled = new JLabel(new ImageIcon(fullscreen));
			
			if(music) {
				MUSIC_check = new JLabel(new ImageIcon(music_checked));
			} else {
				MUSIC_check = new JLabel(new ImageIcon(music_unchecked));
			}
			
			
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
			PARADISO_scaled.addMouseListener(new MouseListener() {

				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mousePressed(MouseEvent e) {
					Music.playTone("select");
					if (difficulty != Difficulty.paradiso) {
						difficulty = Difficulty.paradiso;
						PARADISO_scaled.setIcon(new ImageIcon(paradiso_SELECTED));
						PURGATORIO_scaled.setIcon(new ImageIcon(purgatorio));
						INFERNO_scaled.setIcon(new ImageIcon(inferno));
						revalidate();
						repaint();
					}
				}

				@Override
				public void mouseExited(MouseEvent e) {
					if (difficulty != Difficulty.paradiso) {
						PARADISO_scaled.setIcon(new ImageIcon(paradiso));
						revalidate();
						repaint();
					}
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					Music.playTone("hover");
					if (difficulty != Difficulty.paradiso) {
						PARADISO_scaled.setIcon(new ImageIcon(paradiso_SELECTED));
						revalidate();
						repaint();
					}
				}

				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub

				}
			});
			PURGATORIO_scaled.addMouseListener(new MouseListener() {

				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mousePressed(MouseEvent e) {
					Music.playTone("select");
					if (difficulty != Difficulty.purgatorio) {
						difficulty = Difficulty.purgatorio;
						PARADISO_scaled.setIcon(new ImageIcon(paradiso));
						PURGATORIO_scaled.setIcon(new ImageIcon(purgatorio_SELECTED));
						INFERNO_scaled.setIcon(new ImageIcon(inferno));
						revalidate();
						repaint();
					}
				}

				@Override
				public void mouseExited(MouseEvent e) {
					if (difficulty != Difficulty.purgatorio) {
						PURGATORIO_scaled.setIcon(new ImageIcon(purgatorio));
						revalidate();
						repaint();
					}
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					Music.playTone("hover");
					if (difficulty != Difficulty.purgatorio) {
						PURGATORIO_scaled.setIcon(new ImageIcon(purgatorio_SELECTED));
						revalidate();
						repaint();
					}
				}

				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub

				}
			});
			INFERNO_scaled.addMouseListener(new MouseListener() {

				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mousePressed(MouseEvent e) {
					Music.playTone("select");
					if (difficulty != Difficulty.inferno) {
						difficulty = Difficulty.inferno;
						PARADISO_scaled.setIcon(new ImageIcon(paradiso));
						PURGATORIO_scaled.setIcon(new ImageIcon(purgatorio));
						INFERNO_scaled.setIcon(new ImageIcon(inferno_SELECTED));
						revalidate();
						repaint();
					}
				}

				@Override
				public void mouseExited(MouseEvent e) {
					if (difficulty != Difficulty.inferno) {
						INFERNO_scaled.setIcon(new ImageIcon(inferno));
						revalidate();
						repaint();
					}
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					Music.playTone("hover");
					if (difficulty != Difficulty.inferno) {
						INFERNO_scaled.setIcon(new ImageIcon(inferno_SELECTED));
						revalidate();
						repaint();
					}
				}

				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub

				}
			});
			WINDOWED_scaled.addMouseListener(new MouseListener() {

				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mousePressed(MouseEvent e) {
					Music.playTone("select");
					windowed(frame);
					FULLSCREEN_scaled.setIcon(new ImageIcon(fullscreen));
					WINDOWED_scaled.setIcon(new ImageIcon(windowed_SELECTED));
					revalidate();
					repaint();
					full_screen = false;
				}

				@Override
				public void mouseExited(MouseEvent e) {
					if (full_screen) {
						WINDOWED_scaled.setIcon(new ImageIcon(windowed));
						revalidate();
						repaint();
					}
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					Music.playTone("hover");
					if (full_screen) {
						WINDOWED_scaled.setIcon(new ImageIcon(windowed_SELECTED));
						revalidate();
						repaint();
					}
				}

				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub

				}
			});
			FULLSCREEN_scaled.addMouseListener(new MouseListener() {

				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mousePressed(MouseEvent e) {
					Music.playTone("select");
					full_screen(frame);
					FULLSCREEN_scaled.setIcon(new ImageIcon(fullscreen_SELECTED));
					WINDOWED_scaled.setIcon(new ImageIcon(windowed));
					revalidate();
					repaint();
					full_screen=true;

				}

				@Override
				public void mouseExited(MouseEvent e) {
					if (!full_screen) {
						FULLSCREEN_scaled.setIcon(new ImageIcon(fullscreen));
						revalidate();
						repaint();
					}
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					Music.playTone("hover");
					if (!full_screen) {
						FULLSCREEN_scaled.setIcon(new ImageIcon(fullscreen_SELECTED));
						revalidate();
						repaint();
					}
				}

				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub

				}
			});
			MUSIC_check.addMouseListener(new MouseListener() {

				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mousePressed(MouseEvent e) {
					Music.playTone("select");
					if (music) {
						music = false;
						MUSIC_check.setIcon(new ImageIcon(music_unchecked));
					} else if (!music) {
						music = true;
						MUSIC_check.setIcon(new ImageIcon(music_checked));
					}
					revalidate();
					repaint();
				}

				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub

				}
			});

			this.setLayout(null);
			this.add(ARROW_BACK_scaled);
			this.add(PARADISO_scaled);
			this.add(PURGATORIO_scaled);
			this.add(INFERNO_scaled);
			this.add(WINDOWED_scaled);
			this.add(FULLSCREEN_scaled);
			this.add(MUSIC_check);

			
			Toolkit tk = Toolkit.getDefaultToolkit();
			int xSize = ((int) tk.getScreenSize().getWidth());
			int ySize = ((int) tk.getScreenSize().getHeight());
			
			new Thread(new Runnable() {
				@Override
				public void run() {
					while (true) {
						if(!full_screen) {
							ARROW_BACK_scaled.setBounds(5, 5, 146, 97);
							PARADISO_scaled.setBounds(100, 90, 350, 150);
							PURGATORIO_scaled.setBounds(460, 90, 350, 150);
							INFERNO_scaled.setBounds(800, 90, 350, 150);
							WINDOWED_scaled.setBounds(280, 280, 350, 150);
							FULLSCREEN_scaled.setBounds(620, 280, 350, 150);
							MUSIC_check.setBounds(720, 430, 50, 50);
						}
						else if(full_screen) {
							ARROW_BACK_scaled.setBounds(5+(xSize-1280)/3, 5+(ySize-720)/3, 146, 97);
							PARADISO_scaled.setBounds(100+(xSize-1280)/3, 90+(ySize-720)/3, 350, 150);
							PURGATORIO_scaled.setBounds(460+(xSize-1280)/3, 90+(ySize-720)/3, 350, 150);
							INFERNO_scaled.setBounds(800+(xSize-1280)/3, 90+(ySize-720)/3, 350, 150);
							WINDOWED_scaled.setBounds(280+(xSize-1280)/3, 280+(ySize-720)/3, 350, 150);
							FULLSCREEN_scaled.setBounds(620+(xSize-1280)/3, 280+(ySize-720)/3, 350, 150);
							MUSIC_check.setBounds(720+(xSize-1280)/3, 430+(ySize-720)/3, 50, 50);
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
