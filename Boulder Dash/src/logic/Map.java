package logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

//definisce la mappa di gioco come matrice di blocchi
public class Map {

	private static String defaultPath = "." + File.separator + "resources" + File.separator + "maps" + File.separator;
	private Block[][] map; // matrice per migliorare le prestazioni
	//
	private ArrayList< HashMap<Integer, Block> > hashmap;
	
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
			dimX = -1;
			dimY = -1;
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
						for (int x = 0; x < dimY; x++) {
							line = bIn.readLine();
							for (int y = 0; y < dimX; y++) {
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
		
		map = new Block[dimX][dimY];
		
		for (int x = 0; x < dimX; x++) {
			for (int y = 0; y < dimY; y++) {
				switch (cfile[y][x]) {
					
					case Block.DIAMOND:
						map[x][y] = new Diamond(x, y);
						++numDiamonds;
						break;
					
					case Block.GROUND:
						map[x][y] = new Ground(x, y);
						break;
					
					case Block.WALL:
						map[x][y] = new Wall(x, y);
						break;
					
					case Block.ROCK:
						map[x][y] = new Rock(x, y);
						break;
						
					case Block.DOOR:
						map[x][y] = new Door(x, y);
						break;
					
					case Block.EMPTY_BLOCK:
						map[x][y] = Destructible.emptyTile;
						break;
				}
			}
		}
	}

	public Map(String filename) {
		if(Block.map == null)  Block.map = this;
		if(Living.map == null) Living.map = this;

		initialize(filename);
	}

	public int getDimX() {

		return dimX;
	}

	public int getDimY() {

		return dimY;
	}

	public Block getTile(int x, int y) {

		return map[x][y];
	}

	public void setTile(int x, int y, Block value) {

		map[x][y] = value;
	}
}