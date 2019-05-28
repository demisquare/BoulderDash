package graphics;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPanel;

import logic.World;
import logic.Living;

public class Level extends JPanel implements KeyListener {
	
	private static final long serialVersionUID = -2545695383117923190L;
	private static final HashMap<Integer, Integer> pgMove = new HashMap<Integer, Integer>(){
		{
			put(KeyEvent.VK_LEFT, Living.LEFT);
			put(KeyEvent.VK_RIGHT, Living.RIGHT);
			put(KeyEvent.VK_UP, Living.UP);
			put(KeyEvent.VK_DOWN, Living.DOWN);
		}
	};
	
	//Questa classe far� da interfaccia a TUTTA la logica di un livello
	World world;
	LocalTime lastTimePressed;
	//graphics for both Player and Enemies
	LivingSprite playerSprite;
	ArrayList<LivingSprite> enemySprites;

	public Level() {
		super();

		// crea un world...
		world = new World();
		
		//inizializza le animazioni del giocatore
		playerSprite = new LivingSprite("playerSpriteSheet", world.getPlayer().getSpeed(), world.getPlayer());
		
		//inizializza le animazioni dei nemici
		enemySprites = new ArrayList<LivingSprite>();
		for(int i = 0; i < world.getEnemies().size(); ++i)
			enemySprites.add(new LivingSprite("enemySpriteSheet", world.getEnemies().get(i).getSpeed(), world.getEnemies().get(i)));
		
		
		lastTimePressed=java.time.LocalTime.now();
		Renderer.init(world);
	}

	@Override
	// permette di ridefinire i componenti del pannello di default.
	// questo metodo viene invocato ogni volta che usiamo il metodo repaint().
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
			
		world.update();
		for(int i = 0; i < enemySprites.size(); ++i)
		{
			enemySprites.get(i).movePose(Living.DOWN);
			enemySprites.get(i).getAnimation().update();
		}
		
		// disegna lo sprite del player...
		//g.drawImage(world.getPlayer().ls.getAnimation().getSprite(), world.getPlayer().getX(), world.getPlayer().getY(), null);
		Renderer.render(g, this);

	}

	// eventi di movimento da tastiera del player...

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if( (java.time.LocalTime.now().minusNanos(100000000)).compareTo(lastTimePressed) > 0 ) {
			
			lastTimePressed = java.time.LocalTime.now();
			
			if(pgMove.containsKey(e.getKeyCode())) {
				//a static map instead of a switch
				playerSprite.movePose(pgMove.get(e.getKeyCode()));
				world.getPlayer().move(pgMove.get(e.getKeyCode()));	
			}
		
			playerSprite.getAnimation().update();
			
			//world.dijkstra(); inefficient.
			
			repaint();
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		
		playerSprite.getAnimation().stop();
		playerSprite.getAnimation().reset();
		//a static map instead of a switch
		if(pgMove.containsKey(e.getKeyCode()))
			playerSprite.standPose(pgMove.get(e.getKeyCode()));
		
		playerSprite.getAnimation().update();
		repaint();	
	}
	
/*	
	@Override
	public void keyPressed(KeyEvent e) {
		if( (java.time.LocalTime.now().minusNanos(100000000)).compareTo(lastTimePressed) > 0 )
		{
			lastTimePressed=java.time.LocalTime.now();
		// TODO Auto-generated method stub
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			//System.out.println("left");
			playerSprite.movePose(Living.LEFT);
			world.getPlayer().move(Living.LEFT);
			break;

		case KeyEvent.VK_RIGHT:
			//System.out.println("right");
			playerSprite.movePose(Living.RIGHT);
			world.getPlayer().move(Living.RIGHT);
			break;

		case KeyEvent.VK_UP:
			//System.out.println("up");
			playerSprite.movePose(Living.UP);
			world.getPlayer().move(Living.UP);
			break;

		case KeyEvent.VK_DOWN:
			//System.out.println("down");
			playerSprite.movePose(Living.DOWN);
			world.getPlayer().move(Living.DOWN);
			break;

		}
		
		playerSprite.getAnimation().update();
		repaint();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		playerSprite.getAnimation().stop();
		playerSprite.getAnimation().reset();
		
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			//System.out.println("left");
			playerSprite.standPose(Living.LEFT);
			break;

		case KeyEvent.VK_RIGHT:
			//System.out.println("right");
			playerSprite.standPose(Living.RIGHT);
			break;

		case KeyEvent.VK_UP:
			//System.out.println("up");
			playerSprite.standPose(Living.UP);
			break;

		case KeyEvent.VK_DOWN:
			//System.out.println("down");
			playerSprite.standPose(Living.DOWN);
			break;

		}
		playerSprite.getAnimation().update();
		repaint();
	}
*/
}
