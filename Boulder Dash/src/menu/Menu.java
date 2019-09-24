//AUTORE: Davide Gena

package menu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import audio.Music;
import view.Game;

public class Menu extends JPanel implements Serializable {

	private static final long serialVersionUID = -5844904387077358595L;
	
	private static final String MainMenuPage =
			"." + File.separator + "resources" 
			+ File.separator + "assets" 
			+ File.separator + "Menu"
			+ File.separator + "MainMenuPage" 
			+ File.separator;
	
//	private Thread t;
	
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
	
	Game game;
	Multiplayer multi;
	public Options options;
	Credits credits;
	You_Lose youlose;
	
	public Menu(JFrame frame) {
		super();
		
		panel_init(frame);
		image_init();
		label_init();
		MouseListener_init(frame);
		menu_choices_init();
		
		this.setLayout(null);
		this.add(menu_choices);
		
		synchronized (this) {
			Music.setSong(Music.menuSong);
		}
	}
	
	public void check_resize() {
		
		START_scaled.setIcon(new ImageIcon(Scaling.get(START, 191, 55, Options.full_screen)));
		MULTI_scaled.setIcon(new ImageIcon(Scaling.get(MULTI, 423, 55, Options.full_screen)));
		OPTIONS_scaled.setIcon(new ImageIcon(Scaling.get(OPTIONS, 272, 54, Options.full_screen)));
		CREDITS_scaled.setIcon(new ImageIcon(Scaling.get(CREDITS, 267, 55, Options.full_screen)));
		EXIT_scaled.setIcon(new ImageIcon(Scaling.get(EXIT, 141, 50, Options.full_screen)));
	
		Scaling.set(menu_choices, (int)(1280d/2d - 430d/2d), 250, 550, 550, Options.full_screen);	
	}

	private void start_selected(JFrame frame) throws InterruptedException {
		
		frame.remove(this);
		
		game.launchGame();
		
		frame.setContentPane(game);
		
		if(!frame.isAncestorOf(game)) { 
			throw new InterruptedException();
		}
		
		if(!game.level.requestFocusInWindow()) { 
			throw new InterruptedException(); 
		}
		
		frame.revalidate();
		frame.repaint();
		
		synchronized(this) {
			Music.setSong(Music.gameSong);
		}
	}
	
	private void options_selected(JFrame frame, Options options) throws InterruptedException {
		
//		closeThread();
		frame.remove(this);
		options.check_resize();
		frame.setContentPane(options);
		frame.revalidate();
		frame.repaint();
	}
	
	private void multi_selected(JFrame frame, Multiplayer multi, You_Lose youlose) throws InterruptedException {
	
//		closeThread();
		//frame.remove(this);
		//multi.check_resize();
		//frame.setContentPane(multi);
		//frame.revalidate();
		//frame.repaint();
		
		Options.multiplayer = true;
		
		frame.remove(this);
		
		game.launchGame();
		
		frame.setContentPane(game);
		
		if(!frame.isAncestorOf(game)) { 
			throw new InterruptedException();
		}
		
		if(!game.level.requestFocusInWindow()) { 
			throw new InterruptedException(); 
		}
		
		frame.revalidate();
		frame.repaint();
		
		synchronized(this) {
			Music.setSong(Music.gameSong);
		}
	}
	
	private void credits_selected(JFrame frame, Credits credits) throws InterruptedException {
	
//		closeThread();
		frame.remove(this);
		frame.setContentPane(credits);
		frame.revalidate();
		frame.repaint();
		
		synchronized(this) {
			Music.setSong(Music.creditsSong);
		}
	}

