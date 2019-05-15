package logic;

import java.awt.image.BufferedImage;
//non toglietelo
import java.io.File;
import java.util.Random;
import graphics.Sprite;

public abstract class Block {

	//corrispondono ai valori di ritorno dei vari getType: essendo public, si possono usare all'esterno in modo simile a Color.RED
	public static final char EMPTY_BLOCK = 0;
	public static final char DIAMOND 	 = 1;
	public static final char GROUND 	 = 2;
	public static final char ROCK 		 = 3;
	static Map map = null;
	
	static protected Sprite spritesheet;
	protected BufferedImage sprite;	
	protected int x;
	protected int y;

	public abstract char getType(); //per distinguere i vari blocchi iterando su Map
	//gli aggiornamenti previsti dalle varie interfacce vengnono richiamati tramite update, evitando di dover castare
	public abstract void update();  //interfaccia per i metodi delle classi figlie

	public Block(int x, int y) {
		super();
		this.x = x;
		this.y = y;
		//Va creata la risorsa blockSpriteSheets as soon as possible
		//spritesheet.loadSprite("resources" + File.separator + "assets" + File.separator + "blockSpriteSheet");
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
}


class Diamond extends Block implements Destructible, Gravity{

	private static Random r = new Random();	

	public Diamond(int x, int y) {
		super(x, y);
		sprite = spritesheet.getSprite(3, r.nextInt(3));
	}

	@Override
	public void destroy(boolean condition){
		try{
			if(condition)
				map.setTile(x, y, Destructible.emptyTile);
		} catch(NullPointerException e){
			e.printStackTrace();
		}
	}

	@Override
	public void gravity(){

	}

	//gli aggiornamenti previsti dalle varie interfacce vengnono richiamati tramite update, evitando di dover castare
	@Override
  	public void update(){
		destroy(false);
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
    }

    //EmptyBlock e' l'equivalente di un elemento vuoto, questo metodo non deve fare niente
    @Override
    public void update(){}

    @Override
	public char getType(){
		return EMPTY_BLOCK;
	}
}

class Ground extends Block implements Destructible {

	public Ground(int x, int y) {
		super(x, y);
		sprite = spritesheet.getSprite(0, 0);	
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
 	public void update(){
		destroy(false);
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
		sprite = spritesheet.getSprite(r.nextInt(3), r.nextInt(3));
	}

	@Override
	public void gravity(){

	}

	@Override
    public void update(){
		gravity();
	}

	@Override
	public char getType(){
		return ROCK;
	}
}