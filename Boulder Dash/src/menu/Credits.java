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

public class Credits extends JPanel{

	private static final long serialVersionUID = 1L;
	private static final String CreditsPagePath =
			"." + File.separator + 
			"resources" + File.separator + 
			"assets" + File.separator + 
			"Menu" + File.separator + 
			"CreditsPage" + File.separator;

	BufferedImage background;
	Image arrow_back;
	Image arrow_back_SELECTED;
	JLabel ARROW_BACK_scaled;

	public void turn_back(JFrame frame, Menu menu) {
		
		menu.check_resize();
		frame.remove(this);
		frame.setContentPane(menu);
		frame.revalidate();
		frame.repaint();
		
		synchronized(this) {
			Music.setSong(Music.menuSong);
		}
	}
	
	public Credits(JFrame frame, Menu menu) {
		try {
			
			background = ImageIO.read(new File(CreditsPagePath + "background.png"));
			arrow_back = ImageIO.read(new File(CreditsPagePath + "arrow_back.png")).getScaledInstance(120, 80, Image.SCALE_SMOOTH);
			arrow_back_SELECTED = ImageIO.read(new File(CreditsPagePath + "arrow_back_SELECTED.png")).getScaledInstance(120, 80, Image.SCALE_SMOOTH);
	
			ARROW_BACK_scaled = new JLabel(new ImageIcon(arrow_back));
					
			ARROW_BACK_scaled.addMouseListener(new MouseListener() {
								
				@Override
				public void mousePressed(MouseEvent e) {
					synchronized(this) {
						Music.playTone("select");
					}
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
					synchronized(this) {
						Music.playTone("hover");
					}
					ARROW_BACK_scaled.setIcon(new ImageIcon(arrow_back_SELECTED));
					revalidate();
					repaint();
				}
				
				@Override public void mouseClicked(MouseEvent e) 	{}
				@Override public void mouseReleased(MouseEvent e) 	{}
			});
			
			this.setLayout(null);
			this.add(ARROW_BACK_scaled);
			ARROW_BACK_scaled.setBounds(5, 5, 146, 97);
			
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
