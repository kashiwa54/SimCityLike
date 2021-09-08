package application;

public class Industrial extends Site{
	public Industrial(Map map,int x,int y)	{
		this(map,x,y,0);
	}
	public Industrial(Map map,int x,int y,int capacity)	{
		super(map,x,y);
		type = SiteEnum.INDUSTRIAL;
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
		return new Industrial(this.getMap(),this.getX(),this.getY());
	}
	@Override
	public TileObject copy(int x, int y) {
		return new Industrial(this.getMap(),x,y);
	}
}