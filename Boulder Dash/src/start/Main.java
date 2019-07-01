package start;

import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import audio.Music;

import menu.*;

public class Main implements Runnable {
	
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
// 		1280x720 risoluzione gioco, 29px in pi� in altezza per la barra tel titolo della finestra.
		frame.setSize(1280, 749); 
		menu = new Menu(frame);
// 		IMPOSTO IL MENU DI DEFAULT
		frame.setContentPane(menu);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);	
	
//		__________AUDIO ENGINE____________
		Music.backgroundMusic = Music.menuSong;	
		t = new Thread(new Runnable() {
			@Override
			public void run() {
						
				while (true) {
					
					synchronized(this) {
						Music.start();
					}
							
					try {
						Thread.sleep(34);
					} catch (InterruptedException e) {
						return;
					}
				}
			}
		});
		t.start();		
	}
}
