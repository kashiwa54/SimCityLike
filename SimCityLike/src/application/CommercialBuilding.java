package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class CommercialBuilding extends Building implements Workable{
	public static List<CommercialBuilding> commercialList = Collections.synchronizedList(new ArrayList<CommercialBuilding>(CommonConst.BUILDING_INISIAL_CAPACITY));
	private int workspace;
	private int freeWorkspace;
	private int productCapacity;
	private int consumption;
	private int stock;
	private People[] worker = null;
	public CommercialBuilding(Map map)	{
		this(map,0,0);
	}
	public CommercialBuilding(Map map,int x,int y)	{
		this(map,x,y,1,1);
	}
	public CommercialBuilding(Map map,int x,int y,int width,int height)	{
		this(map,x,y,width,height,1);
	}
	public CommercialBuilding(Map map,int x,int y,int width,int height,int workspace)	{
		super(map,x,y,width,height);
		this.workspace = workspace;
		type = null;
		worker = new People[workspace];
		calcFreeWorkspace();
	}
	public String getInfo()	{
		String info = "住人容量:" + workspace + "\n";
		int residentNumber = 0;
		for(People p : worker)	{
			if(p != null)	{
				residentNumber++;
			}
		}
		info = info.concat("住人数:" + residentNumber + "\n");
		info = info.concat("住居空き" + freeWorkspace + "\n");
		return info;
	}

	public int getStock()	{
		return this.stock;
	}
	public int getConsumption()	{
		return this.consumption;
	}
	public int getProductCapacity()	{
		return this.productCapacity;
	}
	
	@Override
	public boolean addWorker(People p) {
		for(int i = 0;i < worker.length;i++)	{
			if(worker[i] == null)	{
				worker[i] = p;
				p.setWork(this);
				calcFreeWorkspace();
				return true;
			}
		}
		return false;
	}
	@Override
	public boolean removeWorker(People p) {
		for(int i = 0;i < worker.length; i++)	{
			if (worker[i] == p)	{
				worker[i] = null;
				p.removeWork();
				while((i < worker.length - 1))	{
					worker[i] = worker[i + 1];
					if(worker[i] == null) break;
					i++;
				}
				calcFreeWorkspace();
				return true;
			}
		}
		return false;
	}
	public void removeWorkerAll()	{
		for(People p : worker)	{
			if(p != null) p.removeWork();
		}
	}
	public People[] getWorker()	{
		return worker;
	}
	@Override
	public int getFreeWorkspace() {
		return freeWorkspace;
	}
	private void calcFreeWorkspace()	{
		int rCnt = 0;
		for(People p : worker)	{
			if(p != null)	{
				rCnt++;
			}else {
				break;
			}
		}
		this.freeWorkspace = workspace - rCnt;
	}
	@Override
	public boolean place() {
		commercialList.add(this);
		return false;
	}
	@Override
	public void remove() {
		commercialList.remove(this);
		for(People p : worker)	{
			if(p != null) p.setWork(null);
		}
	}
}
