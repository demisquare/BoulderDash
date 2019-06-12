package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;
import java.util.function.Supplier;

import org.jgrapht.*;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.util.SupplierUtil;

import view.Sprite;

//classe che contiene sia il player che la mappa
public class World implements Runnable {

	GameMap map;	
	Stack<Integer> stack = new Stack<Integer>();
	
	//true se il world è stato aggiornato
	boolean hasChanged;

	//dimensione grafica...
	int width  = 0;	
	int height = 0;
	//	Graph<Integer, Integer> g= new SimpleGraph(int.class);
	
	// costruttore di default
	public World() {

		//sostituire con il filename
		map = new GameMap("levelmap");
		
		//default a true (per evitare un aggiornamento immediato)
		hasChanged = true;
		
		//dimensione grafica...
		width = map.getDimX() * Sprite.TILE_SIZE;
		height = map.getDimY() * Sprite.TILE_SIZE;
		
		//da definire la posizione di partenza (e il criterio di scelta)
		stack.add((2*map.getDimX())+10);
		
		Thread t = new Thread(this);
		t.start();
	}
	
	// getter e setter
	
	public GameObject getPlayer() {
		return map.getPlayer();
	}

	public GameMap getMap() {
		return map;
	}

	public void setMap(GameMap map) {
		this.map = map;
	}
	
	public ArrayList<GameObject> getEnemies() {
		return map.getEnemies();
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public boolean isHasChanged() {
		return hasChanged;
	}
	
	public void reset() {

		Collection<GameObject> temp = map.getBlocks().values();
		for(GameObject e : temp) {
			e.processed = false;
		}
		map.getPlayer().processed = false;
		for(GameObject e : map.getEnemies()) {
			e.processed = false;
		}
		
		hasChanged = false;
	}

	//questa funzione dovrebbe in automatico aggiornare gli stati di 
	//Player, Enemy, etc, ogni tick del timer
	public void update() {
		
		//aggiorna gli stati di ogni casella
		Collection<GameObject> temp = map.getBlocks().values();
		for(GameObject e : temp) {
			try {
				if(e.processed == false && e.update())
					hasChanged = true;
					
			} catch(NullPointerException e1) {
				System.out.println(e.getX() + " " + e.getY());
				e1.printStackTrace();
				
			}	
		}
		
		//aggiorna gli stati di ogni nemico
		for(int i = 0; i < getEnemies().size(); ++i)
			if(!getEnemies().get(i).dead && getEnemies().get(i).move(GameObject.DOWN))
				hasChanged = true;
		
		/* scommentare nel caso di dijkstra
		{
			int dir=stack.pop();
			int x=dir/map.dimX;
			int y=dir%map.dimY;
			if(enemies.get(i).y==y+1)
				enemies.get(i).move(Living.UP);
			else if(enemies.get(i).y==y-1)
				enemies.get(i).move(Living.DOWN);
			else if(enemies.get(i).x==x-1)
				enemies.get(i).move(Living.LEFT);
			else 
				enemies.get(i).move(Living.RIGHT);
		}
		*/
		//flag di vittoria qui?

	}
	
	
	public void dijkstra() {
		
		int s= (1*map.getDimY())+10;  //omdat die de coï¿½rdinaten zijn (perchï¿½ queste sono le coordinate nell'unico nemico che c'ï¿½)
		int d= (15*map.getDimY())+15; //coï¿½rdinaten van de speler (coordinate giocatore)
		
		HashMap<Integer, ArrayList<Integer>> graph= new HashMap<Integer, ArrayList<Integer>>();
		for(int i=0; i<map.getDimX(); i++)
			for(int j=0; j<map.getDimY(); j++)
			{
				if( map.getTile(i, j) instanceof EmptyBlock) {
					ArrayList<Integer> a=new ArrayList<Integer>();
					
					if(i-1>=0 && map.getTile(i-1, j) instanceof EmptyBlock)
						a.add( ((i-1)*map.getDimY())+j );
					if(i+1<map.getDimY() && map.getTile(i+1, j) instanceof EmptyBlock)
						a.add( ((i+1)*map.getDimY())+j );
					if(j-1>=0 &&  map.getTile(i, j-1) instanceof EmptyBlock)
						a.add( (i*map.getDimY())+j-1 );
					if(j+1<map.getDimX() &&  map.getTile(i, j+1) instanceof EmptyBlock)
						a.add( (i*map.getDimY())+j+1 );
					
					graph.put(i*map.getDimY()+j, a);
				}
					
			}
		
		HashSet<Integer> w= new HashSet<Integer>(); // nodi etichettati permanentemente
		
		int p[] = new int[map.getDimX()*map.getDimY()]; //pesi
		
		for(int i=0; i<p.length; i++) {
			p[i]=map.getDimX()*map.getDimY()+1;
		}
		p[s]=0;
		
		int prec[] = new int[map.getDimX()*map.getDimY()]; //predecessore
		for(int i=0; i<prec.length; i++) {
			prec[i]=s;
		}
		
		while(!w.contains(d)) {
			//tra i nodi non etichettati prendi quello con il peso piï¿½ basso
			int pesomin=map.getDimX()*map.getDimY();
			int wheremin=-1;
			for(int i=0; i<p.length; i++) {
				if( p[i]<pesomin && !w.contains(i)) {
					pesomin=p[i];
					wheremin=i;
				}
			}
			
			//lo si aggiunge a w
			w.add(wheremin);
			
			ArrayList<Integer> current = graph.get(wheremin);
			
			for(int i=0; i<current.size(); i++) {
				if( !w.contains(current.get(i)) && pesomin+1 < p[current.get(i)]) {
					p[current.get(i)]=pesomin+1;
					prec[current.get(i)]=wheremin;
				}
			}
				
		}
		
		int current=d;
		while (current!=s) {
			stack.add(prec[d]);
			current=prec[d];
		}
		//funz per far muovere il nemico
		//da fare
		
	}

	@Override
	public void run() {
		while(true) {
			
			update();
			
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
