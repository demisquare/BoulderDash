package model;

import static model.GameObject.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

//definisce la mappa di gioco come matrice di blocchi
public class GameMap {
	
	private static String defaultPath = "." + File.separator + "resources" + File.separator + "maps" + File.separator;
	
	//private ArrayList< HashMap<Integer, GameObject> > hashmap;
	private HashMap<Integer, GameObject> blocks;
	private ArrayList<GameObject> player;
	private ArrayList<GameObject> enemy;
	
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
					
					blocks = new HashMap<Integer, GameObject>(dimX*dimY);
					
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
				
			} catch(Exception e) {
				e.printStackTrace();
			}
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
						blocks.put(x*dimX+y, new Diamond(x, y));
						++numDiamonds;
						break;
					
					case GROUND:
						blocks.put(x*dimX+y, new Ground(x, y));
						break;
					
					case WALL:
						blocks.put(x*dimX+y, new Wall(x, y));
						break;
					
					case ROCK:
						blocks.put(x*dimX+y, new Rock(x, y));
						break;
						
					case DOOR:
						blocks.put(x*dimX+y, new Door(x, y));
						break;
						
					case EMPTY_BLOCK:
						blocks.put(x*dimX+y, new EmptyBlock(x, y));
						break;
						
					case PLAYER:
						player.add(new Player(x, y, 1));
						break;
					
					case ENEMY:
						enemy.add(new Enemy(x, y, 1));
						break;	
				}
			}
		}
	}

	public GameMap(String filename) {
		if(GameObject.map == null)  GameObject.map = this;

		player = new ArrayList<GameObject>();
		enemy  = new ArrayList<GameObject>();
		
		initialize(filename);
	}

	public int getDimX() {

		return dimX;
	}

	public int getDimY() {

		return dimY;
	}

	public HashMap<Integer, GameObject> getBlocks() {
		return blocks;
	}
	
	public GameObject getTile(int x, int y) {

		return blocks.get(x*dimX+y);
	}

	public void setTile(int x, int y, GameObject value) {

		blocks.put(x*dimX+y, value);
	}
	
	public GameObject getPlayer() {
		
		return player.get(0);
	}
	
	public ArrayList<GameObject> getEnemies() {
		
		return enemy;
	}
}