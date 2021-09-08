package application;

public abstract class TileObject {
	private Map fieldMap;
	private int x;
	private int y;
	private int width;
	private int height;
	private boolean isOnMap;
	private boolean canPass;
	private Road nearRoad;
	public PlacableEnum type = null;

	public TileObject(Map map)	{
		this(map,0,0,1,1);
	}
	public TileObject(Map map,int x,int y)	{
		this(map,x,y,1,1);
	}
	public TileObject(Map map,int x,int y,int width, int height)	{
		this.fieldMap = map;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	public Map getMap()	{
		return this.fieldMap;
	}
	public void setPosition(int x,int y) {
		this.x = x;
		this.y = y;
	}

	public void setSize(int width,int height)	{
		this.width = width;
		this.height = height;
	}

	public void setOnMap(boolean b)	{
		this.isOnMap = b;
	}
	public void setCanPass(boolean b)	{
		this.canPass = b;
	}


	public int getX	()	{
		return this.x;
	}

	public int getY()	{
		return this.y;
	}

	public int getWidth()	{
		return this.width;
	}
	public int getHeight()	{
		return this.height;
	}

	public PlacableEnum getType()	{
		return type;
	}
	public boolean isOnMap()	{
		return this.isOnMap;
	}
	public boolean getCanPass()	{
		return this.canPass;
	}
	public boolean haveNearRoad()	{
		if(nearRoad == null)	{
			return false;
		}else {
			return true;
		}
	}
	abstract public boolean place();
	abstract public void remove();
	abstract public TileObject copy();
	abstract public TileObject copy(int x,int y);
	abstract public String getInfo();
}
