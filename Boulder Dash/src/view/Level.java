package view;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import menu.Options;
import model.*;
import network.*;
import network.packet.*;

public class Level extends JPanel implements KeyListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9009048960794622320L;
	// mappa che collega ogni pressione di tastiera al movimento corrispondente
	// nello specifico: enumeratore Awt di pressione tasto , enumeratore logico di
	// direzione
	private static final HashMap<Integer, Integer> pgMove = new HashMap<Integer, Integer>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = -6595629016610529055L;

		{
			put(KeyEvent.VK_LEFT, GameObject.LEFT);
			put(KeyEvent.VK_RIGHT, GameObject.RIGHT);
			put(KeyEvent.VK_UP, GameObject.UP);
			put(KeyEvent.VK_DOWN, GameObject.DOWN);
		}
	};

	private static final HashMap<String, Integer[]> blocks = new HashMap<String, Integer[]>() {
		/**
		 * 
		 */
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

	private int FPS = 34;
	private static Sprite spritesheet = new Sprite();
	private static Random r = new Random();

	/*
	 * AudioPlayer game_song = new AudioPlayer("." + File.separator + "resources" +
	 * File.separator + "assets" + File.separator + "music" + File.separator +
	 * "game_song"+ ".wav");
	 */

	private Thread t;

	// Questa classe far� da interfaccia a TUTTA la logica di un livello
	World world;
	LocalTime lastTimePressed;

	// graphics for blocks
	ArrayList<BlockSprite> blockSprites;
	// graphics for both Player and Enemies
	ArrayList<LivingSprite> playerSprites;
	ArrayList<LivingSprite> hostSprites;
	ArrayList<LivingSprite> enemySprites;

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
			Enemy e = (Enemy) world.getEnemies().get(i);
			enemySprites.add(new LivingSprite("enemySpriteSheet", e.getSpeed(), e));
		}
	}

	public Level() {
		super();

		setFocusable(true);
		setVisible(true);
		setEnabled(true);

		// crea un world...
		world = new World(FPS);

		t = null;
		
		initGraphics();

		lastTimePressed = java.time.LocalTime.now();
		Renderer.init(world);
	}

	public ArrayList<BlockSprite> getBlockSprites() {
		return blockSprites;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	// sincronizza la grafica con la logica
	public synchronized void updateGraphics() throws NullPointerException {
		// aggiorna i blocchi
		for (int i = 0; i < blockSprites.size(); ++i) {
			
			GameObject g = blockSprites.get(i).getLogicObject();
			// se il blocco logico e' cambiato nell'ultimo world.update()...
			if (g.isDead() || g.hasChanged()) {
				if (g.getSuccessor() != null) {
					GameObject newObj = g.getSuccessor();
					BufferedImage img = spritesheet.getSprite(1, 2);
					blockSprites.set(i, new BlockSprite(img, newObj));
				}
			}
		}
		// aggiorna i players
		for (int i = 0; i < playerSprites.size(); ++i) {
			Player p = (Player) playerSprites.get(i).logicObj;
			if (p.isDead()) {
				GameObject newObj = p.getSuccessor();
				BufferedImage img = spritesheet.getSprite(1, 2);
				blockSprites.add(new BlockSprite(img, newObj));
				playerSprites.remove(i);
				System.out.println("PLAYER REMOVED");
			}
		}
		
		if(Options.multiplayer && Options.host) {
			// aggiorna gli hosts
			for (int i = 0; i < hostSprites.size(); ++i) {
				Host h = (Host) hostSprites.get(i).logicObj;
				if (h.isDead()) {
					GameObject newObj = h.getSuccessor();
					BufferedImage img = spritesheet.getSprite(1, 2);
					blockSprites.add(new BlockSprite(img, newObj));
					hostSprites.remove(i);
					System.out.println("HOST REMOVED");
				}
			}
		}

		// aggiorna gli enemies
		for (int i = 0; i < enemySprites.size(); ++i) {
			Enemy e = (Enemy) enemySprites.get(i).logicObj;
			if (e.isDead()) {
				GameObject newObj = e.getSuccessor();
				BufferedImage img = spritesheet.getSprite(1, 2);
				blockSprites.add(new BlockSprite(img, newObj));
				enemySprites.remove(i);
				System.out.println("ENEMY REMOVED");
			}
		}
		// aggiorna gli enemies pt.2
		for (int i = 0; i < enemySprites.size(); ++i) {
			Enemy e = (Enemy) enemySprites.get(i).logicObj;
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
	}

	// permette di ridefinire i componenti del pannello di default.
	// questo metodo viene invocato ogni volta che usiamo il metodo repaint().
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

	public void updatePlayerOnPressing(int dir) {

		if (!world.getPlayer().isDead()) {
			if (pgMove.containsKey(dir)) {
				// a static map instead of a switch
				playerSprites.get(0).movePose(pgMove.get(dir));
				synchronized (this) {
					world.getPlayer().update(pgMove.get(dir));
				}
			}
			playerSprites.get(0).getAnimation().update();
		}
	}

	public void updatePlayerOnRelease(int dir) {

		if (!world.getPlayer().isDead()) {
			playerSprites.get(0).getAnimation().stop();
			playerSprites.get(0).getAnimation().reset();
			// a static map instead of a switch
			if (pgMove.containsKey(dir))
				playerSprites.get(0).standPose(pgMove.get(dir));
			playerSprites.get(0).getAnimation().update();
		}
	}
	
	public void updateHostOnPressing(int dir) {

		if (!world.getHost().isDead()) {
			if (pgMove.containsKey(dir)) {
				// a static map instead of a switch
				hostSprites.get(0).movePose(pgMove.get(dir));
				synchronized (this) {
					//world.getPlayer().update(pgMove.get(dir));
				}
			}
			hostSprites.get(0).getAnimation().update();
		}
	}

	public void updateHostOnRelease(int dir) {

		if (!world.getHost().isDead()) {
			hostSprites.get(0).getAnimation().stop();
			hostSprites.get(0).getAnimation().reset();
			// a static map instead of a switch
			if (pgMove.containsKey(dir))
				hostSprites.get(0).standPose(pgMove.get(dir));
			hostSprites.get(0).getAnimation().update();
		}
	}

	// eventi di movimento da tastiera del player...
	@Override public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {

		if ((java.time.LocalTime.now().minusNanos(100000000)).compareTo(lastTimePressed) > 0) {
			lastTimePressed = java.time.LocalTime.now();
			updatePlayerOnPressing(e.getKeyCode());
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

		if ((java.time.LocalTime.now().minusNanos(100000000)).compareTo(lastTimePressed) > 0) {
			lastTimePressed = java.time.LocalTime.now();
			updatePlayerOnRelease(e.getKeyCode());
		}
	}

	public void launchThread() {
		world.launchThread();
	
		if(t != null && t.isAlive())
			t.interrupt();
		
		t = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					synchronized(this) {
						revalidate();
						repaint();
					}
					synchronized(this) {
						world.reset();
					}
					try {
						Thread.sleep(34);
					} catch(InterruptedException e) {
						return;
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
}
