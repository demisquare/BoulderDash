package view;

import java.io.Serializable;

import javax.swing.JSplitPane;

public class Game extends JSplitPane implements Serializable{

	private static final long serialVersionUID = 1L;

	public Level level;
	public Score score;
	
	public Game() {
		level = new Level();
		score = new Score();
		level.addKeyListener(level);
		
		this.setLeftComponent(level);
		this.setRightComponent(score);
		setDividerLocation(920);
		setDividerSize(0);
		
	}
	
	public void reset() {
		level = new Level();
		score = new Score();
	}
}
