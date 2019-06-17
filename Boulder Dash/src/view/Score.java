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
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;

public class Score extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	BufferedImage Background;
	Image arrow_back;
	Image arrow_back_SELECTED;
	Image lives_3;
	Image lives_2;
	Image lives_1;
	
	public static int remaining_time = 150; //150 secondi per livello
	public boolean turn_back=false;
	
	JLabel ARROW_BACK_scaled;
	JLabel time_left;
	JLabel Lives;
	
	
	public Score() {
		try {
			Font eightBit = Font.createFont(Font.TRUETYPE_FONT, new File("." + File.separator + "resources" + File.separator + "assets" + File.separator + "8BITFONT.TTF")).deriveFont(80f);
			
			Background = ImageIO.read(
					new File("." + File.separator + "resources" + File.separator + "assets" + File.separator + "ScoreTab" + File.separator + "score_background.png"));
		
			arrow_back = ImageIO.read(
					new File("." + File.separator + "resources" + File.separator + "assets" + File.separator + "ScoreTab" + File.separator + "arrow_back.png")).getScaledInstance(80, 50, Image.SCALE_SMOOTH);
			
			arrow_back_SELECTED = ImageIO.read(
					new File("." + File.separator + "resources" + File.separator + "assets" + File.separator + "ScoreTab" + File.separator + "arrow_back_SELECTED.png")).getScaledInstance(80, 50, Image.SCALE_SMOOTH);
			
			lives_3 = ImageIO.read(
					new File("." + File.separator + "resources" + File.separator + "assets" + File.separator + "ScoreTab" + File.separator + "3_Hearts.png")).getScaledInstance(240, 73, Image.SCALE_SMOOTH);
			
			lives_2 = ImageIO.read(
					new File("." + File.separator + "resources" + File.separator + "assets" + File.separator + "ScoreTab" + File.separator + "2_Hearts.png")).getScaledInstance(240, 73, Image.SCALE_SMOOTH);
			
			lives_1 = ImageIO.read(
					new File("." + File.separator + "resources" + File.separator + "assets" + File.separator + "ScoreTab" + File.separator + "1_Hearts.png")).getScaledInstance(240, 73, Image.SCALE_SMOOTH);
			
			Lives = new JLabel(new ImageIcon (lives_3));
			
			ARROW_BACK_scaled = new JLabel(new ImageIcon (arrow_back));
			
			ARROW_BACK_scaled.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mousePressed(MouseEvent e) {
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
			Lives.setBounds(23, 440, 305, 94);
			
			time_left=new JLabel("" + remaining_time, JLabel.CENTER);
			time_left.setForeground(Color.WHITE);
			time_left.setFont(eightBit);
			time_left.setBounds(6, 580, 350, 100);
			
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
		
		g.drawImage(Background, 0, 0, 360, 720, null);
	}

}
