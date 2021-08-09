package application;

public abstract class ResidentalBuilding extends Building{
	private int capacity;
	private People[] resident = new People[capacity];
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
		this.capacity = capacity;
		type = null;
	}
	public String getInfo()	{
		String info = "住人容量:" + capacity + "\n";
		int residentNumber = 0;
		for(People p : resident)	{
			if(p == null)	{
				residentNumber++;
			}
		}
		info = info.concat("住人数:" + residentNumber + "\n");
		return info;
	}
}
