package application;

public abstract class Way extends TileObject{
	public static final int DEFAULT_SPEED = 60;
	private int maxSpeed;
	private boolean isConnectNorth = false;
	private boolean isConnectEast = false;
	private boolean isConnectSouth = false;
	private boolean isConnectWest = false;

	public Way()	{
		this(0,0,DEFAULT_SPEED);
	}
	public Way(int x,int y)	{
		this(x,y,DEFAULT_SPEED);
	}
	public Way(int x,int y,int maxSpeed)	{
		super(x,y);
		type = null;
		setCanPass(true);
	}
	public void setMaxSpeed(int s)	{
		this.maxSpeed = s;
	}
	public int getMaxSpeed()	{
		return this.maxSpeed;
	}

	public boolean getConnectNorth()	{
		return this.isConnectNorth;
	}
	public boolean getConnectEast()	{
		return this.isConnectEast;
	}
	public boolean getConnectSouth()	{
		return this.isConnectSouth;
	}
	public boolean getConnectWest()	{
		return this.isConnectWest;
	}
	public boolean ConnectNorth()	{
		isConnectNorth = true;
		return true;
	}
	public boolean ConnectEast()	{
		isConnectEast = true;
		return true;
	}
	public boolean ConnectSouth()	{
		isConnectSouth = true;
		return true;
	}
	public boolean ConnectWest()	{
		isConnectWest = true;
		return true;
	}
	public boolean DisconnectNorth()	{
		isConnectNorth = false;
		return true;
	}
	public boolean DisconnectEast()	{
		isConnectEast = false;
		return true;
	}
	public boolean DisconnectSouth()	{
		isConnectSouth = false;
		return true;
	}
	public boolean DisconnectWest()	{
		isConnectWest = false;
		return true;
	}
}
