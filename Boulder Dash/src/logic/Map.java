package logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

//definisce la mappa di gioco come matrice di blocchi
public class Map {

	
	private static String defaultPath = "." + File.separator + "resources" + File.separator + "maps" + File.separator;
    private Block[][] map; //matrice per migliorare le prestazioni
    
    //dimensione logica...
    int dimX;
	int dimY;

	//numero diamanti sulla mappa (per la condizione di vittoria)
	int numDiamonds;
	
    private void initialize(String filename) {
        
    	numDiamonds = 0;
    	//tentativo di inizializzare il fileBuffer
        BufferedReader bIn = null;
		try {
			bIn = new BufferedReader(new FileReader(defaultPath + filename));
		} catch (FileNotFoundException e) { //trovare una gestione migliore
			e.printStackTrace();
		}
		
		//se e' inizializzato
		if(bIn != null) {
			try {
				String line;
				
				//si leggono le dimensioni del livello
				if(bIn.ready()) {
					dimX = Integer.parseInt(bIn.readLine());
					dimY = Integer.parseInt(bIn.readLine());

					//si inizializza la matrice
					map = new Block[dimX][dimY];
					
					int pos = 0;
					while(bIn.ready() && pos < dimX*dimY) {
						//si legge la casella
						line = bIn.readLine();
						
						//gestione dei vari casi
						for(int i = 0; i < line.length(); ++i) {
							
							switch(line.charAt(i))
							{
								case Block.DIAMOND:
									map[pos/dimY][pos%dimY] = new Diamond(pos/dimY, pos%dimY);
									++numDiamonds;
									break;
								case Block.GROUND:
									map[pos/dimY][pos%dimY] = new Ground(pos/dimY, pos%dimY);
									break;
								case Block.WALL:
									map[pos/dimY][pos%dimY] = new Wall(pos/dimY, pos%dimY);
									break;
								case Block.ROCK:
									map[pos/dimY][pos%dimY] = new Rock(pos/dimY, pos%dimY);
									break;
								case Block.EMPTY_BLOCK:
									map[pos/dimY][pos%dimY] = Destructible.emptyTile;
									break;
							}
							
							/*if(line.charAt(i) == Block.DIAMOND)
								map[pos/dimY][pos%dimY] = new Diamond(pos/dimY, pos%dimY);
							else if(line.charAt(i) == Block.GROUND)
								map[pos/dimY][pos%dimY] = new Ground(pos/dimY, pos%dimY);
							else if(line.charAt(i) == Block.ROCK)
								map[pos/dimY][pos%dimY] = new Rock(pos/dimY, pos%dimY);
							else if(line.charAt(i) == Block.EMPTY_BLOCK)
								map[pos/dimY][pos%dimY] = Destructible.emptyTile;*/
						
							++pos;
						}
					}
					System.out.println(pos);
				}
			
				bIn.close();
				
			} catch (IOException e) { //trovare una gestione migliore
				e.printStackTrace();
			}
			
			System.out.println(dimX);
			System.out.println(dimY);
		}
    }

    public Map(String filename){
    	if(Block.map == null)	Block.map = this;
    	if(Living.map == null)	Living.map = this;
    	
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