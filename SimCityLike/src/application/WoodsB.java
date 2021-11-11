package application;


public class WoodsB extends Wood{
	public WoodsB()	{
		this(null,0,0);
	}
	public WoodsB(Map map,int x,int y)	{
		super(map,x,y,WoodEnum.WOODS_B);
	}
	@Override
	public TileObject copy() {
		return new WoodsB(this.getMap(),this.getX(),this.getY());
	}
	@Override
	public TileObject copy(int x, int y) {
		return new WoodsB(this.getMap(),x,y);
	}
	@Override
	public String getInfo()	{
		String info = "林\n";
		return info;
	}

}
