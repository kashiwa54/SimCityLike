package application;

public abstract class Building extends TileObject{
	private int years;

	public Building()	{
		this(0,0);
	}
	public Building(int x,int y)	{
		this(x,y,1,1);
	}
	public Building(int x,int y,int width,int height) {
		super(x,y,width,height);
		type = null;
		setCanPass(false);
	}

	public void setYears(int y)	{
		this.years = y;
	}

	public int getYears()	{
		return this.years;
	}

}
