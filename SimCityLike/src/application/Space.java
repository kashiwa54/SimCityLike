package application;


public class Space extends TileObject implements Overwritable{
	public Space(Map map)	{
		this(map,0,0);
	}
	public Space(Map map,int x,int y)	{
		super(map,x,y);
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
		return new Space(this.getMap(),this.getX(),this.getY());
	}
	@Override
	public TileObject copy(int x, int y) {
		return new Space(this.getMap(),x,y);
	}
	@Override
	public String getInfo()	{
		return "";
	}

}
