package application;

public class People {
	private int age;
	private Residental home;
	private Building workspace;
	private int money;
	private Time birthday;

	public People(Time birthday,Time world){
		this.birthday = birthday.clone();
		age = calcAge(world);
	}

	public void setHome(Residental r)	{
		this.home = r;
	}
	public void setWork(Building b)		{
		this.workspace = b;
	}
	public void setMoney(int money)	{
		this.money = money;
	}
	private int calcAge(Time now){

		return Time.calcSpan(now, this.birthday).getYear();
	}
	public int getAge(Time world)	 throws NullPointerException{
		age = calcAge(world);
		return age;
	}
	public int getMoney()	{
		return this.money;
	}
	public Residental getHome()	{
		return this.home;
	}
	public Building getWorkspace()	{
		return this.workspace;
	}
	public Time getBirthday()	{
		return this.birthday;
	}
}
