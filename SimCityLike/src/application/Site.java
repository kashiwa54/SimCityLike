package application;

public abstract class Site extends TileObject implements Overwritable{
	public Site(int x,int y)	{
		super(x,y);
		type = null;
		setCanPass(true);
	}
}
