package view;

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

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;

public class Score extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	BufferedImage Background;
	Image arrow_back;
	Image arrow_back_SELECTED;
	
	public static int remaining_time = 150; //150 secondi per livello
	public boolean turn_back=false;
	
	JLabel ARROW_BACK_scaled;
	JLabel time_left;
	
	
	public Score() {
		try {
			Font eightBit = Font.createFont(Font.TRUETYPE_FONT, new File("." + File.separator + "resources" + File.separator + "assets" + File.separator + "8BITFONT.TTF")).deriveFont(80f);
			
			Background = ImageIO.read(
					new File("." + File.separator + "resources" + File.separator + "assets" + File.separator + "ScoreTab" + File.separator + "score_background.png"));
		
			arrow_back = ImageIO.read(
					new File("." + File.separator + "resources" + File.separator + "assets" + File.separator + "ScoreTab" + File.separator + "arrow_back.png")).getScaledInstance(80, 50, Image.SCALE_SMOOTH);
			
			arrow_back_SELECTED = ImageIO.read(
					new File("." + File.separator + "resources" + File.separator + "assets" + File.separator + "ScoreTab" + File.separator + "arrow_back_SELECTED.png")).getScaledInstance(80, 50, Image.SCALE_SMOOTH);
			
			
			ARROW_BACK_scaled = new JLabel(new ImageIcon (arrow_back));
			
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
			
			ARROW_BACK_scaled.setBounds(230, 640, 146, 97);
			
			time_left=new JLabel("" + remaining_time, JLabel.CENTER);
			time_left.setForeground(Color.WHITE);
			time_left.setFont(eightBit);
			time_left.setBounds(6, 580, 350, 100);
			
			
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
		
		g.drawImage(Background, 0, 0, 360, 720, null);
	}

}
