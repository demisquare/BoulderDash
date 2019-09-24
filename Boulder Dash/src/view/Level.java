//AUTORE: Davide Caligiuri
package view;

import java.awt.Color;
import java.awt.Graphics;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.awt.image.BufferedImage;

import java.time.LocalTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.swing.JPanel;

import ai.IntelligentEnemy;

import menu.Options;
import menu.Options.Difficulty;

import model.*;

public class Level extends JPanel /*implements KeyListener*/ {
	
	private static final long serialVersionUID = 9009048960794622320L;

// 	mappa che collega ogni pressione di tastiera al movimento corrispondente
// 	nello specifico: enumeratore Awt di pressione tasto , enumeratore logico di
// 	direzione
	/*public static final HashMap<Integer, Integer> pgMove = new HashMap<Integer, Integer>() {

		private static final long serialVersionUID = -6595629016610529055L;
		{
			put(KeyEvent.VK_LEFT, GameObject.LEFT);
			put(KeyEvent.VK_RIGHT, GameObject.RIGHT);
			put(KeyEvent.VK_UP, GameObject.UP);
			put(KeyEvent.VK_DOWN, GameObject.DOWN);
		}
	};	*/
//	mappa che collega ogni classe a una coppia di interi, utilizzati per l'inizializzazione degli sprites
	public static final HashMap<String, Integer[]> blocks = new HashMap<String, Integer[]>() {
		
		private static final long serialVersionUID = 7591763942826182803L;
		{
			put(EmptyBlock.class.getCanonicalName(), new Integer[] { 1, 2 });
			put(Wall.class.getCanonicalName(), new Integer[] { 0, 1 });
			put(Ground.class.getCanonicalName(), new Integer[] { 0, 2 });
			put(Door.class.getCanonicalName(), new Integer[] { 1, 1 });
			put(Diamond.class.getCanonicalName(), new Integer[] { 2, 3 });
			put(Rock.class.getCanonicalName(), new Integer[] { 2, 0 });
		}
	};

	private static Sprite spritesheet = new Sprite();
	private static Random r = new Random();

	private boolean isComplete;
	private int prevDiamonds;
	private int FPS = 30;
	private boolean mouseReleased = false;
	private Thread t;
	private Game game;
	private Score score;
// 	Questa classe fara' da interfaccia a TUTTA la logica di un livello
	World world;
	LocalTime lastTimePressed;
// 	graphics for blocks
	ArrayList<BlockSprite> blockSprites;
// 	graphics for both Player and Enemies
	ArrayList<LivingSprite> playerSprites;
	ArrayList<LivingSprite> hostSprites;
	ArrayList<LivingSprite> enemySprites;

	public Level(Game g, int stage, Score score) {
	
		super();
		
		isComplete = false;
		
		this.score = score;
		
		setBackground(Color.GRAY);
		setFocusable(true);
		setVisible(true);
		setEnabled(true);

		// crea un world...
		world = new World(stage);
		game = g;
		
		prevDiamonds = world.getMap().getNumDiamonds();
		
		t = null;
		
		initGraphics();

		lastTimePressed = java.time.LocalTime.now();
		Renderer.init(world);
	
		isComplete = true;
	}

