package application;


public class WoodsA extends Wood{
	public WoodsA()	{
		this(null,0,0);
	}
	public WoodsA(Map map,int x,int y)	{
		super(map,x,y,WoodEnum.WOODS_A);
	}
	@Override
	public TileObject copy() {
		return new WoodsA(this.getMap(),this.getX(),this.getY());
	}
	@Override
	public TileObject copy(int x, int y) {
		return new WoodsA(this.getMap(),x,y);
	}
	@Override
	public String getInfo()	{
		String info = "林\n";
		return info;
	}

}
