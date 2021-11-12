package application;


public class WoodA extends Wood{
	public WoodA()	{
		this(null,0,0);
	}
	public WoodA(Map map,int x,int y)	{
		super(map,x,y,WoodEnum.WOOD_A);
	}
	@Override
	public TileObject copy() {
		return new WoodA(this.getMap(),this.getX(),this.getY());
	}
	@Override
	public TileObject copy(int x, int y) {
		return new WoodA(this.getMap(),x,y);
	}
	@Override
	public String getInfo()	{
		String info = "木\n";
		return info;
	}

}
