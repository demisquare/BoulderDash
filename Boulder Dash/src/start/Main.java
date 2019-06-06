package start;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import menu.Credits;
import menu.Menu;
import view.*;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame("Boulder Dash");
		frame.setSize(1280, 749); // 1280x720 risoluzione gioco, 29px in pi� in altezza per la barra tel titolo
								// della finestra.
		frame.setResizable(false);

		Menu menu = new Menu();
		frame.setContentPane(menu);
		
		Credits credits = new Credits();

		Level level = new Level();
		level.setBackground(Color.WHITE);
		
		Score score = new Score();
		score.setBackground(Color.BLACK);
		
		JSplitPane game = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, level, score);
		game.setDividerLocation(920);
		game.setDividerSize(0);
		

		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					if (menu.start_selected) { //AVVIO DEL GIOCO
						frame.remove(menu);
						frame.setContentPane(game);
						level.addKeyListener(level);
						level.setFocusable(true);
						frame.revalidate();
						frame.repaint();
						//________FOCUS SETTATO MANUALMENTE_______
						try {
							Robot r=new Robot();
							r.keyPress(KeyEvent.VK_TAB);
							r.keyRelease(KeyEvent.VK_TAB);
						} catch (AWTException e1) {
							e1.printStackTrace();
						}
						//____________________________________________
						menu.start_selected=false;
					}
					if (menu.credits_selected) { //AVVIO DEI CREDITI
						frame.remove(menu);
						frame.setContentPane(credits);
						frame.revalidate();
						frame.repaint();
						menu.credits_selected=false;
					}
					if (credits.turn_back) { //TORNO AL MENU DALLA SCHERMATA DEI CREDITI
						frame.remove(credits);
						frame.setContentPane(menu);
						frame.revalidate();
						frame.repaint();
						credits.turn_back=false;
					}
					try {
						Thread.sleep(34);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
//		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	
		
		
	}

}
