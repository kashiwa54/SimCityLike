package application;

public class Residental extends Site{
	public Residental(int x,int y)	{
		this(x,y,0);
	}
	public Residental(int x,int y,int capacity)	{
		super(x,y);
		type.concat(":residental");
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
		return new Residental(this.getX(),this.getY());
	}
	@Override
	public TileObject copy(int x, int y) {
		return new Residental(x,y);
	}
}
