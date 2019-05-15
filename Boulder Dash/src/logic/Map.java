package logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

//definisce la mappa di gioco come matrice di blocchi
public class Map {

	public static int X = 0;
	public static int Y = 1;
	
    private Block[][] map; //matrice per migliorare le prestazioni
    int dimX;
    int dimY;

    private void initialize(String filename) {
        
    	//tentativo di inizializzare il fileBuffer
        BufferedReader bIn = null;
		try {
			bIn = new BufferedReader(new FileReader("." + File.separator + "resources" + File.separator + filename));
		} catch (FileNotFoundException e) { //trovare una gestione migliore
			e.printStackTrace();
		}
		
		//se è inizializzato
		if(bIn != null) {
			try {
				int x;
				
				//si leggono le dimensioni del livello
				if(bIn.ready()) {
					dimX = bIn.read();
					dimY = bIn.read();

					//si inizializza la matrice
					map = new Block[dimX][dimY];
					
					int pos = 0;
					while(bIn.ready() && pos < dimX*dimY) {
						//si legge la casella
						x = bIn.read();
						
						//gestione dei vari casi
						if(x == Block.DIAMOND)
							map[pos/dimY][pos%dimY] = new Diamond(pos/dimY, pos%dimY);
						else if(x == Block.GROUND)
							map[pos/dimY][pos%dimY] = new Ground(pos/dimY, pos%dimY);
						else if(x == Block.ROCK)
							map[pos/dimY][pos%dimY] = new Rock(pos/dimY, pos%dimY);
						else if(x == Block.EMPTY_BLOCK)
							map[pos/dimY][pos%dimY] = Destructible.emptyTile;
						
						++pos;
					}
				}
			
				bIn.close();
				
			} catch (IOException e) { //trovare una gestione migliore
				e.printStackTrace();
			}
		}
    }

    public Map(String filename){
    	Block.map = this;
    	Living.map = this;

    	dimX = 800;
    	dimY = 600;
    	
        initialize(filename);
    }

    public Block getTile(int x, int y) {
    	
    	return map[x][y];
    }
    
    public void setTile(int x, int y, Block value) {
    	
    	map[x][y] = value;
    }
}