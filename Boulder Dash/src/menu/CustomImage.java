package menu;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JLabel;

public class CustomImage {
	
	private static double vx;
	private static double vy;
	
	private static void updateToolkit() {
		
		double tx = Toolkit.getDefaultToolkit().getScreenSize().getWidth()/1280d;
		if(tx != vx) vx = tx;
	
		double ty = Toolkit.getDefaultToolkit().getScreenSize().getHeight()/720d;
		if(ty != vy) vy = ty;
	}
	
	private static Image get(Image img, int a, int b, boolean scaled) {
		
		if(scaled) {
			updateToolkit();
			return img.getScaledInstance((int)(a*vx), (int)(b*vy), Image.SCALE_SMOOTH);		
		}
		
		return img.getScaledInstance(a, b, Image.SCALE_SMOOTH);
	}
	
	public static void set(JLabel L, int a, int b, int c, int d, boolean scaled) {
		
		if(scaled) {
			updateToolkit();
			L.setBounds((int)(a*vx), (int)(b*vy), c, d);	
		
		} else
			L.setBounds(a, b, c, d);
	}	
	
	private final Image img;
	private Image actualInstance;
	
	public CustomImage(Image img) {
		this.img = img;
		actualInstance = null;
	}
	
	public void resize(int a, int b, boolean scaled) {
		actualInstance = CustomImage.get(img, a, b, scaled);
	}
	
	public Image getInstance() {
		return actualInstance;
	}
}
