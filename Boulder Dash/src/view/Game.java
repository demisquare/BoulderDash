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
	private Thread t2;
	
	public Level level;
	public Score score;
	
	public boolean isReset;
	
	private void score_init(JFrame frame, Menu menu) {
		
		score = new Score(frame, menu, this);
		
		this.setLeftComponent(level);
		this.setRightComponent(score);
		setDividerLocation(920);
		setDividerSize(0);
		
		Toolkit tk = Toolkit.getDefaultToolkit();
		double xSize = tk.getScreenSize().getWidth();
		
		t2 = new Thread(new Runnable() {
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
						return;
					}
				}
			}
		});
		
		t2.start();
	}
	
	public Game(JFrame frame, Menu menu) {
		
		isReset = false;
		
		setVisible(true);
		setFocusable(true);
		setEnabled(true);
		
		level = new Level();
		level.addKeyListener(level);
		
		t = new Thread(this);
		
		t.start();
		
		score_init(frame, menu);
	}
	
	public void reset(JFrame frame, Menu menu) {
		
		t.interrupt();
		
		while(!t.isInterrupted()) {}
		
		level = new Level();
		level.addKeyListener(level);
		
		t = new Thread(this);
		
		t.start();
		
		score.closeThread();
		score = new Score(frame, menu, this);
		
		this.setLeftComponent(level);
		this.setRightComponent(score);
		
		isReset = true;
	}

	@Override
	public void run() {
			
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
				
			try {
				Thread.sleep(FPS);
			
			}catch(InterruptedException e) {
				return;
			
			}
		}	
	}
	
	public synchronized void closeThread() {
		t.interrupt();
		t2.interrupt();
		score.closeThread();
	}
	
//	public synchronized void wakeThread() { 
//		t.notify();
//		t2.notify();
//		score.wakeThread();
//	}
	
//	public synchronized void stopThread() throws InterruptedException { 
//		t.wait();
//		t2.wait();
//		score.stopThread();
//	}
}
