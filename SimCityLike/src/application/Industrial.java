package application;

public class Industrial extends Site{
	public Industrial(int x,int y)	{
		this(x,y,0);
	}
	public Industrial(int x,int y,int capacity)	{
		super(x,y);
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
		return new Industrial(this.getX(),this.getY());
	}
	@Override
	public TileObject copy(int x, int y) {
		return new Industrial(x,y);
	}
}