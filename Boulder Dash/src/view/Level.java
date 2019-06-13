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

	private static final long serialVersionUID = 1L;

	//mappa che collega ogni pressione di tastiera al movimento corrispondente
	//nello specifico: enumeratore Awt di pressione tasto , enumeratore logico di direzione
	private static final HashMap<Integer, Integer> pgMove = new HashMap<Integer, Integer>() {

		private static final long serialVersionUID = 1L;

		{
			put(KeyEvent.VK_LEFT, GameObject.LEFT);
			put(KeyEvent.VK_RIGHT, GameObject.RIGHT);
			put(KeyEvent.VK_UP, GameObject.UP);
			put(KeyEvent.VK_DOWN, GameObject.DOWN);
		}
	};
	
	private int FPS = 34;
	private static Sprite spritesheet = new Sprite();
	private static Random r = new Random();	
	
	/*AudioPlayer game_song = new AudioPlayer("." + File.separator +
			  "resources" + File.separator +
			  "assets" + File.separator +
			  "music" + File.separator +
			   "game_song"+ ".wav");*/
	
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
		//world.reset();
	}

	public ArrayList<BlockSprite> getBlockSprites() {
		return blockSprites;
	}
	
	public World getWorld() {
		// TODO Auto-generated method stub
		return world;
	}

	public void setWorld(World world) {
		// TODO Auto-generated method stub
		this.world = world;
		
	}
	
	public void updateGraphics() {
		
		//aggiorna la grafica
		for(int i = 0; i < blockSprites.size(); ++i) {
			
			//se il blocco logico e' cambiato nell'ultimo world.update()...
			try {
			if(blockSprites.get(i).getLogicObject().isDead()) {
												
					GameObject newObj = blockSprites.get(i).getLogicObject().getSuccessor();
					
					BufferedImage img = spritesheet.getSprite(1, 2);
					blockSprites.set(i, new BlockSprite(img, newObj));	
				}
			} catch(NullPointerException e) {
				e.printStackTrace();
			}
		}
	}

	
	// permette di ridefinire i componenti del pannello di default.
	// questo metodo viene invocato ogni volta che usiamo il metodo repaint().
	@Override
	protected void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		updateGraphics();
		revalidate();
		
		for(int i = 0; i < enemySprites.size(); ++i) {
			
			if(!enemySprites.get(i).logicObj.isDead()) {
				
				//per accelerare l'animazione dell'Enemy, aumentare la costante 1 	
				if(enemySprites.get(i).counter >= (125/(FPS*1))) {
				
					enemySprites.get(i).movePose(GameObject.DOWN);
					enemySprites.get(i).getAnimation().update();
					enemySprites.get(i).counter = 0;
			
				} else {
					enemySprites.get(i).counter += 1;
				}
			}
		}
		
		Renderer.render(g, this);
	}

	// eventi di movimento da tastiera del player...

	@Override public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		if( (java.time.LocalTime.now().minusNanos(100000000)).compareTo(lastTimePressed) > 0 ) {
			
			lastTimePressed = java.time.LocalTime.now();
			
			if(!world.getPlayer().isDead()) {
				
				if(pgMove.containsKey(e.getKeyCode())) {
					
					//a static map instead of a switch
					playerSprites.get(0).movePose(pgMove.get(e.getKeyCode()));
					world.getPlayer().update(pgMove.get(e.getKeyCode()));	
				
				}
			
				playerSprites.get(0).getAnimation().update();
				
			}
		
			//world.dijkstra(); inefficient.
			
			repaint();
			world.reset();
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		
		if(!world.getPlayer().isDead()) {
			
			playerSprites.get(0).getAnimation().stop();
			playerSprites.get(0).getAnimation().reset();
			//a static map instead of a switch
			if(pgMove.containsKey(e.getKeyCode()))
				playerSprites.get(0).standPose(pgMove.get(e.getKeyCode()));
		
			playerSprites.get(0).getAnimation().update();
		}
		
		repaint();
		world.reset();

	}

	@Override
	public void run() {
		while(true) {
			
			repaint();
			world.reset();
			
			try {
				Thread.sleep(FPS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}		
	}
}
