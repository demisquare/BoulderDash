package view;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
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
	
	public static int remaining_time = 150; //150 secondi per livello
	public boolean turn_back = false;
	
	JLabel ARROW_BACK_scaled;
	JLabel time_left;
	JLabel Lives;
	
	private void turn_back(JFrame frame, Menu menu, Game game) {
		//socketServer.close();
		//socketClient.close();
		try{
			game.isReset = false;
			frame.remove(game);
			frame.setContentPane(menu);
			frame.revalidate();
			frame.repaint();
			Music.setSong(Music.menuSong);
			
		} catch(NullPointerException e) {
			e.printStackTrace();
		}
		
	}
	
	public Score(JFrame frame, Menu menu, Game game) { //Default Score resolution: 360x720
		
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
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mousePressed(MouseEvent e) {
					Music.playTone("select");
					turn_back(frame, menu, game);
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
			

			time_left = new JLabel("" + remaining_time, JLabel.CENTER);
			time_left.setForeground(Color.WHITE);
			time_left.setFont(eightBit);
			
			Toolkit tk = Toolkit.getDefaultToolkit();
			double xSize = tk.getScreenSize().getWidth();
			double ySize = tk.getScreenSize().getHeight();
			
			new Thread(new Runnable() {
				@Override
				public void run() {
					while (true) {
						if(!Options.full_screen) {
							ARROW_BACK_scaled.setBounds(230, 640, 146, 97);
							Lives.setBounds(32, 440, 305, 94);
							time_left.setBounds(16, 580, 350, 100);
						}
						else if(Options.full_screen) {
							//ARROW_BACK_scaled.setIcon(new ImageIcon(arrow_back.getScaledInstance((int)(80*(xSize/1280)), (int)(50*(ySize/720)), Image.SCALE_SMOOTH)));
							ARROW_BACK_scaled.setBounds((int)(230*(xSize/1280)), (int)(640*(ySize/720)), 146, 97);
							Lives.setBounds((int)(32*(xSize/1280)), (int)(440*(ySize/720)), 305, 94);
							time_left.setBounds((int)(16*(xSize/1280)), (int)(580*(ySize/720)), 350, 100);
						}
						try {
							Thread.sleep(34);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}).start();
			
			this.add(Lives);
			this.setLayout(null); //se non settiamo a null non possiamo usare il setBounds.
			this.add(ARROW_BACK_scaled);
			this.add(time_left);
			

			
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
