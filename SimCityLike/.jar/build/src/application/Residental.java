package application;

public class Residental extends Site{
	public Residental(Map map,int x,int y)	{
		this(map,x,y,0);
	}
	public Residental(Map map,int x,int y,int capacity)	{
		super(map,x,y);
		type = SiteEnum.RESIDENTAL;
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
		return new Residental(this.getMap(),this.getX(),this.getY());
	}
	@Override
	public TileObject copy(int x, int y) {
		return new Residental(this.getMap(),x,y);
	}
}
