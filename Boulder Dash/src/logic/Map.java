package logic;

import java.util.ArrayList;

//definisce la mappa di gioco come matrice di blocchi
public class Map {

    private ArrayList<ArrayList<Block> > maps;

    private void initialize(String filename){
        
        //TODO: legge da file la mappa
    }

    public Map(String filename){

        initialize(filename);
    }

}