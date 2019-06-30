package view;

import java.awt.Toolkit;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

import menu.Menu;
import menu.Options;

public class Game extends JSplitPane implements /*Runnable,*/ Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3366963664175197486L;
	
	public static int FPS = 34;
	
	private Thread t2;
	
	public Level level;
	public Score score;
	
	public boolean isReset;
	
	private void score_init(JFrame frame, Menu menu) {
		
		score = new Score(frame, menu, this, level);
		
		this.setLeftComponent(level);
		this.setRightComponent(score);
		setDividerLocation(920);
		setDividerSize(0);
		
		launchThread();
	}
	
	public Game(JFrame frame, Menu menu) {
		
		isReset = false;
		
		setVisible(true);
		setFocusable(true);
		setEnabled(true);
		
		level = new Level(this);
		level.addKeyListener(level);
			
		score_init(frame, menu);
	}
	
	public void launchGame(JFrame frame, Menu menu) throws NullPointerException{
		
		level.closeThread();
		level = new Level(this);
		level.addKeyListener(level);
		
		//score.closeThread();
		score = new Score(frame, menu, this, level);
		
		score.check_resize(level);
		this.setLeftComponent(level);
		this.setRightComponent(score);
		
		level.launchThread();
		
		isReset = true;
	}

	public synchronized void launchThread() { 
		
		if(t2 != null && t2.isAlive())
			t2.interrupt();
		
		t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					if(!Options.full_screen) {
						setDividerLocation(920);
					}
					else if(Options.full_screen) {
						Toolkit tk = Toolkit.getDefaultToolkit();
						double xSize = tk.getScreenSize().getWidth();
						
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
	
	public synchronized void closeThread() {
		if(t2 != null && t2.isAlive())
			t2.interrupt();
		level.closeThread();
	}

	public void youLose() {
		//qui lancia you_lose
	}

	public void youWin() {
		//qui lancia you_win
	}
}
