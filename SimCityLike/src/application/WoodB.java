package application;


public class WoodB extends Wood{
	public WoodB()	{
		this(null,0,0);
	}
	public WoodB(Map map,int x,int y)	{
		super(map,x,y,WoodEnum.WOOD_B);
	}
	@Override
	public TileObject copy() {
		return new WoodB(this.getMap(),this.getX(),this.getY());
	}
	@Override
	public TileObject copy(int x, int y) {
		return new WoodB(this.getMap(),x,y);
	}
	@Override
	public String getInfo()	{
		String info = "木\n";
		return info;
	}

}
