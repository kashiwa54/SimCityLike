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
	public abstract boolean connect(Way w);
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
	protected void removeWay(Direction d)	{
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
	protected EnumMap<Direction,Way> getConnectMap()	{
		return connectMap;
	}
	protected boolean setConnectMap(EnumMap<Direction,Way> map)	{
		for(Direction d : map.keySet())	{
			if (d != checkDirection(map.get(d)))	{
				System.out.println(map.get(d) + " is not " + d);
				return false;
			}
		}
		this.connectMap = map;
		return true;
	}
	public boolean isConnect(Direction d)	{
		return this.connectSet.contains(d);
	}
	public Way getConnectWay(Direction d)	{
		return connectMap.get(d);
	}
	public Direction checkDirection(Way w)	{
		if(w.getY() == this.getY() - 1)	{
			if(w.getX() == this.getX())		{
				return Direction.NORTH;
			}
		}else if(w.getY() == this.getY() + 1)	{
			if(w.getX() == this.getX())		{
				return Direction.SOUTH;
			}
		}
		if(w.getX() == this.getX() + 1)	{
			if(w.getY() == this.getY())		{
				return Direction.EAST;
			}
		}else if(w.getX() == this.getX() - 1)	{
			if(w.getY() == this.getY())		{
				return Direction.WEST;
			}
		}
		return null;
	}
	public void disconnect(Direction d) {
		Way w = connectMap.get(d);
		Direction rd = Direction.reverse(d);
		w.removeWay(rd);
		w.removeConnect(rd);
		removeWay(d);
		removeConnect(d);
	}
	public void remove()	{
		for(Direction d : this.connectSet)	{
			disconnect(d);
		}
	}
}
