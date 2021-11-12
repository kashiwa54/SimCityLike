package application;


public class WoodC extends Wood{
	public WoodC()	{
		this(null,0,0);
	}
	public WoodC(Map map,int x,int y)	{
		super(map,x,y,WoodEnum.WOOD_C);
	}
	@Override
	public TileObject copy() {
		return new WoodC(this.getMap(),this.getX(),this.getY());
	}
	@Override
	public TileObject copy(int x, int y) {
		return new WoodC(this.getMap(),x,y);
	}
	@Override
	public String getInfo()	{
		String info = "木\n";
		return info;
	}

}
