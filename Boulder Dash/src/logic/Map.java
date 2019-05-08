package logic;

//definisce la mappa di gioco come matrice di blocchi
public class Map {

    private Block map[][]; //matrice per migliorare le prestazioni

    private void initialize(String filename){
        
        //TODO: legge da file la mappa
    }

    public Map(String filename, int x, int y){

        map = new Block[x][y]; //dimensioni?
        Block.map = this;
        initialize(filename);
    }

    public Block get(int x, int y) {
    	
    	return map[x][y];
    }
    
    public void set(int x, int y, Block value) {
    	
    	map[x][y] = value;
    }
}