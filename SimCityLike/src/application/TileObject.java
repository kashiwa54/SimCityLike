package application;

public abstract class TileObject {
	private static final int DISTANCE = CommonConst.NEAR_ROAD_DISTANCE;
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
			if (nearRoad.isOnMap())	{
				return true;
			}else {
				nearRoad = null;
				return false;
			}
		}
	}
	public void refresh()	{
		checkNearRoad();
	}
	public Road checkNearRoad()	{
		Road best = null;
		for(int i = 0; i < DISTANCE;i++)	{
			for(int j = -i;j <= i;j++)	{
				findBestRoad(fieldMap.getTileObject(this.getX() + j,this.getY() + i),best);
				findBestRoad(fieldMap.getTileObject(this.getX() + j,this.getY() - i),best);
			}
			for(int j = -i + 1;j <= i - 1;j++)	{
				findBestRoad(fieldMap.getTileObject(this.getX() + i,this.getY() + j),best);
				findBestRoad(fieldMap.getTileObject(this.getX() - i,this.getY() + j),best);
			}
		}
		return null;
	}

	private void findBestRoad(TileObject t,Road best)	{
		double bestDistance = 0.0;
		if((t != null)&&(t instanceof Road))	{
			if(best != null)	{
				bestDistance = Math.sqrt(Math.pow(best.getX() - this.getX(),2) + Math.pow(best.getY() - this.getY(), 2));
				if(bestDistance > Math.sqrt(Math.pow(t.getX() - this.getX(),2) + Math.pow(t.getY() - this.getY(), 2)))	{
					best = (Road) t;
				}
			}else {
				best = (Road) t;
			}
		}
	}
	abstract public boolean place();
	abstract public void remove();
	abstract public TileObject copy();
	abstract public TileObject copy(int x,int y);
	abstract public String getInfo();
}
