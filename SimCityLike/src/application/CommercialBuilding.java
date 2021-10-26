package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;

public abstract class CommercialBuilding extends Building implements Workable,Consumable{
	public static List<CommercialBuilding> commercialList = Collections.synchronizedList(new ArrayList<CommercialBuilding>(CommonConst.BUILDING_INISIAL_CAPACITY));
	private int workspace;
	private int freeWorkspace;
	private int productCapacity;
	private int consumption;
	private int customerCapacity;
	private int freeCustomer;
	private People[] worker = null;
	private People[] customer = null;

	private ArrayList<Producable> clientList = new ArrayList<Producable>();
	private EnumSet<Products> consumeSet = EnumSet.noneOf(Products.class);
	private EnumMap<Products,Integer> stockMap = new EnumMap<Products,Integer>(Products.class);

	Random rnd = new Random();
	public CommercialBuilding(Map map,int x,int y,CommercialBuildingEnum cbe)	{
		super(map,x,y,cbe.getWidth(),cbe.getHeight());
		this.workspace = cbe.getWorkspace();
		this.productCapacity = cbe.getproductCapacity();
		this.consumption = cbe.getConsumption();
		this.consumeSet = cbe.getConsumeSet();
		for(Products p : consumeSet)	{
			stockMap.put(p, 0);
		}
		this.customerCapacity = cbe.getCustomerCapacity();
		type = cbe;
		worker = new People[workspace];
		customer = new People[customerCapacity];
		calcFreeWorkspace();
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
		info = info.concat("商品消費量" + consumption + "\n");
		info = info.concat("商品量\n");
		for(Products p : consumeSet)	{
			info = info.concat(p.japanese + " : " +stockMap.get(p) + " / " + productCapacity + "\n");
		}
		return info;
	}

	public int getStock(Products product)	{
		return stockMap.get(product);
	}
	public int getConsumption()	{
		return this.consumption;
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
	public int consume(Products product)	{
		int stock = stockMap.get(product);
		if(stock >= consumption)	{
			stockMap.put(product, stock -= consumption);
			if(stockMap.get(product) <= productCapacity / 2)	{
				selectingImport(product,productCapacity / 2);
			}
			return consumption;
		}else	{
			int tmp = stock;
			stockMap.put(product, 0);
			selectingImport(product,productCapacity);
			return tmp;
		}
	}
	public boolean selectingImport(Products product,int amount)	{
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
		return true;

	}
}