	private void image_init () {
		try {
			background = ImageIO.read(new File(MainMenuPage + "MainMenuPage_Background.png"));
			START = ImageIO.read(new File(MainMenuPage + "MainMenuPage_START.png")).getScaledInstance(191, 55, Image.SCALE_SMOOTH);
			MULTI = ImageIO.read(new File(MainMenuPage + "MainMenuPage_AIMODE.png")).getScaledInstance(423, 55, Image.SCALE_SMOOTH);
			OPTIONS = ImageIO.read(new File(MainMenuPage + "MainMenuPage_OPTIONS.png")).getScaledInstance(272, 54, Image.SCALE_SMOOTH);
			CREDITS = ImageIO.read(new File(MainMenuPage + "MainMenuPage_CREDITS.png")).getScaledInstance(267, 55, Image.SCALE_SMOOTH);
			EXIT = ImageIO.read(new File(MainMenuPage + "MainMenuPage_EXIT.png")).getScaledInstance(141, 50, Image.SCALE_SMOOTH);
			START_SELECTED = ImageIO.read(new File(MainMenuPage + "MainMenuPage_START_SELECTED.png")).getScaledInstance(191, 55, Image.SCALE_SMOOTH);
			MULTI_SELECTED = ImageIO.read(new File(MainMenuPage + "MainMenuPage_AIMODE_SELECTED.png")).getScaledInstance(423, 55, Image.SCALE_SMOOTH);
			OPTIONS_SELECTED = ImageIO.read(new File(MainMenuPage + "MainMenuPage_OPTIONS_SELECTED.png")).getScaledInstance(272, 54, Image.SCALE_SMOOTH);
			CREDITS_SELECTED = ImageIO.read(new File(MainMenuPage + "MainMenuPage_CREDITS_SELECTED.png")).getScaledInstance(267, 55, Image.SCALE_SMOOTH);
			EXIT_SELECTED = ImageIO.read(new File(MainMenuPage + "MainMenuPage_EXIT_SELECTED.png")).getScaledInstance(141, 50, Image.SCALE_SMOOTH);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void label_init() {
		START_scaled = new JLabel(new ImageIcon(START));
		MULTI_scaled = new JLabel(new ImageIcon(MULTI));
		OPTIONS_scaled = new JLabel(new ImageIcon(OPTIONS));
		CREDITS_scaled = new JLabel(new ImageIcon(CREDITS));
		EXIT_scaled = new JLabel(new ImageIcon(EXIT));
	}
	
	private void MouseListener_init (JFrame frame) {
		START_scaled.addMouseListener(new MouseListener() {

			@Override
			public void mouseEntered(MouseEvent e) {
				synchronized(this) {
					Music.playTone("hover");
				}
				START_scaled.setIcon(new ImageIcon(Scaling.get(START_SELECTED, 191, 55, Options.full_screen)));
				revalidate();
				repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				START_scaled.setIcon(new ImageIcon(Scaling.get(START, 191, 55, Options.full_screen)));
				revalidate();
				repaint();
			}

			@Override
			public void mousePressed(MouseEvent e) {
				
				synchronized(this) {
					Music.playTone("select");
				}
				
				try {
					start_selected(frame);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				START_scaled.setIcon(new ImageIcon(Scaling.get(START, 191, 55, Options.full_screen)));
				revalidate();
				repaint();
			}

			@Override public void mouseReleased(MouseEvent e) 	{}
			@Override public void mouseClicked(MouseEvent e) 	{}
		});
		
		MULTI_scaled.addMouseListener(new MouseListener() {

			@Override
			public void mousePressed(MouseEvent e) {
				
				synchronized(this) {
					Music.playTone("select");
				}
				
				try {
					multi_selected(frame, multi, youlose);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				
				MULTI_scaled.setIcon(new ImageIcon(Scaling.get(MULTI, 423, 55, Options.full_screen)));
				revalidate();
				repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				MULTI_scaled.setIcon(new ImageIcon(Scaling.get(MULTI, 423, 55, Options.full_screen)));
				revalidate();
				repaint();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				
				synchronized(this) {
					Music.playTone("hover");
				}
				
				MULTI_scaled.setIcon(new ImageIcon(Scaling.get(MULTI_SELECTED, 423, 55, Options.full_screen)));
				revalidate();
				repaint();
			}

			@Override public void mouseClicked(MouseEvent e) {}
			@Override public void mouseReleased(MouseEvent e) {}
		});
		
		OPTIONS_scaled.addMouseListener(new MouseListener() {

			@Override
			public void mousePressed(MouseEvent e) {
				
				synchronized(this) {
					Music.playTone("select");
				}
				
				try {
					options_selected(frame, options);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				
				OPTIONS_scaled.setIcon(new ImageIcon(Scaling.get(OPTIONS, 272, 54, Options.full_screen)));
				revalidate();
				repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				OPTIONS_scaled.setIcon(new ImageIcon(Scaling.get(OPTIONS, 272, 54, Options.full_screen)));
				revalidate();
				repaint();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				
				synchronized(this) {
					Music.playTone("hover");
				}
				
				OPTIONS_scaled.setIcon(new ImageIcon(Scaling.get(OPTIONS_SELECTED, 272, 54, Options.full_screen)));
				revalidate();
				repaint();
			}

			@Override public void mouseClicked(MouseEvent e) 	{}
			@Override public void mouseReleased(MouseEvent e) 	{}
		});
		
		CREDITS_scaled.addMouseListener(new MouseListener() {

			@Override
			public void mousePressed(MouseEvent e) {
				
				synchronized(this) {
					Music.playTone("select");
				}
				
				try {
					credits_selected(frame, credits);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				
				CREDITS_scaled.setIcon(new ImageIcon(Scaling.get(CREDITS, 267, 55, Options.full_screen)));
				revalidate();
				repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				CREDITS_scaled.setIcon(new ImageIcon(Scaling.get(CREDITS, 267, 55, Options.full_screen)));
				revalidate();
				repaint();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				
				synchronized(this) {
					Music.playTone("hover");
				}
				
				CREDITS_scaled.setIcon(new ImageIcon(Scaling.get(CREDITS_SELECTED, 267, 55, Options.full_screen)));
				revalidate();
				repaint();
			}

			@Override public void mouseClicked(MouseEvent e) 	{}
			@Override public void mouseReleased(MouseEvent e) 	{}
		});
		
		EXIT_scaled.addMouseListener(new MouseListener() {

			@Override
			public void mouseEntered(MouseEvent e) {
				
				synchronized(this) {
					Music.playTone("hover");
				}
				
				EXIT_scaled.setIcon(new ImageIcon(Scaling.get(EXIT_SELECTED, 141, 50, Options.full_screen)));
				revalidate();
				repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				EXIT_scaled.setIcon(new ImageIcon(Scaling.get(EXIT, 141, 50, Options.full_screen)));
				revalidate();
				repaint();
			}

			@Override
			public void mousePressed(MouseEvent e) {
				
				synchronized(this) {
					Music.playTone("select");
				}
				
				System.exit(0);
			}

			@Override public void mouseReleased(MouseEvent e) 	{}
			@Override public void mouseClicked(MouseEvent e) 	{}
		});
	}
	
	private void menu_choices_init () {
		menu_choices = new JPanel();
		menu_choices.setLayout(new BoxLayout(menu_choices, BoxLayout.Y_AXIS));

		menu_choices.add(START_scaled);
		menu_choices.add(MULTI_scaled);
		menu_choices.add(OPTIONS_scaled);
		menu_choices.add(CREDITS_scaled);
		menu_choices.add(EXIT_scaled);
		
		menu_choices.setBackground(new Color(0, 0, 0, 0));
		menu_choices.setBounds((1280 / 2 - 430 / 2), 250, 550, 550);
	}
	
	private void panel_init(JFrame frame) {
		options  = new Options(frame, this);
		credits  = new Credits(frame, this);
		game 	 = new Game(frame, this);
		multi	 = new Multiplayer(frame, game, this);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), null);

	}
}
