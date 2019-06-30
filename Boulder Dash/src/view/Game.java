package view;

import java.awt.Toolkit;
import java.io.Serializable;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

import menu.Menu;
import menu.Options;

public class Game extends JSplitPane implements /*Runnable,*/ Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3366963664175197486L;
	
	public static int FPS = 34;
	
	private final JFrame frame;
	private final Menu menu;
	
	public Level level;
	public Score score;
	
	public boolean isReset;
	
	private void score_init() {
		
		score = new Score(frame, menu, this, level);
		
		this.setLeftComponent(level);
		this.setRightComponent(score);
		setDividerLocation(920);
		setDividerSize(0);
	}
	
	private void checkResize() {
		if(!Options.full_screen) {
			setDividerLocation(920);
		}
		else if(Options.full_screen) {
			Toolkit tk = Toolkit.getDefaultToolkit();
			double xSize = tk.getScreenSize().getWidth();
			
			setDividerLocation((int)(920*(xSize/1280)));
		}
	}
	
	public Game(JFrame frame, Menu menu) {
		
		isReset = false;
		this.frame = frame;
		this.menu = menu;
		
		setVisible(true);
		setFocusable(true);
		setEnabled(true);
		
		level = new Level(this);
		level.addKeyListener(level);
			
		score_init();
	}
	
	public void launchGame() throws NullPointerException {
		
		checkResize();
		
		level.closeThread();
		level = new Level(this);
		level.addKeyListener(level);
		
		score = new Score(frame, menu, this, level);
		score.check_resize(level);
		
		this.setLeftComponent(level);
		this.setRightComponent(score);
		isReset = true;
		
		level.launchThread();
	}
	
	public synchronized void closeThread() {
		level.closeThread();
	}

	public void youLose() {
		//qui lancia you_lose
	}

	public void youWin() {
		//qui lancia you_win
	}
}
