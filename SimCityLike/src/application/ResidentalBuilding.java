package application;

public abstract class ResidentalBuilding extends Building{
	private int capacity;
	private People[] resident;
	public ResidentalBuilding()	{
		this(0,0);
	}
	public ResidentalBuilding(int x,int y)	{
		this(x,y,1,1);
	}
	public ResidentalBuilding(int x,int y,int width,int height)	{
		this(x,y,width,height,1);
	}
	public ResidentalBuilding(int x,int y,int width,int height,int capacity)	{
		super(x,y,width,height);
		type = null;
	}

}
