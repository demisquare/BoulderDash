package view;

import java.awt.Toolkit;
import java.io.Serializable;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

import menu.Menu;
import menu.Options;

public class Game extends JSplitPane implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3366963664175197486L;
	
	private Thread t1;
	private Thread t2;
	
	public Level level;
	public Score score;
	
	public boolean isReset;
	
	public Game() {
		
		isReset = false;
		
		level = new Level();
		level.addKeyListener(level);
		
		t2 = new Thread(level);
		t1 = new Thread(level.getWorld());
		t1.start();
		t2.start();
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
		
		level = new Level();
		level.addKeyListener(level);
		
		t1.interrupt();
		
		if(t1.isInterrupted()) {
			t1 = new Thread(level.getWorld());
			t1.start();
			
		} else {
			System.out.println("Non rilancia il level");
		}
		
		t2.interrupt();
		
		if(t2.isInterrupted()) {
			t2 = new Thread(level);
			t2.start();
			
		} else {
			System.out.println("Non rilancia il world");
		}
		
		score = new Score(frame, menu, this);
		
		isReset = true;
	}
}