	private void initGraphics() {

		spritesheet.loadSprite("blockSpriteSheet");

		// inizializza le sprites dei blocchi
		blockSprites = new ArrayList<BlockSprite>();

		for (int i = 0; i < world.getMap().getDimX(); ++i) {
			for (int j = 0; j < world.getMap().getDimY(); ++j) {

				GameObject g = world.getMap().getTile(i, j);

				BufferedImage img = null;
				if (blocks.containsKey(g.getClass().getCanonicalName())) {

					Integer[] temp = blocks.get(g.getClass().getCanonicalName());

					// a static map instead of a switch
					if (g instanceof Diamond || g instanceof Rock) {
						img = spritesheet.getSprite(r.nextInt(temp[0]), temp[1]);
					} else {
						img = spritesheet.getSprite(temp[0], temp[1]);
					}
				}

				if (img != null) {
					blockSprites.add(new BlockSprite(img, g));
				}
			}
		}

		// inizializza le animazioni del giocatore
		playerSprites = new ArrayList<LivingSprite>();

		// inizializza le animazioni del giocatore
		hostSprites = new ArrayList<LivingSprite>();

		Player p = (Player) world.getPlayer();
		Host h = null;
		if (Options.multiplayer)
			h = (Host) world.getHost();

		if (Options.multiplayer) {
			if (Options.host) {
				playerSprites.add(new LivingSprite("hostSpriteSheet", p.getSpeed(), p));
				hostSprites.add(new LivingSprite("playerSpriteSheet", p.getSpeed(), h));
			} else {
				playerSprites.add(new LivingSprite("playerSpriteSheet", p.getSpeed(), p));
				hostSprites.add(new LivingSprite("hostSpriteSheet", p.getSpeed(), h));
			}
		} else {
			playerSprites.add(new LivingSprite("playerSpriteSheet", p.getSpeed(), p));
		}

		// inizializza le animazioni dei nemici
		enemySprites = new ArrayList<LivingSprite>();

		for (int i = 0; i < world.getEnemies().size(); ++i) {
			IntelligentEnemy e = (IntelligentEnemy) world.getEnemies().get(i);
			enemySprites.add(new LivingSprite("enemySpriteSheet", e.getSpeed(), e));
		}
	}
	
//	questo metodo è l'unico motivo per l'esistenza di DummyClass:
//	permette di gestire in maniera uniforme l'aggiornamento di ogni tipologia di sprite
	private void updateCategory(ArrayList<? extends DummyClass> Arr, boolean toRemove, boolean toAdd) {
		
		for (int i = 0; i < Arr.size(); ++i) {
			
			GameObject g = Arr.get(i).getLogicObj();
			// se il blocco logico e' cambiato nell'ultimo world.update()...
			if(g.isDead() && g.getSuccessor() != null) {
				GameObject newObj = g.getSuccessor();
				BufferedImage img = spritesheet.getSprite(1, 2);
				if(!toAdd)
					blockSprites.set(i, new BlockSprite(img, newObj));
				else
					blockSprites.add(i, new BlockSprite(img, newObj));
				if(toRemove) {
					Arr.remove(i);
				}
			}
		}
	}

// 	sincronizza la grafica con la logica
	public synchronized void updateGraphics() throws NullPointerException {

		updateCategory(blockSprites, false, false);
		updateCategory(playerSprites, true, true);		
		updateCategory(enemySprites, true, true);
		if(Options.multiplayer && Options.host)
			updateCategory(hostSprites, true, true);
		
		// aggiorna gli enemies 
		for (int i = 0; i < enemySprites.size(); ++i) {
			Enemy e = (Enemy) enemySprites.get(i).getLogicObj();
			if (!e.isDead()) {
				// per accelerare l'animazione dell'Enemy, aumentare la costante 1
				if (enemySprites.get(i).counter >= (125 / (FPS * 1))) {
					enemySprites.get(i).movePose(e.getLastDir());
					enemySprites.get(i).getAnimation().update();
					enemySprites.get(i).counter = 0;
				} else {
					enemySprites.get(i).counter += 1;
				}
			}
		}

//		condizione di sconfitta
		if(((Player)world.getPlayer()).getLifes() == 0)
			game.youLose();
//		aggiornamento vite
		else if(world.getPlayer().hasRespawned()) {
			score.updateHearts();
			if(Options.multiplayer == false)
				world.getPlayer().setRespawned(false);
		}

//		vittoria livello
		if(world.getWinCon()) {
			
			if(Options.difficulty == Difficulty.paradiso)
				Options.difficulty = Difficulty.purgatorio;
			else if(Options.difficulty == Difficulty.purgatorio)
				Options.difficulty = Difficulty.inferno;
			
			if(score.getRemaining_time() > 0)
				score.setTotal_score(score.getTotal_score() + score.getRemaining_time());
			//inserisci qui canzone di vittoria livello
			game.launchGame();
		}
		
//		aggiornamento diamanti
		if(prevDiamonds != world.getMap().getNumDiamonds()) {
			score.setMissing_diamonds(world.getMap().getNumDiamonds());
			score.setTotal_score(score.getTotal_score() + 20);
			prevDiamonds = world.getMap().getNumDiamonds();
		}
	}

//	gestione update per gli eventi e il multiplayer
	
