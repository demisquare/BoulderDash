package view;

import java.awt.Toolkit;

import java.io.Serializable;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

import audio.Music;
import menu.Menu;
import menu.Options;
import menu.Options.Difficulty;
import menu.You_Lose;
import menu.You_Win;

public class Game extends JSplitPane implements /*Runnable,*/ Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3366963664175197486L;
	
	public static int FPS = 34;
	
	private Thread t2;
	private final JFrame frame;
	private final Menu menu;
	
	public Level level;
	public Score score;
	
	public boolean isReset;
	
	private Difficulty startingDifficulty;
	private int stage;
	
	private final You_Lose youlose;
	private final You_Win youwin;
	
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
	
	private void score_init() {
		
		score = new Score(frame, menu, this, level);
		
		this.setLeftComponent(level);
		this.setRightComponent(score);
		setDividerLocation(920);
		setDividerSize(0);
	
	}
	
	public Game(JFrame frame, Menu menu) {
		
		stage = 0;
		startingDifficulty = null;
		
		youlose = new You_Lose(frame, this, menu);
		youwin = new You_Win(frame, this, menu);
		
		isReset = false;
		this.frame = frame;
		this.menu = menu;
		
		setVisible(true);
		setFocusable(true);
		setEnabled(true);
		
		level = new Level(this, stage, null);
		level.addKeyListener(level);
			
		score_init();
	}
	
	public void launchGame() throws NullPointerException{
		
		checkResize();
	
		if(startingDifficulty == null) {
			startingDifficulty = Options.difficulty;
		}
		
		if(stage < 3) {
			
			level.closeThread();
			level = new Level(this, stage, null);
			level.addKeyListener(level);
			
			score = new Score(frame, menu, this, level);
		
			score.check_resize(level);
			this.setLeftComponent(level);
			this.setRightComponent(score);
			
			level.setScore(score);
			
			level.launchThread();
			isReset = true;

			++stage;
			
		} else {
			youWin();
		}
	}
	
	public synchronized void closeThread() {
		if(t2 != null && t2.isAlive())
			t2.interrupt();
		level.closeThread();
	}

	public void youLose() {
		
		stage = 0;
		
		frame.remove(this);
		youlose.check_resize();
		frame.setContentPane(youlose);
		frame.revalidate();
		frame.repaint();
		
		synchronized(this) {
			Music.backgroundMusic.stop();
			Music.playTone("lose");
		}
	}

	public void youWin() {
		
		stage = 0;
		
		frame.remove(this);
		youwin.check_resize();
		frame.setContentPane(youwin);
		frame.revalidate();
		frame.repaint();
		
		synchronized(this) {
			Music.backgroundMusic.stop();
			Music.playTone("victory");
		}
	}

	public void setStage(int i) {
		stage = i;
	}

	public Difficulty getStartingDifficulty() {
		return startingDifficulty;
	}
}