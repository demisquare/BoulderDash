package graphics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;

public class Score extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	BufferedImage Background;
	
	public static int remaining_time = 150; //150 secondi per livello
	
	JLabel time_left;
	
	
	public Score() {
		try {
			Font eightBit = Font.createFont(Font.TRUETYPE_FONT, new File("." + File.separator + "resources" + File.separator + "assets" + File.separator + "8BITFONT.TTF")).deriveFont(80f);
			
			Background = ImageIO.read(
					new File("." + File.separator + "resources" + File.separator + "assets" + File.separator + "score_background.png"));
		
			time_left=new JLabel("" + remaining_time, JLabel.CENTER);
			time_left.setForeground(Color.WHITE);
			time_left.setFont(eightBit);
			time_left.setBounds(6, 580, 350, 100);
			
			
			this.setLayout(null); //se non settiamo a null non possiamo usare il setBounds.
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
