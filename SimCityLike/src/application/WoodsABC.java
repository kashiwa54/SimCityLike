package application;


public class WoodsABC extends Wood{
	public WoodsABC()	{
		this(null,0,0);
	}
	public WoodsABC(Map map,int x,int y)	{
		super(map,x,y,WoodEnum.WOODS_C);
	}
	@Override
	public TileObject copy() {
		return new WoodsABC(this.getMap(),this.getX(),this.getY());
	}
	@Override
	public TileObject copy(int x, int y) {
		return new WoodsABC(this.getMap(),x,y);
	}
	@Override
	public String getInfo()	{
		String info = "林\n";
		return info;
	}

}
