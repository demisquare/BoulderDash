//AUTORE: Davide Caligiuri
package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import ai.IntelligentEnemy;

import menu.Options;

//	definisce la mappa di gioco come matrice di blocchi
public class GameMap {
	
	private static String defaultPath = 
			"." + File.separator + 
			"resources" + File.separator + 
			"maps" + File.separator;

// 	mappe separate per ogni tipologia di blocco:
// 	contiene Player, Enemy, Wall, Door
	private ConcurrentHashMap<Integer, GameObject> blocks;
// 	contiene EmptyBlock
	private ConcurrentHashMap<Integer, GameObject> emptyBlocksMap;
// 	contiene Rock
	private ConcurrentHashMap<Integer, GameObject> rocksMap;
// 	contiene Diamond
	private ConcurrentHashMap<Integer, GameObject> diamondsMap;
// 	contiene Ground
	private ConcurrentHashMap<Integer, GameObject> groundMap;
// 	shortcut per i vari oggetti Player
	private GameObject player;
// 	shortcut per i vari oggetti Host
	private GameObject host;
// 	shortcut per i vari oggetti Enemy
	private ArrayList<GameObject> enemy;
//	true se viene vinta la partita
	private boolean winCon;

//	dimensione logica...
	final static int dimX = 40;
	final static int dimY = 22;

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
	
	private char[][] load(String filename) {

		BufferedReader bIn = null;
		try {
			bIn = new BufferedReader(new FileReader(defaultPath + filename));
		} catch (FileNotFoundException e) { // trovare una gestione migliore
			GameObject.map = null;
			e.printStackTrace();
		}

		char[][] ret = new char[dimY][dimX];

		if (bIn != null) {
			try {
				String line;
				// si leggono le dimensioni del livello
				if (bIn.ready()) {

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
			return null;
		}

		return ret;
	}

	private void initialize(String filename) {

		char[][] cfile = load(filename);

		for (int x = 0; x < dimX; ++x) {
			for (int y = 0; y < dimY; ++y) {
				switch (cfile[y][x]) {

				case GameObject.DIAMOND:
					diamondsMap.put(x * dimX + y, new Diamond(x, y));
					break;

				case GameObject.GROUND:
					groundMap.put(x * dimX + y, new Ground(x, y));
					break;

				case GameObject.WALL:
					blocks.put(x * dimX + y, new Wall(x, y));
					break;

				case GameObject.ROCK:
					rocksMap.put(x * dimX + y, new Rock(x, y));
					break;

				case GameObject.DOOR:
					blocks.put(x * dimX + y, new Door(x, y));
					break;

				case GameObject.PLAYER:
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

				case GameObject.HOST:
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

				case GameObject.ENEMY:
					GameObject e = new IntelligentEnemy(x, y, 1);
					enemy.add(e);
					blocks.put(x * dimX + y, e);
					break;

				case GameObject.EMPTY_BLOCK:
					emptyBlocksMap.put(x * dimX + y, new EmptyBlock(x, y));
					break;
				}
			}
		}
	}

	// verifica se il valore i � contenuto nella matrice (conviene sostituirlo con
	// due param?)
	public boolean containsKey(int i) {

		return blocks.containsKey(i) || emptyBlocksMap.containsKey(i) || diamondsMap.containsKey(i)
				|| groundMap.containsKey(i) || rocksMap.containsKey(i);
	}

//getter e setter
	
//	restituisce il tile nella posizione <x, y>
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

//	aggiunge il GameObject in posizione <x, y> e si assiura che nessun altro
// 	oggetto abbia le stesse coordinate
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
	
	public int getNumDiamonds() 				{ return diamondsMap.size(); }
	
	public boolean getWinCon() 					{ return winCon; }
	public void setWinCon(boolean t) 			{ winCon = t; }
	
	public int getDimX() 						{ return dimX; }
	public int getDimY() 						{ return dimY; }

	public ConcurrentHashMap<Integer, GameObject> getBlocks() 			{ return blocks; }
	public ConcurrentHashMap<Integer, GameObject> getEmptyBlocksMap() 	{ return emptyBlocksMap; }
	public ConcurrentHashMap<Integer, GameObject> getRocksMap() 		{ return rocksMap; }
	public ConcurrentHashMap<Integer, GameObject> getDiamondsMap() 		{ return diamondsMap; }
	public ConcurrentHashMap<Integer, GameObject> getGroundMap() 		{ return groundMap; }

	public GameObject getPlayer() 				{ return player; }
	public GameObject getHost() 				{ return host; }

	public ArrayList<GameObject> getEnemies() 	{ return enemy; }
}