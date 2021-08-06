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
	public boolean connect(Way w)	{
		if(w instanceof Road)	{
			Direction d = this.checkDirection(w);
			if(d == null)	{
				return false;
			}
			addConnect(d);
			addWay(d,w);

			Direction rd = Direction.reverse(d);
			w.addConnect(rd);
			w.addWay(rd, this);
			System.out.println(w.toString() + " and " + this.toString() + " connected.");
			return true;
		}else {
			System.out.println("connect fault.");
			return false;
		}
	}
}
