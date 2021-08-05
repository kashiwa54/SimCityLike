package application;

public class Road60 extends Road{
	static final int MAX_SPEED = 60;
	public Road60()	{
		this(0,0);
	}
	public Road60(int x,int y)	{
		super(x,y,MAX_SPEED);
		type = WayEnum.ROAD60;
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
		return new Road60(this.getX(),this.getY());
	}
	@Override
	public TileObject copy(int x, int y) {
		return new Road60(x,y);
	}
	@Override
	public boolean connect(Direction d,Way w) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}
	@Override
	public void disconnect(Direction d) {
		// TODO 自動生成されたメソッド・スタブ

	}

}
