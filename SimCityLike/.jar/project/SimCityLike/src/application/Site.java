package application;

public abstract class Site extends TileObject implements Overwritable{
	public Site(Map map,int x,int y)	{
		super(map,x,y);
		type = null;
		setCanPass(true);
	}
	public String getInfo()	{
		return "";
	}
	public void maintenance()	{

	}
}
