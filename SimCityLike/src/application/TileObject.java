package application;

import java.util.ArrayList;
import java.util.List;

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

	private static List<List<? extends TileObject>> objectListSet = new ArrayList<List<? extends TileObject>>()	{{
		add(ResidentalBuilding.residentalList);
		add(CommercialBuilding.commercialList);
		add(IndustrialBuilding.industrialList);
		add(Road.roadList);
	}};

	protected static ProductManager pm;
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
	public static void setProductManager(ProductManager proMng)	{
		pm = proMng;
	}
	public void setMap(Map map)	{
		this.fieldMap = map;
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
	public static List<List<? extends TileObject>> getListSet()	{
		return objectListSet;
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
	public Road getNearRoad()	{
		checkNearRoad();
		return this.nearRoad;
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


	public static <T extends TileObject> T getObject(Class<T> obj)	{
		try {
			return obj.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	public void checkNearRoad()	{
		Road best = null;
		for(int i = 0; i <= DISTANCE;i++)	{
			for(int j = -i;j <= i;j++)	{
				best = findBestRoad(fieldMap.getTileObject(this.getX() + j,this.getY() + i),best);
				best = findBestRoad(fieldMap.getTileObject(this.getX() + j,this.getY() - i),best);
			}
			for(int j = -i + 1;j <= i - 1;j++)	{
				best = findBestRoad(fieldMap.getTileObject(this.getX() + i,this.getY() + j),best);
				best = findBestRoad(fieldMap.getTileObject(this.getX() - i,this.getY() + j),best);
			}
		}
		nearRoad = best;
	}

	private Road findBestRoad(TileObject t,Road best)	{
		if((t != null)&&(t instanceof Road))	{
			if(best != null)	{
				double bestDistance = Math.sqrt(Math.pow(best.getX() - this.getX(),2) + Math.pow(best.getY() - this.getY(), 2));
				if(bestDistance > Math.sqrt(Math.pow(t.getX() - this.getX(),2) + Math.pow(t.getY() - this.getY(), 2)))	{
					best = (Road) t;
				}
			}else {
				best = (Road) t;
			}
		}
		return best;
	}

	abstract public boolean place();
	abstract public void remove();
	abstract public TileObject copy();
	abstract public TileObject copy(int x,int y);
	abstract public String getInfo();
	abstract public void maintenance();
}
