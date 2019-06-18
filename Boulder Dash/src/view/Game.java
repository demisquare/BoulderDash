package view;

import java.io.Serializable;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

import menu.Menu;

public class Game extends JSplitPane implements Serializable{

	private static final long serialVersionUID = 1L;

	public Level level;
	public Score score;
	
	public Game() {
		level = new Level();
		level.addKeyListener(level);
	}
	
	public void score_init(JFrame frame, Menu menu) {
		score = new Score(frame, menu, this);
		
		this.setLeftComponent(level);
		this.setRightComponent(score);
		setDividerLocation(920);
		setDividerSize(0);
	}
	
	public void reset(JFrame frame, Menu menu) {
		level = new Level();
		score = new Score(frame, menu, this);
	}
}
