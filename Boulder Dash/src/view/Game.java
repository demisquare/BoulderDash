package view;

import java.awt.Toolkit;
import java.io.Serializable;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

import menu.Menu;
import menu.Options;

public class Game extends JSplitPane implements Runnable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3366963664175197486L;
	
	public static int FPS = 34;
	
	private Thread t;
	
	public Level level;
	public Score score;
	
	public boolean isReset;
	
	public Game() {
		
		isReset = false;
		
		setVisible(true);
		setFocusable(true);
		setEnabled(true);
		
		level = new Level();
		level.addKeyListener(level);
		
		t = new Thread(this);
		
		t.start();
	}
	
	public void score_init(JFrame frame, Menu menu) {
		
		score = new Score(frame, menu, this);
		
		this.setLeftComponent(level);
		this.setRightComponent(score);
		setDividerLocation(920);
		setDividerSize(0);
		
		Toolkit tk = Toolkit.getDefaultToolkit();
		double xSize = tk.getScreenSize().getWidth();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					if(!Options.full_screen) {
						setDividerLocation(920);
					}
					else if(Options.full_screen) {
						setDividerLocation((int)(920*(xSize/1280)));
					}
					try {
						Thread.sleep(34);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	
	public void reset(JFrame frame, Menu menu) {
		
		t.interrupt();
		
		level = new Level();
		level.addKeyListener(level);
		
		t = new Thread(this);
		
		t.start();
		
		score = new Score(frame, menu, this);
		
		isReset = true;
	}

	@Override
	public void run() {
		try {
			
			int counter = 0; 
			
			while(true) {
				
				++counter;
				if(counter == 200/FPS) {
					
					counter = 0;
					level.getWorld().update();
				}
				
				revalidate();
				repaint();
				level.getWorld().reset();
				
				Thread.sleep(FPS);
				
			}
			
		}catch(InterruptedException e) {
			e.printStackTrace();
			return;
		}	
	}
}
