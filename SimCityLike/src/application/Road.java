package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Road extends Way implements Upgradable{
	public static List<Road> roadList = Collections.synchronizedList(new ArrayList<Road>(CommonConst.WAY_INISIAL_CAPACITY));

	public Road(Map map)	{
		this(map,0,0);
	}

	public Road(Map map,int x,int y)	{
		this(map,x,y,Way.DEFAULT_SPEED);
	}

	public Road(Map map,int x,int y,int maxSpeed)	{
		super(map,x,y,maxSpeed);
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
			return true;
		}else {
			System.out.println("connect fault.");
			return false;
		}
	}
	public TileObject upgrade(TileObject o)	{
		if(o instanceof Road)	{
			if(WayEnum.classToEnum(o.getClass()).getLevel() > WayEnum.classToEnum(this.getClass()).getLevel())	{
				Road r = (Road) o;
				r.setPosition(this.getX(), this.getY());
				r.setConnectMap(this.getConnectMap());
				return o;
			}
		}
		return this;
	}
	@Override
	public boolean place() {
		roadList.add(this);
		return true;
	}
	@Override
	public void remove()	{
		super.remove();
		roadList.remove(this);
	}
	public void maintenance()	{
		MoneyManager.expenditure(getType().getMaintenanceCost());
	}
}
