package application;

public class People {
	private int age;
	private Residental home;
	private Building workspace;
	private int money;
	private Time birthday;

	private static Time worldClock = null;

	public People(Time birthday) throws NullPointerException {
		this.birthday = birthday.clone();
		age = calcAge(worldClock);
	}

	public static void setWorldClock(Time w)	{
		worldClock = w;
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
	private int calcAge(Time now) throws NullPointerException {
		
		return Time.calcSpan(now, this.birthday).getYear();
	}
	public int getAge()	 throws NullPointerException{
		age = calcAge(worldClock);
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