	public synchronized void updatePlayerOnPressing(int dir) {

		if (!world.getPlayer().isDead()) {
			// a static map instead of a switch
			playerSprites.get(0).movePose(dir);
			if(world.getPlayer().update(dir)) {
				((Player)world.getPlayer()).setLastDir(dir);
			}
			playerSprites.get(0).getAnimation().update();
		}
	}

	public synchronized void updatePlayerOnRelease(int dir) {

		if (!world.getPlayer().isDead()) {
			playerSprites.get(0).getAnimation().stop();
			playerSprites.get(0).getAnimation().reset();
			// a static map instead of a switch
			playerSprites.get(0).standPose(dir);
			playerSprites.get(0).getAnimation().update();
			setMouseReleased(true);
		}
	}
	
	public synchronized void updateHostOnPressing(int dir) {

		if (!world.getHost().isDead()) {
			// a static map instead of a switch
			hostSprites.get(0).movePose(dir);
			world.getHost().update(dir);
			hostSprites.get(0).getAnimation().update();
		}
	}

	public synchronized void updateHostOnRelease(int dir) {

		if (!world.getHost().isDead()) {
			hostSprites.get(0).getAnimation().stop();
			hostSprites.get(0).getAnimation().reset();
			// a static map instead of a switch
			hostSprites.get(0).standPose(dir);
			hostSprites.get(0).getAnimation().update();
		}
	}

// 	gestione multithreading
	
	public void launchThread() {
		world.launchThread();
	
		if(t != null && t.isAlive())
			t.interrupt();
		
		t = new Thread(new Runnable() {
			@Override
			public void run() {
				
				int timecounter = 0;
				
				while(!isComplete && !world.isComplete()) {
					try {
						Thread.sleep(5);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				while(true) {
					revalidate();
					repaint();
					world.reset();
					try {
						Thread.sleep(1000/FPS + 1);
					} catch(InterruptedException e) {
						return;
					}
					
					++timecounter;
					if(timecounter == FPS) {
						
						timecounter = 0;
						int rt = score.getRemaining_time()-1;
						score.setRemaining_time(rt >= 0 ? rt : 0);
					}
				}
			}
		});
		
		t.start();
	}
	
	public synchronized void closeThread() {
		if(t != null && t.isAlive())
			t.interrupt();
		world.closeThread();
	}

	// eventi di movimento da tastiera del player...
	/*@Override
	public void keyPressed(KeyEvent e) {
		if ((java.time.LocalTime.now().minusNanos(100000000)).compareTo(lastTimePressed) > 0) {
			lastTimePressed = java.time.LocalTime.now();
			if(pgMove.containsKey(e.getKeyCode()))
				updatePlayerOnPressing(pgMove.get(e.getKeyCode()));
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(pgMove.containsKey(e.getKeyCode()))
			updatePlayerOnRelease(pgMove.get(e.getKeyCode()));
	}
	
	@Override public void keyTyped(KeyEvent e) {}*/

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);

		try {
			updateGraphics();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		Renderer.render(g, this);
	}
	
	public boolean isComplete() 						{ return isComplete; }
	
	public void setScore(Score score) 					{ this.score = score; }

	public ArrayList<BlockSprite> getBlockSprites() 	{ return blockSprites; }

	public World getWorld() 							{ return world; }
	public void setWorld(World world) 					{ this.world = world; }
		
	public boolean isMouseReleased() 					{ return mouseReleased; }
	public void setMouseReleased(boolean mouseReleased) { this.mouseReleased = mouseReleased; }
}
