package application;


public class WoodsC extends Wood{
	public WoodsC()	{
		this(null,0,0);
	}
	public WoodsC(Map map,int x,int y)	{
		super(map,x,y,WoodEnum.WOODS_ABC);
	}
	@Override
	public TileObject copy() {
		return new WoodsC(this.getMap(),this.getX(),this.getY());
	}
	@Override
	public TileObject copy(int x, int y) {
		return new WoodsC(this.getMap(),x,y);
	}
	@Override
	public String getInfo()	{
		String info = "林\n";
		return info;
	}

}
