package application;

public class Road60 extends Road{
	static final int MAX_SPEED = 60;
	public Road60(Map map)	{
		this(map,0,0);
	}
	public Road60(Map map,int x,int y)	{
		super(map,x,y,MAX_SPEED);
		type = WayEnum.ROAD60;
	}
	@Override
	public TileObject copy() {
		return new Road60(this.getMap(),this.getX(),this.getY());
	}
	@Override
	public TileObject copy(int x, int y) {
		return new Road60(this.getMap(),x,y);
	}
}
