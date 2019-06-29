package model;

import static model.GameObject.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import ai.IntelligentEnemy;
import menu.Options;
import menu.Options.Difficulty;

//definisce la mappa di gioco come matrice di blocchi
public class GameMap {

	private static String defaultPath = "." + File.separator + "resources" + File.separator + "maps" + File.separator;

	// mappe separate per ogni tipologia di blocco:
	// contiene Player, Enemy, Wall, Door
	private ConcurrentHashMap<Integer, GameObject> blocks;
	// contiene EmptyBlock
	private ConcurrentHashMap<Integer, GameObject> emptyBlocksMap;
	// contiene Rock
	private ConcurrentHashMap<Integer, GameObject> rocksMap;
	// contiene Diamond
	private ConcurrentHashMap<Integer, GameObject> diamondsMap;
	// contiene Ground
	private ConcurrentHashMap<Integer, GameObject> groundMap;
	// shortcut per i vari oggetti Player
	private GameObject player;
	// shortcut per i vari oggetti Host
	private GameObject host;
	// shortcut per i vari oggetti Enemy
	private ArrayList<GameObject> enemy;

	boolean winCon;
	// dimensione logica...
	int dimX;
	int dimY;

	// numero diamanti sulla mappa (per la condizione di vittoria)
	int numDiamonds;

	private char[][] load(String filename) {

		BufferedReader bIn = null;
		try {
			bIn = new BufferedReader(new FileReader(defaultPath + filename));
		} catch (FileNotFoundException e) { // trovare una gestione migliore
			map = null;
			e.printStackTrace();
		}

		char[][] ret = null;

		if (bIn != null) {
			try {
				String line;

				// si leggono le dimensioni del livello
				if (bIn.ready()) {
					dimX = Integer.parseInt(bIn.readLine());
					dimY = Integer.parseInt(bIn.readLine());

					ret = new char[dimY][dimX];

					System.out.println(dimX);
					System.out.println(dimY);

					while (bIn.ready()) {
						for (int x = 0; x < dimY; ++x) {
							line = bIn.readLine();
							for (int y = 0; y < dimX; ++y) {
								ret[x][y] = line.charAt(y);
							}
						}
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		try {
			bIn.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return ret;
	}

	private void initialize(String filename) {

		numDiamonds = 0;

		char[][] cfile = load(filename);

		for (int x = 0; x < dimX; ++x) {
			for (int y = 0; y < dimY; ++y) {
				switch (cfile[y][x]) {

				case DIAMOND:
					diamondsMap.put(x * dimX + y, new Diamond(x, y));
					++numDiamonds;
					break;

				case GROUND:
					groundMap.put(x * dimX + y, new Ground(x, y));
					break;

				case WALL:
					blocks.put(x * dimX + y, new Wall(x, y));
					break;

				case ROCK:
					rocksMap.put(x * dimX + y, new Rock(x, y));
					break;

				case DOOR:
					blocks.put(x * dimX + y, new Door(x, y));
					break;

				case PLAYER:
					if(Options.multiplayer) {
						if(Options.host) {
							host = new Host(x, y, 1);
							blocks.put(x * dimX + y, host);
						} else {
							player = new Player(x, y, 1);
							blocks.put(x * dimX + y, player);
						}
					} else {
						player = new Player(x, y, 1);
						blocks.put(x * dimX + y, player);
					}

					break;

				case HOST:
					if(Options.multiplayer) {
						if(Options.host) {
							player = new Player(x, y, 1);
							blocks.put(x * dimX + y, player);
						} else {
							host = new Host(x, y, 1);
							blocks.put(x * dimX + y, host);
						}
					} else {
						emptyBlocksMap.put(x * dimX + y, new EmptyBlock(x, y));
					}
					break;

				case ENEMY:
					GameObject e = new IntelligentEnemy(x, y, 1);
					enemy.add(e);
					blocks.put(x * dimX + y, e);
					break;

				case EMPTY_BLOCK:
					emptyBlocksMap.put(x * dimX + y, new EmptyBlock(x, y));
					break;
				}
			}
		}
	}

	public GameMap(String filename/* , Mode m */) {
		GameObject.map = this;

		winCon = false;
		
		blocks = new ConcurrentHashMap<Integer, GameObject>(dimX * dimY, 1);
		emptyBlocksMap = new ConcurrentHashMap<Integer, GameObject>(dimX * dimY, 1);
		diamondsMap = new ConcurrentHashMap<Integer, GameObject>(dimX * dimY, 1);
		rocksMap = new ConcurrentHashMap<Integer, GameObject>(dimX * dimY, 1);
		groundMap = new ConcurrentHashMap<Integer, GameObject>(dimX * dimY, 1);

		enemy = new ArrayList<GameObject>();

		initialize(filename);
	}

	// restituisce il tile nella posizione <x, y>
	public GameObject getTile(int x, int y) {

		GameObject ret = blocks.get(x * dimX + y);

		if (ret == null)
			ret = emptyBlocksMap.get(x * dimX + y);
		if (ret == null)
			ret = rocksMap.get(x * dimX + y);
		if (ret == null)
			ret = diamondsMap.get(x * dimX + y);
		if (ret == null)
			ret = groundMap.get(x * dimX + y);

		return ret;
	}

	// aggiunge il GameObject in posizione <x, y> e si assiura che nessun altro
	// oggetto abbia le stesse coordinate
	public GameObject setTile(int x, int y, GameObject value) {

		GameObject ret = getTile(x, y);

		if (value instanceof EmptyBlock)
			emptyBlocksMap.put(x * dimX + y, value);
		else if (value instanceof Ground)
			groundMap.put(x * dimX + y, value);
		else if (value instanceof Diamond)
			diamondsMap.put(x * dimX + y, value);
		else if (value instanceof Rock)
			rocksMap.put(x * dimX + y, value);
		else
			blocks.put(x * dimX + y, value);

		if (ret instanceof EmptyBlock)
			emptyBlocksMap.remove(x * dimX + y, ret);
		else if (ret instanceof Ground)
			groundMap.remove(x * dimX + y, ret);
		else if (ret instanceof Diamond)
			diamondsMap.remove(x * dimX + y, ret);
		else if (ret instanceof Rock)
			rocksMap.remove(x * dimX + y, ret);
		else
			blocks.remove(x * dimX + y, ret);

		return ret;
	}
	
	public int getDimX() {
		return dimX;
	}

	public int getDimY() {
		return dimY;
	}

	public ConcurrentHashMap<Integer, GameObject> getBlocks() {
		return blocks;
	}

	public ConcurrentHashMap<Integer, GameObject> getEmptyBlocksMap() {
		return emptyBlocksMap;
	}

	public ConcurrentHashMap<Integer, GameObject> getRocksMap() {
		return rocksMap;
	}

	public ConcurrentHashMap<Integer, GameObject> getDiamondsMap() {
		return diamondsMap;
	}

	public ConcurrentHashMap<Integer, GameObject> getGroundMap() {
		return groundMap;
	}

	public GameObject getPlayer() {

		return player;
	}

	public GameObject getHost() {

		return host;
	}

	public ArrayList<GameObject> getEnemies() {

		return enemy;
	}

	// verifica se il valore i � contenuto nella matrice (conviene sostituirlo con
	// due param?)
	public boolean containsKey(int i) {

		return blocks.containsKey(i) || emptyBlocksMap.containsKey(i) || diamondsMap.containsKey(i)
				|| groundMap.containsKey(i) || rocksMap.containsKey(i);
	}
}