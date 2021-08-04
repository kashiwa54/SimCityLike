package application;

public abstract class Site extends TileObject{
	public Site(int x,int y)	{
		super(x,y);
		type.concat(":site");
		setCanPass(true);
	}
}
