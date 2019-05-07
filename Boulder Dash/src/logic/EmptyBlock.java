package logic;

public class EmptyBlock extends Block {

    public EmptyBlock(){
        super(-1, -1);
    }

    //EmptyBlock è l'equivalente di un elemento vuoto, questo metodo non deve fare niente
    @Override
    public void update(){}

    @Override
	public byte getType(){
		return EMPTY_BLOCK;
	}
}