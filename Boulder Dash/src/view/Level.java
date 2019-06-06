package view;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.swing.JPanel;

import model.*;

public class Level extends JPanel implements KeyListener, Runnable {
		
	//mappa che collega ogni pressione di tastiera al movimento corrispondente
	//nello specifico: enumeratore Awt di pressione tasto , enumeratore logico di direzione
	private static final HashMap<Integer, Integer> pgMove = new HashMap<Integer, Integer>() {
		{
			put(KeyEvent.VK_LEFT, GameObject.LEFT);
			put(KeyEvent.VK_RIGHT, GameObject.RIGHT);
			put(KeyEvent.VK_UP, GameObject.UP);
			put(KeyEvent.VK_DOWN, GameObject.DOWN);
		}
	};
	
	private static Sprite spritesheet = new Sprite();
	private static Random r = new Random();	
	
	//Questa classe far� da interfaccia a TUTTA la logica di un livello
	World world;
	LocalTime lastTimePressed;
	
	//graphics for blocks
	ArrayList<BlockSprite> blockSprites;
	//graphics for both Player and Enemies
	ArrayList<LivingSprite> playerSprites;
	ArrayList<LivingSprite> enemySprites;

	public Level() {
		super();
		
		spritesheet.loadSprite("blockSpriteSheet");
		
		// crea un world...
		world = new World();
		
		//inizializza le sprites dei blocchi
		blockSprites = new ArrayList<BlockSprite>();
		
		for(int i = 0; i < world.getMap().getDimX(); ++i) {
			for(int j = 0; j < world.getMap().getDimY(); ++j) {
				
				GameObject g = world.getMap().getTile(i, j);
				
				if(world.getMap().getTile(i, j) instanceof EmptyBlock) {
					BufferedImage img = spritesheet.getSprite(1, 2);
					blockSprites.add(new BlockSprite(img, g));
				
				} else if(world.getMap().getTile(i, j) instanceof Wall) {
					BufferedImage img = spritesheet.getSprite(0, 1);
					blockSprites.add(new BlockSprite(img, g));
					
				} else if(world.getMap().getTile(i, j) instanceof Diamond) {
					BufferedImage img = spritesheet.getSprite(r.nextInt(2), 3);
					blockSprites.add(new BlockSprite(img, g));
					
				} else if(world.getMap().getTile(i, j) instanceof Ground) {
					BufferedImage img = spritesheet.getSprite(0, 2);
					blockSprites.add(new BlockSprite(img, g));
					
				} else if(world.getMap().getTile(i, j) instanceof Rock) {
					BufferedImage img = spritesheet.getSprite(r.nextInt(2), 0);
					blockSprites.add(new BlockSprite(img, g));
					
				} else if(world.getMap().getTile(i, j) instanceof Door) {
					BufferedImage img = spritesheet.getSprite(1, 1);
					blockSprites.add(new BlockSprite(img, g));
					
				}
			}
		}
		
		//inizializza le animazioni del giocatore
		playerSprites = new ArrayList<LivingSprite>();
		
		Player p = (Player) world.getPlayer();
		playerSprites.add(new LivingSprite("playerSpriteSheet", p.getSpeed(), p));
		
		//inizializza le animazioni dei nemici
		enemySprites = new ArrayList<LivingSprite>();
		
		for(int i = 0; i < world.getEnemies().size(); ++i) {
			Enemy e = (Enemy) world.getEnemies().get(i);
			enemySprites.add(new LivingSprite("enemySpriteSheet", e.getSpeed(), e));
		}
		
		lastTimePressed=java.time.LocalTime.now();
		Renderer.init(world);
		
		Thread t = new Thread(this);
		t.start();
		world.reset();
	}

	public ArrayList<BlockSprite> getBlockSprites() {
		return blockSprites;
	}
	
	public void updateGraphics() {
		//aggiorna la grafica
		for(int i = 0; i < blockSprites.size(); ++i) {
			
			BlockSprite e = blockSprites.get(i);
			if(e.getLogicObject().hasChanged()) {
				
				int x = e.getLogicObject().getX();
				int y = e.getLogicObject().getY();
				
				if(!(world.getMap().getTile(x, y).equals(e.getLogicObject()))) {
					
					GameObject newObj = world.getMap().getTile(x, y);
					
					if(newObj instanceof EmptyBlock) {
						BufferedImage img = spritesheet.getSprite(1, 2);
						blockSprites.set(i, new BlockSprite(img, newObj));
					
					} else if(newObj instanceof Wall) {
						BufferedImage img = spritesheet.getSprite(0, 1);
						blockSprites.set(i, new BlockSprite(img, newObj));
					
					} else if(newObj instanceof Diamond) {
						BufferedImage img = spritesheet.getSprite(r.nextInt(2), 3);
						blockSprites.set(i, new BlockSprite(img, newObj));
						
					} else if(newObj instanceof Ground) {
						BufferedImage img = spritesheet.getSprite(0, 2);
						blockSprites.set(i, new BlockSprite(img, newObj));
					
					} else if(newObj instanceof Rock) {
						BufferedImage img = spritesheet.getSprite(r.nextInt(2), 0);
						blockSprites.set(i, new BlockSprite(img, newObj));
						
					} else if(newObj instanceof Door) {
						BufferedImage img = spritesheet.getSprite(1, 1);
						blockSprites.set(i, new BlockSprite(img, newObj));

					}
					
					//System.out.println("GameObject in position [" + x + ", " + y + "] is getting updated...");
				}
			}
		}
	}
	
	@Override
	// permette di ridefinire i componenti del pannello di default.
	// questo metodo viene invocato ogni volta che usiamo il metodo repaint().
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		updateGraphics();
		revalidate();
		
		for(int i = 0; i < enemySprites.size(); ++i) {
			enemySprites.get(i).movePose(GameObject.DOWN);
			enemySprites.get(i).getAnimation().update();
		}
		
		// disegna lo sprite del player...
		//g.drawImage(world.getPlayer().ls.getAnimation().getSprite(), world.getPlayer().getX(), world.getPlayer().getY(), null);
		Renderer.render(g, this);
	}

	// eventi di movimento da tastiera del player...

	@Override public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		if( (java.time.LocalTime.now().minusNanos(100000000)).compareTo(lastTimePressed) > 0 ) {
			
			lastTimePressed = java.time.LocalTime.now();
			
			if(pgMove.containsKey(e.getKeyCode())) {
				//a static map instead of a switch
				playerSprites.get(0).movePose(pgMove.get(e.getKeyCode()));
				world.getPlayer().update(pgMove.get(e.getKeyCode()));	
			}
		
			playerSprites.get(0).getAnimation().update();
			
			//world.dijkstra(); inefficient.
			
			repaint();
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		
		playerSprites.get(0).getAnimation().stop();
		playerSprites.get(0).getAnimation().reset();
		//a static map instead of a switch
		if(pgMove.containsKey(e.getKeyCode()))
			playerSprites.get(0).standPose(pgMove.get(e.getKeyCode()));
		
		playerSprites.get(0).getAnimation().update();

		repaint();	
	}

	@Override
	public void run() {
		while(true) {
			if(world.isHasChanged()) {
				
				world.reset();
				repaint();
			}
			
			try {
				Thread.sleep(34);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}		
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
