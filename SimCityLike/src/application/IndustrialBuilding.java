package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;

public abstract class IndustrialBuilding extends Building implements Workable,Producable{
	public static List<IndustrialBuilding> industrialList = Collections.synchronizedList(new ArrayList<IndustrialBuilding>(CommonConst.BUILDING_INISIAL_CAPACITY));
	private int workspace;
	private int freeWorkspace;
	private int productCapacity;
	private int production;
	private int customerCapacity;
	private int freeCustomer;

	private ArrayList<Consumable> clientList = new ArrayList<Consumable>();
	private EnumSet<Products> productSet = EnumSet.noneOf(Products.class);
	private EnumMap<Products,Integer> stockMap = new EnumMap<Products,Integer>(Products.class);
	private People[] worker = null;
	private CommercialBuilding[] customer;
	public IndustrialBuilding(Map map,int x,int y,IndustrialBuildingEnum ibe)	{
		super(map,x,y,ibe.getWidth(),ibe.getHeight());
		this.workspace = ibe.getWorkspace();
		type = ibe;
		this.productCapacity = ibe.getproductCapacity();
		this.production = ibe.getProduction();
		this.customerCapacity = ibe.getCustomerCapacity();
		this.productSet = ibe.getProductSet();
		for(Products p : productSet)	{
			stockMap.put(p, 0);
		}
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
		info = info.concat("商品量\n");
		for(Products p : productSet)	{
			info = info.concat(p.japanese + " : " +stockMap.get(p) + " / " + productCapacity + "\n");
		}
		info = info.concat("納入先:" + clientList.size() + "店舗\n");
		return info;
	}
	public int getProductCapacity()	{
		return this.productCapacity;
	}
	public int getProduction()	{
		return this.production;
	}
	public int getStock(Products p)	{
		return this.stockMap.get(p);
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
	@Override
	public void setClientList(ArrayList<Consumable> list) {
		this.clientList = list;
	}
	@Override
	public ArrayList<Consumable> getClientList()	{
		return this.clientList;
	}
	@Override
	public EnumSet<Products> getProductSet()	{
		return this.productSet;
	}
	@Override
	public void produce(Products product)	{
		int stock = stockMap.get(product);
		if(stock >= productCapacity) return;

		stock += production;
		if(stock > productCapacity) stock = productCapacity;

		stockMap.put(product, stock);
	}
	@Override
	public boolean receiveRequest(Products product,int amount,Consumable c)	{
		if(!productSet.contains(product)) return false;
		int stock = stockMap.get(product);
		ProductPacket packet;
		if(stock <= 0) {
			return false;
		}else if(stock < amount)	{
			packet = new ProductPacket(product,stock,this,c);
			stock = 0;
		}else	{
			packet = new ProductPacket(product,amount,this,c);
			stock -= amount;
		}
		stockMap.put(product, stock);
		send(packet);
		return true;

	}
	@Override
	public boolean send(ProductPacket packet) {
		return packet.getReceiver().receivePacket(packet);
	}
}
