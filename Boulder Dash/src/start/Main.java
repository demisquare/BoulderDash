package start;

import javax.swing.JFrame;
import menu.Credits;
import menu.Menu;
import menu.Options;
import view.*;

public class Main {

	public static void main(String[] args) {
		
		//__________FRAME INIT_____________
		JFrame frame = new JFrame("Boulder Dash");
		frame.setSize(1280, 749); // 1280x720 risoluzione gioco, 29px in pi� in altezza per la barra tel titolo
								// della finestra.

		
		
		
		//__________PANEL INIT_____________
		Menu menu = new Menu();
		frame.setContentPane(menu); //IMPOSTO IL MENU DI DEFAULT
		Game game = new Game();
		Credits credits = new Credits();
		Options options = new Options();
		//_________________________________
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					if (menu.start_selected) { //AVVIO DEL GIOCO
						frame.remove(menu);
						frame.setContentPane(game);
						game.level.requestFocusInWindow();
						frame.revalidate();
						frame.repaint();
						menu.start_selected=false;
					}
					if (menu.credits_selected) { //AVVIO DEI CREDITI
						frame.remove(menu);
						frame.setContentPane(credits);
						frame.revalidate();
						frame.repaint();
						menu.credits_selected=false;
					}
					if (menu.options_selected) { //AVVIO DELLE OPZIONI
						frame.remove(menu);
						frame.setContentPane(options);
						frame.revalidate();
						frame.repaint();
						menu.options_selected=false;
					}
					if (credits.turn_back) { //TORNO AL MENU DALLA SCHERMATA DEI CREDITI
						frame.remove(credits);
						frame.setContentPane(menu);
						frame.revalidate();
						frame.repaint();
						credits.turn_back=false;
					}
					if (options.turn_back) { //TORNO AL MENU DALLA SCHERMATA DELLE OPZIONI
						frame.remove(options);
						frame.setContentPane(menu);
						frame.revalidate();
						frame.repaint();
						options.turn_back=false;
					}
					if (game.score.turn_back) { //TORNO AL MENU DAL GIOCO
						frame.remove(game);
						frame.setContentPane(menu);
						frame.revalidate();
						frame.repaint();
						game.score.turn_back=false;
					}
					try {
						Thread.sleep(34);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();

		
//		if(options.full_screen) {	
//			frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
//			frame.setUndecorated(true);
//		}
//		else if(!options.full_screen) {
//			frame.setUndecorated(false);
//			frame.setSize(1280, 749);
//		}

	
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	
	}

}
