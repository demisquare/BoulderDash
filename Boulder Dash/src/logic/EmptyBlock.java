package logic;

public abstract class EmptyBlock extends Block {

    public EmptyBlock(){
        super(-1, -1, null);
    }

    //EmptyBlock è l'equivalente di un elemento vuoto, questo metodo non deve fare niente
    @Override
    public abstract void update(){}

    @Override
	public abstract byte getType(){
		return EMPTY_BLOCK;
	}
}