//AUTORE: Davide Gena

package menu;

import java.awt.Component;
import java.awt.Image;
import java.awt.Toolkit;

public class Scaling {
	
	private Scaling() {}
	
	private static double vx;
	private static double vy;
	
	private static void updateToolkit() {
		
		double tx = Toolkit.getDefaultToolkit().getScreenSize().getWidth()/1280d;
		if(tx != vx) vx = tx;
	
		double ty = Toolkit.getDefaultToolkit().getScreenSize().getHeight()/720d;
		if(ty != vy) vy = ty;
	}
	
	public static Image get(Image img, int a, int b, boolean scaled) {
		
		if(scaled) {
			updateToolkit();
			return img.getScaledInstance((int)(a*vx), (int)(b*vy), Image.SCALE_SMOOTH);		
		}
		
		return img.getScaledInstance(a, b, Image.SCALE_SMOOTH);
	}
	
	public static void set(Component L, int a, int b, int c, int d, boolean scaled) {
		
		if(scaled) {
			updateToolkit();
			L.setBounds((int)(a*vx), (int)(b*vy), (int)(c*vx), (int)(d*vy));	
		
		} else
			L.setBounds(a, b, c, d);
	}
}
