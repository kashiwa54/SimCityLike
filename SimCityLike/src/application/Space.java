package application;


public class Space extends TileObject{
	public Space()	{
		this(0,0);
	}
	public Space(int x,int y)	{
		super(x,y);
		type = OtherTileEnum.SPACE;
		setCanPass(true);
	}
	@Override
	public boolean place() {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	public void remove() {
		// TODO 自動生成されたメソッド・スタブ
	}
	@Override
	public TileObject copy() {
		return new Space(this.getX(),this.getY());
	}
	@Override
	public TileObject copy(int x, int y) {
		return new Space(x,y);
	}

}
