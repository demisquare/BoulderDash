//AUTORE: Davide Gena

package view;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import audio.Music;
import menu.Menu;
import menu.Multiplayer;
import menu.Options;
import menu.Scaling;

import model.Player;
import network.SocketClient;
import network.SocketServer;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;

public class Score extends JPanel implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -48116048080203555L;
	private static final String ScoreTabPath =
			"." + File.separator + 
			"resources" + File.separator + 
			"assets" + File.separator + 
			"ScoreTab" + File.separator;
	
	private Multiplayer multi;

	private JFrame frame;
	private Menu menu;
	private Game game;
	
	BufferedImage Background;
	Image arrow_back;
	Image arrow_back_SELECTED;
	Image lives_3;
	Image lives_2;
	Image lives_1;
	
	Level level;
	
	public int missing_diamonds;
	public static int total_score = 0;
	public int remaining_time; //150 secondi per livello
	public boolean turn_back = false;
	
	JLabel ARROW_BACK_scaled;
	JLabel time_left;
	JLabel Lives;
	JLabel Diamonds;
	JLabel score;
	
	public Score(JFrame frame, Menu menu, Game game, Level level) { //Default Score resolution: 360x720		
		
		super();
		
		this.frame = frame;
		this.menu = menu;
		this.game = game;
		this.level = level;
		this.multi = null;
		
		remaining_time = 150;
		
		missing_diamonds = level.getWorld().getMap().getNumDiamonds();
		
		Font eightBit=null;
		try {
			eightBit = Font.createFont(Font.TRUETYPE_FONT, new File("." + File.separator + "resources" + File.separator + "assets" + File.separator + "8BITFONT.TTF")).deriveFont(80f);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		image_init();
		label_init(eightBit);
		MouseListener_init();
		panel_init();
	}
	
	public void check_resize() {
		ARROW_BACK_scaled.setIcon(new ImageIcon(Scaling.get(arrow_back, 80, 50, Options.full_screen)));
		
		Scaling.set(ARROW_BACK_scaled, 230, 640, 146, 97, Options.full_screen);
		Scaling.set(Lives, 32, 440, 305, 94, Options.full_screen);
		Scaling.set(time_left, 16, 580, 350, 100, Options.full_screen);
		Scaling.set(Diamonds, 14, 120, 350, 100, Options.full_screen);
		Scaling.set(score, 12, 275, 350, 100, Options.full_screen);	
	}
	
	private void turn_back() throws InterruptedException {		
		if (Options.multiplayer) {
			if (multi.getSocketClient() != null && multi.getSocketClient().isConnected()) {
				multi.getSocketClient().close();
				Options.host = false;
			}
			if(multi.getSocketServer() != null && multi.getSocketServer().isConnected())
				multi.getSocketServer().close();
			Options.multiplayer = false;
		}
		
		try{
			
			Options.difficulty = game.getStartingDifficulty();
			total_score = 0;
			game.setStage(0);
			menu.check_resize();
			game.isReset = false;
//			menu.ai.stop();
			game.closeThread();
			frame.remove(game);
			frame.setContentPane(menu);
			frame.revalidate();
			frame.repaint();
			Music.setSong(Music.menuSong);
			
		} catch(NullPointerException e) {
			e.printStackTrace();
		}
		
	}
	
	public void setMulti(Multiplayer multi) {
		this.multi = multi;
	}

	private void image_init() {
		try {
			Background = ImageIO.read(new File(ScoreTabPath + "score_background.png"));
			arrow_back = ImageIO.read(new File(ScoreTabPath + "arrow_back.png")).getScaledInstance(80, 50, Image.SCALE_SMOOTH);
			arrow_back_SELECTED = ImageIO.read(new File(ScoreTabPath + "arrow_back_SELECTED.png")).getScaledInstance(80, 50, Image.SCALE_SMOOTH);
			lives_3 = ImageIO.read(new File(ScoreTabPath + "3_Hearts.png")).getScaledInstance(240, 73, Image.SCALE_SMOOTH);
			lives_2 = ImageIO.read(new File(ScoreTabPath + "2_Hearts.png")).getScaledInstance(240, 73, Image.SCALE_SMOOTH);
			lives_1 = ImageIO.read(new File(ScoreTabPath + "1_Hearts.png")).getScaledInstance(240, 73, Image.SCALE_SMOOTH);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void label_init(Font eightBit) {
		Lives = new JLabel(new ImageIcon(lives_3));	
		ARROW_BACK_scaled = new JLabel(new ImageIcon(arrow_back));
		
		time_left = new JLabel("" + remaining_time, JLabel.CENTER);
		time_left.setForeground(Color.WHITE);
		time_left.setFont(eightBit);
		
		Diamonds = new JLabel("" + missing_diamonds, JLabel.CENTER);
		Diamonds.setForeground(Color.WHITE);
		Diamonds.setFont(eightBit);
		
		score = new JLabel("" + total_score, JLabel.CENTER);
		score.setForeground(Color.WHITE);
		score.setFont(eightBit);
	}
	
	private void MouseListener_init() {
		ARROW_BACK_scaled.addMouseListener(new MouseListener() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				Music.playTone("select");
				
				try {
					turn_back();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				
				ARROW_BACK_scaled.setIcon(new ImageIcon(Scaling.get(arrow_back, 80, 50, Options.full_screen)));
				revalidate();
				repaint();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				ARROW_BACK_scaled.setIcon(new ImageIcon(Scaling.get(arrow_back, 80, 50, Options.full_screen)));
				revalidate();
				repaint();
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				Music.playTone("hover");
				ARROW_BACK_scaled.setIcon(new ImageIcon(Scaling.get(arrow_back_SELECTED, 80, 50, Options.full_screen)));
				revalidate();
				repaint();
			}
			
			@Override public void mouseClicked(MouseEvent e) {}
			@Override public void mouseReleased(MouseEvent e) {}
		});
	}
	
	private void panel_init() {
		this.add(Lives);
		this.setLayout(null);
		this.add(ARROW_BACK_scaled);
		this.add(time_left);
		this.add(Diamonds);
		this.add(score);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.drawImage(Background, 0, 0, this.getWidth(), this.getHeight(), null);
	}
	
	int getMissing_diamonds() {return missing_diamonds;}
	void setMissing_diamonds(int md) {missing_diamonds=md; Diamonds.setText("" + missing_diamonds);}
	
	int getTotal_score() {return total_score;}
	void setTotal_score(int ts) {total_score=ts; score.setText("" + total_score);}
	
	int getRemaining_time() {return remaining_time;}
	void setRemaining_time(int rt) {remaining_time=rt; time_left.setText("" + remaining_time);}

	void updateHearts() {
		if(((Player)level.getWorld().getPlayer()).getLifes() == 3)
			Lives.setIcon(new ImageIcon(Scaling.get(lives_3, 240, 73, Options.full_screen)));
		else if(((Player)level.getWorld().getPlayer()).getLifes() == 2)
			Lives.setIcon(new ImageIcon(Scaling.get(lives_2, 240, 73, Options.full_screen)));
		else
			Lives.setIcon(new ImageIcon(Scaling.get(lives_1, 240, 73, Options.full_screen)));
	}
}