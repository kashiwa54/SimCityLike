package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class IndustrialBuilding extends Building implements Workable{
	public static List<IndustrialBuilding> industrialList = Collections.synchronizedList(new ArrayList<IndustrialBuilding>(CommonConst.BUILDING_INISIAL_CAPACITY));
	private int workspace;
	private int freeWorkspace;
	private int productCapacity;
	private int production;
	private int stock;
	private int customerCapacity;
	private int freeCustomer;
	private People[] worker = null;
	private CommercialBuilding[] customer;
	public IndustrialBuilding(Map map,int x,int y,IndustrialBuildingEnum ibe)	{
		super(map,x,y,ibe.getWidth(),ibe.getHeight());
		this.workspace = ibe.getWorkspace();
		type = ibe;
		this.productCapacity = ibe.getproductCapacity();
		this.production = ibe.getProduction();
		this.stock = 0;
		this.customerCapacity = ibe.getCustomerCapacity();
		worker = new People[workspace];
		customer = new CommercialBuilding[customerCapacity];
		calcFreeWorkspace();
		calcFreeCustomer();
	}
	public String getInfo()	{
		String info = "職場容量:" + workspace + "\n";
		int workerNumber = 0;
		for(People p : worker)	{
			if(p != null)	{
				workerNumber++;
			}
		}
		info = info.concat("労働者数:" + workerNumber + "\n");
		info = info.concat("求人数" + freeWorkspace + "\n");
		info = info.concat("生産量" + production + "\n");
		info = info.concat("商品量" + stock + " / " + productCapacity + "\n");
		return info;
	}
	public int getProductCapacity()	{
		return this.productCapacity;
	}
	public int getProduction()	{
		return this.production;
	}
	public int getStock()	{
		return this.stock;
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
	public boolean addCustomer(CommercialBuilding c) {
		for(int i = 0;i < customer.length;i++)	{
			if(customer[i] == null)	{
				customer[i] = c;
				calcFreeWorkspace();
				return true;
			}
		}
		return false;
	}
	public boolean removeCustomer(CommercialBuilding c) {
		for(int i = 0;i < customer.length; i++)	{
			if (customer[i] == c)	{
				customer[i] = null;
				while((i < customer.length - 1))	{
					customer[i] = customer[i + 1];
					if(customer[i] == null) break;
					i++;
				}
				calcFreeCustomer();
				return true;
			}
		}
		return false;
	}
	public CommercialBuilding[] getCustomer()	{
		return customer;
	}
	public int getFreeCustomer() {
		return freeCustomer;
	}
	private void calcFreeCustomer()	{
		int cCnt = 0;
		for(CommercialBuilding c : customer)	{
			if(c != null)	{
				cCnt++;
			}else {
				break;
			}
		}
		this.freeWorkspace = workspace - cCnt;
	}
	@Override
	public boolean place() {
		industrialList.add(this);
		return false;
	}
	@Override
	public void remove() {
		industrialList.remove(this);
		for(People p : worker)	{
			if(p != null) p.setWork(null);
		}
	}
}
