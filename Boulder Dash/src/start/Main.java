package start;

import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import audio.Music;
import menu.*;

//import java.awt.event.WindowEvent;
//import java.awt.event.WindowListener;

public class Main implements Runnable/*, WindowListener*/ {
	
	static JFrame frame = null;
	static Menu menu 	= null;
	static Thread t 	= null;
	
	private Main() {}
	
	public static void main(String[] args) {

		Main m = new Main();
		
		try {
			SwingUtilities.invokeAndWait(m);
		} catch (InvocationTargetException | InterruptedException e1) {
			e1.printStackTrace();
		}
		
	}

	@Override
	public void run() {
		
		// _____________INIT________________
		frame = new JFrame("Boulder Dash");
		frame.setSize(1280, 749); // 1280x720 risoluzione gioco, 29px in pi� in altezza per la barra tel titolo della finestra.		
		menu = new Menu(frame);
		frame.setContentPane(menu); // IMPOSTO IL MENU DI DEFAULT
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);	
	}
}
