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
import menu.Options;
import menu.Scaling;

import model.Player;

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
	
	
	BufferedImage Background;
	Image arrow_back;
	Image arrow_back_SELECTED;
	Image lives_3;
	Image lives_2;
	Image lives_1;
	
	public static int total_score = 0;
	public static int remaining_time = 150; //150 secondi per livello
	public boolean turn_back = false;
	
	JLabel ARROW_BACK_scaled;
	JLabel time_left;
	JLabel Lives;
	JLabel Diamonds;
	JLabel score;
	
	public void check_resize(Level level) {
		ARROW_BACK_scaled.setIcon(new ImageIcon(Scaling.get(arrow_back, 80, 50, Options.full_screen)));
		if(((Player)level.getWorld().getPlayer()).getLifes() == 3)
			Lives.setIcon(new ImageIcon(Scaling.get(lives_3, 240, 73, Options.full_screen)));
		else if(((Player)level.getWorld().getPlayer()).getLifes() == 2)
			Lives.setIcon(new ImageIcon(Scaling.get(lives_2, 240, 73, Options.full_screen)));
		else
			Lives.setIcon(new ImageIcon(Scaling.get(lives_1, 240, 73, Options.full_screen)));
		
		Scaling.set(ARROW_BACK_scaled, 230, 640, 146, 97, Options.full_screen);
		Scaling.set(Lives, 32, 440, 305, 94, Options.full_screen);
		Scaling.set(time_left, 16, 580, 350, 100, Options.full_screen);
		Scaling.set(Diamonds, 14, 120, 350, 100, Options.full_screen);
		Scaling.set(score, 12, 275, 350, 100, Options.full_screen);	
	}
	
	private void turn_back(JFrame frame, Menu menu, Game game) throws InterruptedException {
		
//		if (Options.multiplayer) {
//			if (socketClient != null && socketClient.isConnected()) {
//				socketClient.close();
//				Options.host = false;
//			}
//			if(socketServer != null && socketServer.isConnected())
//				socketServer.close();
//			Options.multiplayer = false;
//		}
		
		try{

			menu.check_resize();
			game.isReset = false;
			game.closeThread();
			frame.remove(game);
			frame.setContentPane(menu);
			frame.revalidate();
			frame.repaint();
			Music.setSong(Music.menuSong);
			
			//game.stopThread();
			//menu.wakeThread();
			
		} catch(NullPointerException e) {
			e.printStackTrace();
		}
		
	}
	
	public Score(JFrame frame, Menu menu, Game game, Level level) { //Default Score resolution: 360x720
		
		try {
			Font eightBit = Font.createFont(Font.TRUETYPE_FONT, new File("." + File.separator + "resources" + File.separator + "assets" + File.separator + "8BITFONT.TTF")).deriveFont(80f);
			
			Background = ImageIO.read(new File(ScoreTabPath + "score_background.png"));
			arrow_back = ImageIO.read(new File(ScoreTabPath + "arrow_back.png")).getScaledInstance(80, 50, Image.SCALE_SMOOTH);
			arrow_back_SELECTED = ImageIO.read(new File(ScoreTabPath + "arrow_back_SELECTED.png")).getScaledInstance(80, 50, Image.SCALE_SMOOTH);
			lives_3 = ImageIO.read(new File(ScoreTabPath + "3_Hearts.png")).getScaledInstance(240, 73, Image.SCALE_SMOOTH);
			lives_2 = ImageIO.read(new File(ScoreTabPath + "2_Hearts.png")).getScaledInstance(240, 73, Image.SCALE_SMOOTH);
			lives_1 = ImageIO.read(new File(ScoreTabPath + "1_Hearts.png")).getScaledInstance(240, 73, Image.SCALE_SMOOTH);
			
			Lives = new JLabel(new ImageIcon(lives_3));
			
			ARROW_BACK_scaled = new JLabel(new ImageIcon(arrow_back));
			
			ARROW_BACK_scaled.addMouseListener(new MouseListener() {
					
				@Override
				public void mousePressed(MouseEvent e) {
					Music.playTone("select");
					
					try {
						turn_back(frame, menu, game);
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
			
			time_left = new JLabel("" + remaining_time, JLabel.CENTER);
			time_left.setForeground(Color.WHITE);
			time_left.setFont(eightBit);
			
			int missing_diamonds = level.getWorld().getMap().getNumDiamonds();
			Diamonds = new JLabel("" + missing_diamonds, JLabel.CENTER);
			Diamonds.setForeground(Color.WHITE);
			Diamonds.setFont(eightBit);
			
			score = new JLabel("" + total_score, JLabel.CENTER);
			score.setForeground(Color.WHITE);
			score.setFont(eightBit);
					
			this.add(Lives);
			this.setLayout(null); //se non settiamo a null non possiamo usare il setBounds.
			this.add(ARROW_BACK_scaled);
			this.add(time_left);
			this.add(Diamonds);
			this.add(score);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FontFormatException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.drawImage(Background, 0, 0, this.getWidth(), this.getHeight(), null);
	}
}
