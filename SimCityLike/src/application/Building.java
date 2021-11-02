package application;

public abstract class Building extends TileObject{
	private int years;
	private int ruinPoint;
	public Building() {
		this(null);
	}
	public Building(Map map)	{
		this(map,0,0);
	}
	public Building(Map map,int x,int y)	{
		this(map,x,y,1,1);
	}
	public Building(Map map,int x,int y,int width,int height) {
		super(map,x,y,width,height);
		type = null;
		setCanPass(false);
	}
	public void setYears(int y)	{
		this.years = y;
	}

	public int getYears()	{
		return this.years;
	}
	public void setRuinPoint(int p)	{
		this.ruinPoint = p;
	}
	public int getRuinPoint()	{
		return ruinPoint;
	}
	public void addRuinPoint(int p)	{
		this.ruinPoint += p;
	}

}
