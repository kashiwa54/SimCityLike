package application;

public abstract class Road extends Way{
	public Road()	{
		this(0,0);
	}

	public Road(int x,int y)	{
		this(x,y,Way.DEFAULT_SPEED);
	}

	public Road(int x,int y,int maxSpeed)	{
		super(x,y,maxSpeed);
	}

}
