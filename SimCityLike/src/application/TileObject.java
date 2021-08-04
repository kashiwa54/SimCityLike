package application;

public abstract class TileObject {
	private int x;
	private int y;
	private int width;
	private int height;
	private boolean isOnMap;
	private boolean canPass;
	protected String type = "Tile";

	public TileObject()	{
		this(0,0,1,1);
	}
	public TileObject(int x,int y)	{
		this(x,y,1,1);
	}
	public TileObject(int x,int y,int width, int height)	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
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

	public String getType()	{
		return type;
	}
	public String getLastType()	{
		return type.substring(type.lastIndexOf(':'));
	}

	public boolean isOnMap()	{
		return this.isOnMap;
	}
	public boolean getCanPass()	{
		return this.canPass;
	}
	abstract public boolean place();
	abstract public void remove();
	abstract public TileObject copy();
	abstract public TileObject copy(int x,int y);
}
