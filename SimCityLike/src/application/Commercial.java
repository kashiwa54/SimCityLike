package application;

public class Commercial extends Site{
	public Commercial(int x,int y)	{
		this(x,y,0);
	}
	public Commercial(int x,int y,int capacity)	{
		super(x,y);
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
		return new Commercial(this.getX(),this.getY());
	}
	@Override
	public TileObject copy(int x, int y) {
		return new Commercial(x,y);
	}
}