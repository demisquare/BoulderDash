package logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

//definisce la mappa di gioco come matrice di blocchi
public class Map {

	private static String defaultPath = "." + File.separator + "resources" + File.separator + "maps" + File.separator;
	private Block[][] map; // matrice per migliorare le prestazioni

	// dimensione logica...
	int dimX;
	int dimY;

	// numero diamanti sulla mappa (per la condizione di vittoria)
	int numDiamonds;

	// carica mappa da file di testo e la salva in una mappa di string
	private char[][] load(String filename) {

		// tentativo di inizializzare il fileBuffer
		BufferedReader bIn = null;
		try {
			bIn = new BufferedReader(new FileReader(defaultPath + filename));
		} catch (FileNotFoundException e) { // trovare una gestione migliore
			e.printStackTrace();
		}

		char[][] level = null;

		// se e' inizializzato
		if (bIn != null) {
			try {
				String line;

				// si leggono le dimensioni del livello
				if (bIn.ready()) {
					dimX = Integer.parseInt(bIn.readLine());
					dimY = Integer.parseInt(bIn.readLine());

					System.out.println(dimX);
					System.out.println(dimY);

					// si inizializza la matrice
					level = new char[dimX][dimY];

					while (bIn.ready())
						for (int i = 0; i < dimX; i++) {
							// si legge la casella
							line = bIn.readLine();
							for (int j = 0; j < dimY; j++)
								level[i][j] = line.charAt(j);

						}
					bIn.close();

				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}

		return level;

	}

	private void initialize(String filename) {

		numDiamonds = 0;

		char level[][] = load(filename);
		
		/*for(int i = 0; i < dimX; i++) {
			for(int j = 0; j < dimY; j++)
				System.out.print(level[i][j]);
			System.out.print("\n");
		}*/

		map = new Block[dimX][dimY];

		for (int x = 0; x < dimX; x++)
			for (int y = 0; y < dimY; y++) {
				//System.out.println(x + " - " + y);
				switch (level[x][y]) {
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
				case Block.EMPTY_BLOCK:
					map[x][y] = Destructible.emptyTile;
					break;
				}
			}
				
	}

	public Map(String filename) {
		if (Block.map == null)
			Block.map = this;
		if (Living.map == null)
			Living.map = this;

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