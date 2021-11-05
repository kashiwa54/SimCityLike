package application;

import java.util.LinkedList;

public class MoneyManager {
	private int playerMoney = Main.playerMoney;
	private Time worldClock = null;

	private int dayIncome = 0;
	private int dayExpenditure = 0;
	private int seasonIncome = 0;
	private int seasonExpenditure = 0;
	private int yearIncome = 0;
	private int yearExpenditure = 0;

	private LinkedList<DayStat> statistics = new LinkedList<DayStat>();

	public MoneyManager(Time worldClock)	{
		this.worldClock = worldClock;
	}

	public void income(int income)	{
		playerMoney += income;
		dayIncome += income;
		seasonIncome += income;
		yearIncome += income;
	}

	public void expenditure(int expenditure)	{
		playerMoney -= expenditure;
		dayExpenditure -= expenditure;
		seasonExpenditure -= expenditure;
		yearExpenditure -= expenditure;
	}

	public void resetDayStat()		{
		statistics.addFirst(new DayStat(worldClock.clone(),dayIncome,dayExpenditure));
		if(statistics.size() >= 7 * 4)	{
			statistics.removeLast();
		}
		if(worldClock.getWeek() == Week.SUNDAY)	{
			seasonIncome = 0;
			seasonExpenditure = 0;
			if(worldClock.getSeason() == Season.SPRING)	{
				yearIncome = 0;
				yearExpenditure = 0;
			}
		}

		dayIncome = 0;
		dayExpenditure = 0;
	}


}

class 	DayStat	{
	private Time date;
	private int income;
	private int expenditure;

	public DayStat(Time date,int income,int expenditure)	{
		this.date = date;
		this.income = income;
		this.expenditure = expenditure;
	}

	public void setDate(Time date)	{
		this.date = date;
	}
	public void setIncome(int income)	{
		this.income = income;
	}
	public void setExpenditure(int expenditure)	{
		this.expenditure = expenditure;
	}

	public Time getDate()	{
		return date;
	}
	public int getIncome()	{
		return income;
	}
	public int getExpenditure()	{
		return expenditure;
	}
}
