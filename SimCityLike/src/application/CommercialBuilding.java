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
	private int customerCapacity;
	private int freeCustomer;
	private People[] worker = null;
	private People[] customer = null;
	public CommercialBuilding(Map map,int x,int y,CommercialBuildingEnum cbe)	{
		super(map,x,y,cbe.getWidth(),cbe.getHeight());
		this.workspace = cbe.getWorkspace();
		this.productCapacity = cbe.getproductCapacity();
		this.consumption = cbe.getConsumption();
		this.stock = 0;
		this.customerCapacity = cbe.getCustomerCapacity();
		type = cbe;
		worker = new People[workspace];
		customer = new People[customerCapacity];
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
				while(i < worker.length - 1)	{
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
	public boolean addCustomer(People p) {
		for(int i = 0;i < customer.length;i++)	{
			if(customer[i] == null)	{
				customer[i] = p;
				calcFreeCustomer();
				return true;
			}
		}
		return false;
	}
	public boolean removeCustomer(People p) {
		for(int i = 0;i < customer.length; i++)	{
			if (customer[i] == p)	{
				customer[i] = null;
				while(i < customer.length - 1)	{
					customer[i] = customer[i + 1];
					if(customer[i] == null) break;
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
	public void removeCustomerAll()	{
		for(People p : customer)	{
			if(p != null) p.removeWork();
		}
	}
	public People[] getWorker()	{
		return worker;
	}
	public People[] getCustomer()	{
		return customer;
	}
	@Override
	public int getFreeWorkspace() {
		return freeWorkspace;
	}
	public int getFreeCustomer()	{
		return freeCustomer;
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
	private void calcFreeCustomer()	{
		int rCnt = 0;
		for(People p : customer)	{
			if(p != null)	{
				rCnt++;
			}else {
				break;
			}
		}
		this.freeCustomer = customerCapacity - rCnt;
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
