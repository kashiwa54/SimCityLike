package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class ResidentalBuilding extends Building implements Habitable{
	public static List<ResidentalBuilding> residentalList = Collections.synchronizedList(new ArrayList<ResidentalBuilding>(CommonConst.BUILDING_INISIAL_CAPACITY));
	private int capacity;
	private int freeCapacity;
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

	@Override
	public boolean addResident(People p) {
		for(int i = 0;i < resident.length;i++)	{
			if(resident[i] == null)	{
				resident[i] = p;
				p.setHome(this);
				return true;
			}
		}
		calcFreeCapacity();
		return false;
	}
	@Override
	public boolean removeResident(People p) {
		for(int i = 0;i < resident.length; i++)	{
			if (resident[i] == p)	{
				resident[i] = null;
				p.removeHome();
				while((i < resident.length - 1))	{
					resident[i] = resident[i + 1];
					if(resident[i] == null) break;
					i++;
				}
				calcFreeCapacity();
				return true;
			}
		}
		return false;
	}
	public void removeResidentAll()	{
		for(People p : resident)	{
			p.removeHome();
		}
	}
	public People[] getResident()	{
		return resident;
	}
	@Override
	public int getFreeCapacity() {
		return freeCapacity;
	}
	private void calcFreeCapacity()	{
		int rCnt = 0;
		for(People p : resident)	{
			if(p != null)	{
				rCnt++;
			}else {
				break;
			}
		}
		this.freeCapacity = capacity - rCnt;
	}
	@Override
	public boolean place() {
		residentalList.add(this);
		return false;
	}
	@Override
	public void remove() {
		residentalList.remove(this);
		for(People p : resident)	{
			p.setHome(null);
		}
	}
}
