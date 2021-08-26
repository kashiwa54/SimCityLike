package application;

public class People {
	private int age;
	private Residental home;
	private Building workspace;
	private int money;
	private Time birthday;

	private static Time worldClock;

	public People(Time birthday)	{
		age = calcAge(birthday,worldClock);
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
	private int calcAge(Time birthday,Time now) throws NullPointerException {
		return 0;
	}
	public int getAge()	 throws NullPointerException{
		age = calcAge(this.birthday,worldClock);
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
