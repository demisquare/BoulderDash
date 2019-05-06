package logic;

public abstract class EmptyBlock extends Block {

    public EmptyBlock(int x, int y){
        super(x, y);
    }

    //EmptyBlock è l'equivalente di un elemento vuoto, questo metodo non deve fare niente
    @Override
    public abstract void update(){}

    @Override
	public abstract byte getType(){
		return 0;
	}
}