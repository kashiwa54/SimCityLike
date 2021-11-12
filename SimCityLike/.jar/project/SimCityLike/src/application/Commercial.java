package application;

public class Commercial extends Site{
	public Commercial(Map map,int x,int y)	{
		this(map,x,y,0);
	}
	public Commercial(Map map,int x,int y,int capacity)	{
		super(map,x,y);
		type = SiteEnum.COMMERCIAL;
	}
	@Override
	public boolean place() {

		return false;
	}
	@Override
	public void remove() {
		// TODO 自動生成されたメソッド・スタブ
	}
	@Override
	public TileObject copy() {
		return new Commercial(this.getMap(),this.getX(),this.getY());
	}
	@Override
	public TileObject copy(int x, int y) {
		return new Commercial(this.getMap(),x,y);
	}
}