package application;

import java.text.NumberFormat;
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
	private int money;
	private int salary;
	private double efficiency;

	private ArrayList<Consumable> clientList = new ArrayList<Consumable>();
	private EnumSet<Products> productSet = EnumSet.noneOf(Products.class);
	private EnumMap<Products,Integer> stockMap = new EnumMap<Products,Integer>(Products.class);
	private People[] worker = null;
	public IndustrialBuilding(Map map,int x,int y,IndustrialBuildingEnum ibe)	{
		super(map,x,y,ibe.getWidth(),ibe.getHeight());
		this.workspace = ibe.getWorkspace();
		type = ibe;
		this.productCapacity = ibe.getproductCapacity();
		this.production = ibe.getProduction();
		this.productSet = ibe.getProductSet();
		this.salary = ibe.getSalary();
		for(Products p : productSet)	{
			stockMap.put(p, 0);
		}
		worker = new People[workspace];
		calcFreeWorkspace();

		money = 100000;

	}
	public String getInfo()	{
		NumberFormat per = NumberFormat.getPercentInstance();
		String info = "職場容量:" + workspace + "\n";
		int workerNumber = 0;
		for(People p : worker)	{
			if(p != null)	{
				workerNumber++;
			}
		}
		info = info.concat("労働者数:" + workerNumber + "\n");
		info = info.concat("求人数:" + freeWorkspace + "\n");
		info = info.concat("資金:" + money + "\n");
		info = info.concat("生産量:" + (int)(production * efficiency) + "\n");
		info = info.concat("生産効率:" + per.format(efficiency) + "\n");
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
	public int getMoney()	{
		return money;
	}
	public void setMoney(int money)	{
		this.money = money;
	}
	public void addMoney(int add)	{
		this.money += add;
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
		calcEfficiency();
	}
	@Override
	public boolean place() {
		industrialList.add(this);
		return true;
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

		stock += production * efficiency;
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
		if(send(packet))	{
			money += product.price * amount;
		}
		return true;

	}
	@Override
	public boolean send(ProductPacket packet) {
		return packet.getReceiver().receivePacket(packet);
	}
	public void maintenance()	{
		int tax = (int)(money * CommonConst.INDUSTRIAL_TAX_RATE);
		money -= tax;
		MoneyManager.income(tax);
		for(People w : worker)	{
			if(w != null)	{
				w.setMoney(w.getMoney() + salary);
				money -= salary;
			}
		}
		money -= getType().getMaintenanceCost();
	}
	private void calcEfficiency()	{
		int workerNum = workspace - freeWorkspace;
		efficiency = (50.0 / Math.log1p(workspace) * Math.log1p(workerNum) + 50.0)/100;
	}
}
