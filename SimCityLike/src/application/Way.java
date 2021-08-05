package application;

import java.util.EnumMap;
import java.util.EnumSet;

public abstract class Way extends TileObject{
	public static final int DEFAULT_SPEED = 60;
	private int maxSpeed;
	private EnumMap<Direction,Way> connectMap = new EnumMap<Direction,Way>(Direction.class);
	private EnumSet<Direction> connectSet = EnumSet.noneOf(Direction.class);

	public Way()	{
		this(0,0,DEFAULT_SPEED);
	}
	public Way(int x,int y)	{
		this(x,y,DEFAULT_SPEED);
	}
	public Way(int x,int y,int maxSpeed)	{
		super(x,y);
		this.type = null;
		setCanPass(true);
	}
	public void setMaxSpeed(int s)	{
		this.maxSpeed = s;
	}
	public int getMaxSpeed()	{
		return this.maxSpeed;
	}
	public EnumSet<Direction> getConnect()	{
		return this.connectSet;
	}
	protected void addWay(Direction d,Way w)	{
		connectMap.put(d, w);
	}
	protected void romoveWay(Direction d)	{
		connectMap.remove(d);
	}
	protected void addConnect(Direction... d)	{
		for(Direction v : d)	{
			this.connectSet.add(v);
		}
	}
	protected void removeConnect(Direction... d) {
		for(Direction v : d)	{
			this.connectSet.remove(v);
		}
	}
	public boolean isConnect(Direction d)	{
		return this.connectSet.contains(d);
	}
	public abstract boolean connect(Direction d,Way w);
	public abstract void disconnect(Direction d);
}
