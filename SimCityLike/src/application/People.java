package application;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Random;
import java.util.Set;

public class People {
	private static final double DESIRE_MAX = CommonConst.DESIRE_MAX;

	private PeopleManager manager;
	private int age;
	private String name;
	private Habitable home;
	private Workable workspace;
	private int money;
	private Time birthday;
	private Random rnd = new Random();
	private EnumMap<Desire,Double> desireMap = new EnumMap<Desire,Double>(Desire.class)	{{
		put(Desire.FOOD, 100.0);
	}};
	private EnumMap<Desire,ArrayList<Consumable>> supplierListMap = new EnumMap<Desire,ArrayList<Consumable>>(Desire.class)	{{
		for(Desire d : desireMap.keySet())	{
			put(d,new ArrayList<Consumable>());
		}
	}};

	public People(PeopleManager manager,Time birthday,Time world,String name){
		this.manager = manager;
		this.birthday = birthday.clone();
		this.age = calcAge(world);
		this.name = name;
	}

	public void setHome(Habitable r)	{
		if(r == null)	{
			removeHome();
		}
		this.home = r;
	}
	public void setWork(Workable b)		{
		this.workspace = b;
	}
	public void setMoney(int money)	{
		this.money = money;
	}
	private int calcAge(Time now){

		return Time.calcSpan(this.birthday,now).getYear();
	}
	public int getAge(Time world)	 throws NullPointerException{
		age = calcAge(world);
		return age;
	}
	public int getMoney()	{
		return this.money;
	}
	public Habitable getHome()	{
		return this.home;
	}
	public Workable getWorkspace()	{
		return this.workspace;
	}
	public Time getBirthday()	{
		return this.birthday;
	}
	public String getName()	{
		return this.name;
	}

	public void setSupplierListMap(EnumMap<Desire,ArrayList<Consumable>> listMap)	{
		this.supplierListMap = listMap;
	}
	public void setSupplierList(Desire d,ArrayList<Consumable> list)		{
		this.supplierListMap.put(d, list);
	}
	public ArrayList<Consumable> getSupplierList(Desire d)	{
		return this.supplierListMap.get(d);
	}
	public double getDesire(Desire d)	{
		return desireMap.get(d);
	}
	public void setDesire(Desire d,double amount)	{
		desireMap.put(d, amount);
	}
	public Set<Desire> getDesireSet()	{
		return desireMap.keySet();
	}
	public void removeHome()	{
		manager.addHomelessList(this);
		this.home = null;
	}
	public void removeWork()	{
		manager.addJoblessList(this);
		this.workspace = null;
	}

	public double getDesireAverage()	{
		int cnt = 0;
		double ave = 0.0;
		for(Desire d : desireMap.keySet())	{
			ave += desireMap.get(d);
			cnt++;
		}
		if(cnt > 0) ave /= cnt;
		return ave;
	}
	public void increaseDesire(Desire d,double amount)	{
		Double desire = desireMap.get(d);
		if(desire != null)	{
			desire += amount;
			if(desire > DESIRE_MAX)	desire = DESIRE_MAX;
			if(desire < 0)	desire = 0.0;
			desireMap.put(d, desire);
		}
	}
	public void decreaseDesire(Desire d,double amount)	{
		increaseDesire(d,-amount);
	}
	public void moreDesire()	{
		for(Desire d : desireMap.keySet())	{
			decreaseDesire(d,d.getBaseDecrease() / 24);
			if(desireMap.get(d) < DESIRE_MAX / 2)	{
				shopping(d);
			}
		}
	}
	public String toString()	{
		return "name : " + name + "\n"
				+"age : " + age + "\n"
				+"birthday : " + birthday + "\n"
				+"home : " + home + "\n"
				+"workSpace : " + workspace + "\n"
				+"money : " + money + "\n";
	}
	public boolean shopping(Desire d)	{
		if(!getDesireSet().contains(d)) return false;

		int need = (int)(CommonConst.DESIRE_MAX - getDesire(d));
		for(int i = 0; i < CommonConst.CLIENT_REQUEST_MAX_NUMBER ;i++)	{
			for(Products product : d.getProducts())	{
				ArrayList<Consumable> cl = supplierListMap.get(d);
				if((cl == null)||(cl.size() <= 0))	continue;
				int index = rnd.nextInt(cl.size());
				int get = cl.get(index).consume(product, need);
				if(get + getDesire(d) > CommonConst.DESIRE_MAX)	{
					setDesire(d, CommonConst.DESIRE_MAX);
				}else {
					setDesire(d, getDesire(d) + get);
				}
				return true;
			}
		}
		return false;
	}

}

