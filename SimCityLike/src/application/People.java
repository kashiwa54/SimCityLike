package application;

public class People {
	private PeopleManager manager;
	private int age;
	private String name;
	private Habitable home;
	private Workable workspace;
	private int money;
	private Time birthday;

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
	public void removeHome()	{
		manager.addHomelessList(this);
		this.home = null;
	}
	public void removeWork()	{
		manager.addJoblessList(this);
		this.workspace = null;
	}
	@Override
	public String toString()	{
		return "name : " + name + "\n"
				+"age : " + age + "\n"
				+"birthday : " + birthday + "\n"
				+"home : " + home + "\n"
				+"workSpace : " + workspace + "\n"
				+"money : " + money + "\n";
	}
}
