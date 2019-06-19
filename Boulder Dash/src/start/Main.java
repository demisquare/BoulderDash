package start;

import javax.swing.JFrame;
import audio.Music;
import menu.*;

public class Main {
	
	Long en = 2L;

	public static void main(String[] args) {

		// _____________INIT________________
		JFrame frame = new JFrame("Boulder Dash");
		frame.setSize(1280, 749); // 1280x720 risoluzione gioco, 29px in pi� in altezza per la barra tel titolo
									// della finestra.
		
		Menu menu = new Menu(frame);
		// _________________________________
		
		Music.backgroundMusic = Music.menuSong;
		
		//__________AUDIO ENGINE____________
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					Music.start();
					try {
						Thread.sleep(34);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
		//__________________________________		
		
		frame.setContentPane(menu); // IMPOSTO IL MENU DI DEFAULT
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}

}
