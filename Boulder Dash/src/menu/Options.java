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

public class Options extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private static final String OptionsPagePath =
			"." + File.separator + 
			"resources" + File.separator + 
			"assets" + File.separator
			+ "Menu" + File.separator + 
			"OptionsPage" + File.separator;
	
	public static boolean music = false;
	public static boolean full_screen = false;	
	
	public static boolean multiplayer = false;
	public static boolean host = false;

	public enum Difficulty {
		paradiso, purgatorio, inferno;
	}

	static public Difficulty difficulty = Difficulty.purgatorio;
	
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
	
	public Options(JFrame frame, Menu menu) {
		super();
		
		image_init();
		label_init();
		MouseListener_init(frame, menu);
		panel_init();	
	}
	
	public void check_resize() {
		
		Scaling.set(ARROW_BACK_scaled, 5, 5, 146, 97, Options.full_screen);
		Scaling.set(PARADISO_scaled, 100, 92, 350, 150, Options.full_screen);
		Scaling.set(PURGATORIO_scaled, 460, 90, 350, 150, Options.full_screen);
		Scaling.set(INFERNO_scaled, 800, 90, 350, 150, Options.full_screen);
		Scaling.set(WINDOWED_scaled, 280, 330, 265, 41, Options.full_screen);
		Scaling.set(FULLSCREEN_scaled, 620, 330, 305, 41, Options.full_screen);
		Scaling.set(MUSIC_check, 720, 430, 50, 50, Options.full_screen);		
		
		ARROW_BACK_scaled.setIcon(new ImageIcon(Scaling.get(arrow_back, 120, 80, Options.full_screen)));
		if(difficulty==Difficulty.paradiso) {
			PARADISO_scaled.setIcon(new ImageIcon(Scaling.get(paradiso_SELECTED, 285, 55, Options.full_screen)));
			PURGATORIO_scaled.setIcon(new ImageIcon(Scaling.get(purgatorio, 313, 61, Options.full_screen)));
			INFERNO_scaled.setIcon(new ImageIcon(Scaling.get(inferno, 235, 61, Options.full_screen)));
		}
		else if(difficulty==Difficulty.purgatorio) {
			PARADISO_scaled.setIcon(new ImageIcon(Scaling.get(paradiso, 285, 55, Options.full_screen)));
			PURGATORIO_scaled.setIcon(new ImageIcon(Scaling.get(purgatorio_SELECTED, 313, 61, Options.full_screen)));
			INFERNO_scaled.setIcon(new ImageIcon(Scaling.get(inferno, 235, 61, Options.full_screen)));
			}
		else if(difficulty==Difficulty.inferno) {
			PARADISO_scaled.setIcon(new ImageIcon(Scaling.get(paradiso, 285, 55, Options.full_screen)));
			PURGATORIO_scaled.setIcon(new ImageIcon(Scaling.get(purgatorio, 313, 61, Options.full_screen)));
			INFERNO_scaled.setIcon(new ImageIcon(Scaling.get(inferno_SELECTED, 235, 61, Options.full_screen)));
			}
		if(!full_screen) {
			WINDOWED_scaled.setIcon(new ImageIcon(Scaling.get(windowed_SELECTED, 265, 41, Options.full_screen)));
			FULLSCREEN_scaled.setIcon(new ImageIcon(Scaling.get(fullscreen, 305, 41, Options.full_screen)));
		}
		else if(full_screen) {
			WINDOWED_scaled.setIcon(new ImageIcon(Scaling.get(windowed, 265, 41, Options.full_screen)));
			FULLSCREEN_scaled.setIcon(new ImageIcon(Scaling.get(fullscreen_SELECTED, 305, 41, Options.full_screen)));
		}
		if(music)	MUSIC_check.setIcon(new ImageIcon(Scaling.get(music_checked, 50, 50, Options.full_screen)));
		else 		MUSIC_check.setIcon(new ImageIcon(Scaling.get(music_unchecked, 50, 50, Options.full_screen)));
	}

	private void turn_back(JFrame frame, Menu menu) throws InterruptedException {		
		menu.check_resize();
		frame.remove(this);
		frame.setContentPane(menu);
		frame.revalidate();
		frame.repaint();
	}
	
	private void full_screen(JFrame frame) {
		this.check_resize();
		frame.dispose();
		if (!frame.isUndecorated())
			frame.setUndecorated(true);
		frame.setVisible(true);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}
	
	private void windowed(JFrame frame) {
		this.check_resize();
		frame.dispose();
		if (frame.isUndecorated())
			frame.setUndecorated(false);
		frame.setVisible(true);
		frame.setSize(1280, 749);
	}
	
	private void image_init() {
		try {
			background = ImageIO.read(new File(OptionsPagePath + "background.png"));
			arrow_back = ImageIO.read(new File(OptionsPagePath + "arrow_back.png")).getScaledInstance(120, 80, Image.SCALE_SMOOTH);
			paradiso = ImageIO.read(new File(OptionsPagePath + "Paradiso.png")).getScaledInstance(285, 55, Image.SCALE_SMOOTH);
			purgatorio = ImageIO.read(new File(OptionsPagePath + "Purgatorio.png")).getScaledInstance(313, 61, Image.SCALE_SMOOTH);
			inferno = ImageIO.read(new File(OptionsPagePath + "Inferno.png")).getScaledInstance(235, 61, Image.SCALE_SMOOTH);
			windowed = ImageIO.read(new File(OptionsPagePath + "Windowed.png")).getScaledInstance(265, 41, Image.SCALE_SMOOTH);
			fullscreen = ImageIO.read(new File(OptionsPagePath + "FullScreen.png")).getScaledInstance(305, 41, Image.SCALE_SMOOTH);
			arrow_back_SELECTED = ImageIO.read(new File(OptionsPagePath + "arrow_back_SELECTED.png")).getScaledInstance(120, 80, Image.SCALE_SMOOTH);
			paradiso_SELECTED = ImageIO.read(new File(OptionsPagePath + "Paradiso_SELECTED.png")).getScaledInstance(297, 61, Image.SCALE_SMOOTH);
			purgatorio_SELECTED = ImageIO.read(new File(OptionsPagePath + "Purgatorio_SELECTED.png")).getScaledInstance(313, 61, Image.SCALE_SMOOTH);
			inferno_SELECTED = ImageIO.read(new File(OptionsPagePath + "Inferno_SELECTED.png")).getScaledInstance(235, 61, Image.SCALE_SMOOTH);
			windowed_SELECTED = ImageIO.read(new File(OptionsPagePath + "Windowed_SELECTED.png")).getScaledInstance(265, 41, Image.SCALE_SMOOTH);
			fullscreen_SELECTED = ImageIO.read(new File(OptionsPagePath + "FullScreen_SELECTED.png")).getScaledInstance(305, 41, Image.SCALE_SMOOTH);
			music_unchecked = ImageIO.read(new File(OptionsPagePath + "unchecked.png")).getScaledInstance(50, 50, Image.SCALE_SMOOTH);
			music_checked = ImageIO.read(new File(OptionsPagePath + "checked.png")).getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private void label_init () {
		ARROW_BACK_scaled = new JLabel(new ImageIcon(arrow_back));
		PARADISO_scaled = new JLabel(new ImageIcon(paradiso));
		PURGATORIO_scaled = new JLabel(new ImageIcon(purgatorio_SELECTED));
		INFERNO_scaled = new JLabel(new ImageIcon(inferno));
		WINDOWED_scaled = new JLabel(new ImageIcon(windowed_SELECTED));
		FULLSCREEN_scaled = new JLabel(new ImageIcon(fullscreen));
		
		if(music)	MUSIC_check = new JLabel(new ImageIcon(music_checked));
		else 		MUSIC_check = new JLabel(new ImageIcon(music_unchecked));
	}
	
	private void MouseListener_init(JFrame frame, Menu menu) {
		ARROW_BACK_scaled.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				
				synchronized(this) {
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
				
				synchronized(this) {
					Music.playTone("hover");
				}
				
				ARROW_BACK_scaled.setIcon(new ImageIcon(Scaling.get(arrow_back_SELECTED, 120, 80, Options.full_screen)));
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
				
				synchronized(this) {
					Music.playTone("select");
				}
				
				if (difficulty != Difficulty.paradiso) {
					difficulty = Difficulty.paradiso;
					PARADISO_scaled.setIcon(new ImageIcon(Scaling.get(paradiso_SELECTED, 285, 55, Options.full_screen)));
					PURGATORIO_scaled.setIcon(new ImageIcon(Scaling.get(purgatorio, 313, 61, Options.full_screen)));
					INFERNO_scaled.setIcon(new ImageIcon(Scaling.get(inferno, 235, 61, Options.full_screen)));
					revalidate();
					repaint();
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (difficulty != Difficulty.paradiso) {
					PARADISO_scaled.setIcon(new ImageIcon(Scaling.get(paradiso, 285, 55, Options.full_screen)));
					revalidate();
					repaint();
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				
				synchronized(this) {
					Music.playTone("hover");
				}
				
				if (difficulty != Difficulty.paradiso) {
					PARADISO_scaled.setIcon(new ImageIcon(Scaling.get(paradiso_SELECTED, 285, 55, Options.full_screen)));
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
				
				synchronized(this) {
					Music.playTone("select");
				}
				
				if (difficulty != Difficulty.purgatorio) {
					difficulty = Difficulty.purgatorio;
					PARADISO_scaled.setIcon(new ImageIcon(Scaling.get(paradiso, 285, 55, Options.full_screen)));
					PURGATORIO_scaled.setIcon(new ImageIcon(Scaling.get(purgatorio_SELECTED, 313, 61, Options.full_screen)));
					INFERNO_scaled.setIcon(new ImageIcon(Scaling.get(inferno, 235, 61, Options.full_screen)));
					revalidate();
					repaint();
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (difficulty != Difficulty.purgatorio) {
					PURGATORIO_scaled.setIcon(new ImageIcon(Scaling.get(purgatorio, 313, 61, Options.full_screen)));
					revalidate();
					repaint();
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				
				synchronized(this) {
					Music.playTone("hover");
				}
				
				if (difficulty != Difficulty.purgatorio) {
					PURGATORIO_scaled.setIcon(new ImageIcon(Scaling.get(purgatorio_SELECTED, 313, 61, Options.full_screen)));
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
				
				synchronized(this) {
					Music.playTone("select");
				}
				
				if (difficulty != Difficulty.inferno) {
					difficulty = Difficulty.inferno;
					PARADISO_scaled.setIcon(new ImageIcon(Scaling.get(paradiso, 285, 55, Options.full_screen)));
					PURGATORIO_scaled.setIcon(new ImageIcon(Scaling.get(purgatorio, 313, 61, Options.full_screen)));
					INFERNO_scaled.setIcon(new ImageIcon(Scaling.get(inferno_SELECTED, 235, 61, Options.full_screen)));
					revalidate();
					repaint();
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (difficulty != Difficulty.inferno) {
					INFERNO_scaled.setIcon(new ImageIcon(Scaling.get(inferno, 235, 61, Options.full_screen)));
					revalidate();
					repaint();
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				
				synchronized(this) {
					Music.playTone("hover");
				}
				
				if (difficulty != Difficulty.inferno) {
					INFERNO_scaled.setIcon(new ImageIcon(Scaling.get(inferno_SELECTED, 235, 61, Options.full_screen)));
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
				
				synchronized(this) {
					Music.playTone("select");
				}
				
				full_screen = false;
				windowed(frame);
				FULLSCREEN_scaled.setIcon(new ImageIcon(Scaling.get(fullscreen, 305, 41, Options.full_screen)));
				WINDOWED_scaled.setIcon(new ImageIcon(Scaling.get(windowed_SELECTED, 265, 41, Options.full_screen)));
				revalidate();
				repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (full_screen) {
					WINDOWED_scaled.setIcon(new ImageIcon(Scaling.get(windowed, 265, 41, Options.full_screen)));
					revalidate();
					repaint();
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				
				synchronized(this) {
					Music.playTone("hover");
				}
				
				if (full_screen) {
					WINDOWED_scaled.setIcon(new ImageIcon(Scaling.get(windowed_SELECTED, 265, 41, Options.full_screen)));
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
				
				synchronized(this) {
					Music.playTone("select");
				}
				
				full_screen=true;
				full_screen(frame);
				FULLSCREEN_scaled.setIcon(new ImageIcon(Scaling.get(fullscreen_SELECTED, 305, 41, Options.full_screen)));
				WINDOWED_scaled.setIcon(new ImageIcon(Scaling.get(windowed, 265, 41, Options.full_screen)));
				revalidate();
				repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (!full_screen) {
					FULLSCREEN_scaled.setIcon(new ImageIcon(Scaling.get(fullscreen, 305, 41, Options.full_screen)));
					revalidate();
					repaint();
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				
				synchronized(this) {
					Music.playTone("hover");
				}
				
				if (!full_screen) {
					FULLSCREEN_scaled.setIcon(new ImageIcon(Scaling.get(fullscreen_SELECTED, 305, 41, Options.full_screen)));
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
			public void mousePressed(MouseEvent e) {
				
				synchronized(this) {
					Music.playTone("select");
				}
				
				if (music) {
					music = false;
					Music.stop();
					MUSIC_check.setIcon(new ImageIcon(Scaling.get(music_unchecked, 50, 50, Options.full_screen)));
				} else if (!music) {
					music = true;
					Music.start();
					MUSIC_check.setIcon(new ImageIcon(Scaling.get(music_checked, 50, 50, Options.full_screen)));
				}
				revalidate();
				repaint();
			}

			@Override public void mouseExited(MouseEvent e) 	{}
			@Override public void mouseEntered(MouseEvent e) 	{}
			@Override public void mouseClicked(MouseEvent e) 	{}
			@Override public void mouseReleased(MouseEvent e) 	{}
		});
	}
	
	private void panel_init () {
		this.setLayout(null);
		this.add(ARROW_BACK_scaled);
		this.add(PARADISO_scaled);
		this.add(PURGATORIO_scaled);
		this.add(INFERNO_scaled);
		this.add(WINDOWED_scaled);
		this.add(FULLSCREEN_scaled);
		this.add(MUSIC_check);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), null);
	}
}
