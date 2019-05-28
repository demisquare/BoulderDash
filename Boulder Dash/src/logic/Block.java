package logic;

import java.awt.image.BufferedImage;
//non toglietelo
	import java.io.File;
import java.util.Random;
import graphics.Sprite;

public abstract class Block {

	//corrispondono ai valori di ritorno dei vari getType: essendo public, si possono usare all'esterno in modo simile a Color.RED
	public static final char EMPTY_BLOCK = '0';
	public static final char WALL	 	 = '1';
	public static final char DIAMOND 	 = '2';
	public static final char GROUND 	 = '3';
	public static final char ROCK 		 = '4';
	static Map map = null;
	
	protected static Sprite spritesheet = new Sprite();
	protected BufferedImage sprite;	

	protected int x;
	protected int y;

	public abstract char getType(); //per distinguere i vari blocchi iterando su Map
	//gli aggiornamenti previsti dalle varie interfacce vengnono richiamati tramite update, evitando di dover castare
	public abstract void update(boolean cond);  //interfaccia per i metodi delle classi figlie

	public Block(int x, int y) {
		super();
		this.x = x;
		this.y = y;
		spritesheet.loadSprite("blockSpriteSheet");
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public BufferedImage getSprite() {
		return sprite;
	}
	
	public void setSprite(BufferedImage sprite) {
		this.sprite = sprite;
	}
}



class Diamond extends Block implements Destructible, Gravity {

	private static Random r = new Random();	

	public Diamond(int x, int y) {
		super(x, y);
		sprite = spritesheet.getSprite(r.nextInt(2), 3);
	}

	@Override
	public void destroy(boolean condition) {
		try {
			if(condition)
				map.setTile(x, y, Destructible.emptyTile);
		} catch(NullPointerException e){
			e.printStackTrace();
		}
	}

	@Override
	public void gravity() {
		
		if(y+1 < 0 || y+1 >= map.dimY) return;
		
		if(map.getTile(x, y+1) instanceof EmptyBlock) {
			map.setTile(x, y+1, this);
			map.setTile(x, y, Destructible.emptyTile);
			y += 1;
		}
	}

	//gli aggiornamenti previsti dalle varie interfacce vengnono 
	//richiamati tramite update, evitando di dover castare
	@Override
  	public void update(boolean cond){
		destroy(cond);
		gravity();
	}

	@Override
	public char getType(){
		return DIAMOND;
	}
}



class EmptyBlock extends Block {

    public EmptyBlock(){
        super(-1, -1);
        sprite = spritesheet.getSprite(1, 2);
    }

    //EmptyBlock e' l'equivalente di un elemento vuoto, questo metodo non deve fare niente
    @Override
    public void update(boolean cond){}

    @Override
	public char getType(){
		return EMPTY_BLOCK;
	}
}



class Ground extends Block implements Destructible {

	public Ground(int x, int y) {
		super(x, y);
		sprite = spritesheet.getSprite(0, 2);	
	}

	@Override
	public void destroy(boolean condition) {
		try{
			if(condition)
				map.setTile(x, y, Destructible.emptyTile);
		} catch(NullPointerException e){
			e.printStackTrace();
		}
	}

	//gli aggiornamenti previsti dalle varie interfacce vengnono richiamati tramite update, evitando di dover castare
	@Override
 	public void update(boolean cond){
		destroy(cond);
	}

	@Override
	public char getType(){
		return GROUND;
	}
}



class Rock extends Block implements Gravity {

	private static Random r = new Random();

	public Rock(int x, int y) {
		super(x, y);
		sprite = spritesheet.getSprite(r.nextInt(2), 0);
	}

	@Override
	public void gravity(){
		
		if(y+1 < 0 || y+1 >= map.dimY) return;
		
		if(map.getTile(x, y+1) instanceof EmptyBlock) {
			map.setTile(x, y+1, this);
			map.setTile(x, y, Destructible.emptyTile);
			y += 1;
		}
		
	}

	@Override
    public void update(boolean cond){
		gravity();
	}

	@Override
	public char getType(){
		return ROCK;
	}
}



class Wall extends Block {

	public Wall(int x, int y) {
		super(x, y);
		sprite = spritesheet.getSprite(0, 1);
	}

	@Override
    public void update(boolean cond){
	}

	@Override
	public char getType(){
		return WALL;
	}
}