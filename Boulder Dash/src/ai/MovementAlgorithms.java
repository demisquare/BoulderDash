package ai;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import model.EmptyBlock;
import model.GameObject;

class MovementAlgorithms {
	
//	la classe è completamente statica
	private MovementAlgorithms() {}

//	alcuni classi, campi e metodi d'utilità
//	per semplificare la gestione degli algoritmi
	
	private static class Triple<T> {
		public T first;
		public T second;
		public T third;
		
		public Triple(T first, T second, T third) {
			this.first = first;
			this.second = second;
			this.third = third;
		}
		
		@Override
		public boolean equals(Object obj) {
			
			Triple<T> temp = null;
			try {
				temp = this.getClass().cast(obj);
			} catch(ClassCastException e) {
				e.printStackTrace();
				return false;
			}
			return 
				first  == temp.first &&
				second == temp.second &&
				third  == temp.third;
		}
	}
	
	private static int dim = 0;
	
//	converte una coppia di coordinate nel corrispondente scalare
//	la conversione è univoca e invertibile, fissato dim
	private static int toScalar(int a, int b) 	{ return a * dim + b; }
	
//	converte uno scalare nella prima o nella seconda coordinata corrispondente
//	la conversione è univoca e invertibile, fissato dim
	private static int toX(int a) 				{ return a / dim; }
	private static int toY(int a) 				{ return a % dim; }
	
//	calcola la distanza con una p-norma a scelta dell'utente
//	(p = 2: norma euclidea -> formula standard della distanza, ovvero teorema di Pitagora)
//	(p = 1: distanza di Manhattan -> movimento lungo un reticolo, ovvero geometria del taxi)
	private static double norm2D(double dx, double dy, double p) {
		return 
			Math.pow(Math.pow(Math.abs(dx), p) + Math.pow(Math.abs(dy), p), 1d/p);
	}
	
//	recupera l'elemento precedente a quello ricevuto, se presente nella lista
	private static <T> Triple<T> findPrevious(ArrayList<Triple<T> > L, T k) {
		for(int i = L.size()-1; i >= 0; --i) {
			if((L.get(i).second).equals(k)) {
				return L.get(i);
			}
		}
		
		return null;
	}
	
	private static <T> boolean containsTriple(ArrayList<Triple<T>> L, T k) {
		for(int i = 0; i < L.size(); ++i) {
			if((L.get(i).second).equals(k)) {
				return true;
			}
		}
		
		return false;
	}
	
//	sceglie la direzione in base alla distanza fra Player e agente
//	tecnica greedy: ottima locale ad ogni step 
//	complessità dello step: costante
	public static int greedyPath(Agent e) {
	
		if(e instanceof IntelligentEnemy) {
			
			GameObject player = e.getEnvironment().getPlayer();
			GameObject enemy  = (GameObject) e;
			
			double min = e.getEnvironment().getDimX() + e.getEnvironment().getDimY(); 
			int ret    = (new Random()).nextInt(4);
			for(int i = 0; i < 4; ++i) {
				
				int new_x = enemy.getX() + GameObject.dmap[i][0];
				int new_y = enemy.getY() + GameObject.dmap[i][1];
				
				double temp = norm2D(player.getX()-new_x, player.getY()-new_y, 2);
				
				if(temp < min && e.getEnvironment().getTile(new_x, new_y) instanceof EmptyBlock) {
					
					min = temp;
					ret = i;
				}
			}
			
			return ret;
		}
		
		return -1;
	}

//	vista breadth-first con partenza nella posizione dell'agente e
//	e punto d'arrivo nella posizione del Player
//	garantita l'ottimalità della soluzione se possibile
//	complessità del metodo: ~O(E) con E numero di "nodi" (il numero di archi è costante)
//	complessità totale: 	~O(E*N) con N numero di nemici in vita	
	public static LinkedList<Integer> optimalPath(Agent e) {
		
		if(e instanceof IntelligentEnemy) {
			
			dim = e.getEnvironment().getDimX();
			
			GameObject player = e.getEnvironment().getPlayer();
			GameObject enemy  = (GameObject) e;
			final ConcurrentHashMap<Integer, GameObject> E = e.getEnvironment().getEmptyBlocksMap();
			
			int objective = toScalar(player.getX(), player.getY());
			int finito = toScalar(enemy.getX(), enemy.getY());
			Triple<Integer> finito2 = new Triple<Integer>(finito, finito, -1);
			
			LinkedList<Integer> L = new LinkedList<Integer>();
			ArrayList<Triple<Integer>> A = new ArrayList<Triple<Integer>>();
			
			L.addLast(finito);
			A.add(finito2);
		
			while(!L.isEmpty()) {
				Integer p = L.getFirst();
				L.removeFirst();
				
				for(int i = 0; i < 4; ++i) {
					int x = toX(p) + GameObject.dmap[i][0];
					int y = toY(p) + GameObject.dmap[i][1];
					int k = toScalar(x, y);
					
					if(!containsTriple(A, k)) {
						
						Triple<Integer> temp2 = findPrevious(A, k);
						
						if(E.get(k) != null) {
							L.addLast(k);
							A.add(new Triple<Integer>(p, k, i));
							
						} else if(k == objective) {
							
							LinkedList<Integer> ret = new LinkedList<Integer>();
							Triple<Integer> start = findPrevious(A, p);
							ret.addFirst(i);
							
							while(start != null) {
								
								Triple<Integer> temp = findPrevious(A, start.first);
								if(temp == finito2)
									return ret;
								ret.addFirst(temp.third);
								start = temp;
							}
						
						}
					}
				}
			}
		}
		
		return null;
	}
}
