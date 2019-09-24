package view;

import java.awt.Toolkit;

import java.io.Serializable;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

import ai.ASPEngine;
import audio.Music;

import menu.Menu;
import menu.Options;
import menu.Options.Difficulty;
import model.Host;
import model.Player;
import menu.You_Lose;
import menu.You_Win;

public class Game extends JSplitPane implements /*Runnable,*/ Serializable {
	
	private static final long serialVersionUID = -3366963664175197486L;
	public static int FPS = 34;
	
	private final JFrame frame;
	private final Menu menu;
	private final You_Lose youlose;
	private final You_Win youwin;
	
	private Difficulty startingDifficulty;
	private Thread t2;
	private int stage;

	public Level level;
	public Score score;
	public ASPEngine ai;
	
	public boolean isReset;	
	
	public Game(JFrame frame, Menu menu) {
		
		super();
		
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
		
		if(Options.ai)
			ai = new ASPEngine(this);
		else		
		level.addKeyListener(level);
			
		score_init();
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
	
	private void score_init() {
		
		score = new Score(frame, menu, this, level);
		
		this.setLeftComponent(level);
		this.setRightComponent(score);
		setDividerLocation(920);
		setDividerSize(0);
	
	}
	
	public void launchGame() throws NullPointerException{
		
		checkResize();
	
		if(startingDifficulty == null) {
			startingDifficulty = Options.difficulty;
		}
		
		if(stage < 3) {
			
			if(ai!=null)
				ai.stop();
			
			level.closeThread();
			level = new Level(this, stage, null);
			
			if(Options.ai)
				ai = new ASPEngine(this);
			else			
			level.addKeyListener(level);
			
			score = new Score(frame, menu, this, level);
		
			score.check_resize();
			this.setLeftComponent(level);
			this.setRightComponent(score);
			level.requestFocusInWindow();
			
			level.setScore(score);
			level.launchThread();
			
			if(Options.ai)
				ai.start();

			isReset = true;
			
			setVisible(true);
			setFocusable(true);
			setEnabled(true);

			++stage;
			
		} else
			youWin();
	}
	
	public synchronized void closeThread() {
		if(t2 != null && t2.isAlive())
			t2.interrupt();
		if(Options.ai)
			ai.stop();
		
		level.closeThread();
	}

	public void youLose() {
		
		stage = 0;
		score.setTotal_score(0);
		
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