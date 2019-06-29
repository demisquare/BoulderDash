package menu;

import java.awt.Image;
import java.awt.Toolkit;

public class ScaleImage {

	private static double vx;
	private static double vy;
	
	private ScaleImage() {}
	
	public static Image get(Image img, int a, int b) {
		
		double tx = Toolkit.getDefaultToolkit().getScreenSize().getWidth()/1280d;
		if(tx != vx)
			vx = tx;
		
		double ty = Toolkit.getDefaultToolkit().getScreenSize().getHeight()/720d;
		if(ty != vy)
			vy = ty;
		
		return img.getScaledInstance((int)(a*vx), (int)(b*vy), Image.SCALE_SMOOTH);
	}
}
