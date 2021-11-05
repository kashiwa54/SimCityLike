package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;

public abstract class CommercialBuilding extends Building implements Workable,Consumable{
	public static List<CommercialBuilding> commercialList = Collections.synchronizedList(new ArrayList<CommercialBuilding>(CommonConst.BUILDING_INISIAL_CAPACITY));
	private final int workspace;
	private int freeWorkspace;
	private final int productCapacity;
	private final int maxConsumption;
	private int todayStock;
	private People[] worker = null;
	private int money;

	private ArrayList<Producable> clientList = new ArrayList<Producable>();
	private EnumSet<Products> consumeSet = EnumSet.noneOf(Products.class);
	private EnumMap<Products,Integer> stockMap = new EnumMap<Products,Integer>(Products.class);

	private ArrayList<People> customerList = new ArrayList<People>();

	Random rnd = new Random();
	public CommercialBuilding(Map map,int x,int y,CommercialBuildingEnum cbe)	{
		super(map,x,y,cbe.getWidth(),cbe.getHeight());
		this.workspace = cbe.getWorkspace();
		this.productCapacity = cbe.getproductCapacity();
		this.maxConsumption = cbe.getConsumption();
		this.consumeSet = cbe.getConsumeSet();
		for(Products p : consumeSet)	{
			stockMap.put(p, 0);
		}
		type = cbe;
		worker = new People[workspace];
		calcFreeWorkspace();

		money = 100000;
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
		info = info.concat("資金:" + money + "\n");
		info = info.concat("商品量\n");
		info = info.concat("仕入れ先:" + clientList.size() + "個\n");
		info = info.concat("顧客:" + customerList.size() + "人\n");
		for(Products p : consumeSet)	{
			info = info.concat(p.japanese + " : " +stockMap.get(p) + " / " + productCapacity + "\n");
		}
		return info;
	}

	public int getStock(Products product)	{
		return stockMap.get(product);
	}
	public int getMaxConsumption()	{
		return this.maxConsumption;
	}
	public int getProductCapacity()	{
		return this.productCapacity;
	}
	public double getStockAverage()	{
		double ave = 0;
		int cnt = 0;
		for(Products p : stockMap.keySet())	{
			ave += stockMap.get(p);
			cnt++;
		}
		if(cnt > 0) ave /= cnt;
		return ave;
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
	@Override
	public void setClientList(ArrayList<Producable> list)	{
		this.clientList = list;
	}
	@Override
	public ArrayList<Producable> getClientList()	{
		return this.clientList;
	}
	@Override
	public EnumSet<Products> getConsumeSet()	{
		return this.consumeSet;
	}
	@Override
	public int consume(Products product,int amount)	{
		Integer stock = stockMap.get(product);
		if(stock == null)	return 0;
		int s = stock.intValue();
		if(s > todayStock) s = todayStock;
		if(s >= amount)	{
			stockMap.put(product, s -= amount);
			if(stockMap.get(product) <= productCapacity / 2)	{
				selectingImport(product,productCapacity / 2);
			}
			todayStock -= amount;
			money += product.price * amount;
			return amount;
		}else	{
			int tmp = s;
			stockMap.put(product, 0);
			selectingImport(product,productCapacity);
			todayStock -= tmp;
			money += product.price * tmp;
			return tmp;
		}
	}
	public boolean selectingImport(Products product,int amount)	{
		if((clientList == null)||(clientList.size() <= 0)) return false;
		@SuppressWarnings("unchecked")
		ArrayList<Producable> clients = (ArrayList<Producable>) clientList.clone();
		int len = clientList.size();
		for(int i = 0; i < CommonConst.CLIENT_REQUEST_MAX_NUMBER;i++)	{
			int index = 0;
			if(len > 1)	{
				index = rnd.nextInt(len);
			}
			Producable c = clients.get(index);
			if(request(c,product, amount))	return true;
			if(index == 0) break;
			Producable tmp = clients.get(index);
			clients.set(index, clients.get(len - 1));
			clients.set(len - 1, tmp);
			len--;
		}
		return false;
	}
	@Override
	public boolean request(Producable to,Products product,int amount)	{
		return to.receiveRequest(product, amount,this);
	}
	@Override
	public boolean receivePacket(ProductPacket packet)	{
		if(packet.getReceiver() != this) return false;
		Products p = packet.getProduct();
		int stock = stockMap.get(p);
		stock += packet.getAmount();
		if(stock > productCapacity)	stock = productCapacity;
		stockMap.put(p,stock);

		money -= (int)(p.price * packet.getAmount() * CommonConst.PRODUCT_DISCOUNT_RATE);
		return true;

	}
	public void setCustomerList(ArrayList<People> list) {
		this.customerList = list;
	}
	public ArrayList<People> getCustomerList(){
		return customerList;
	}
	public void resetTodayStock()	{
		todayStock = maxConsumption;
	}
	public void maintenance()	{
		money -= getType().getMaintenanceCost();
	}
}
