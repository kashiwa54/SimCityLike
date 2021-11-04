package application;

public class MoneyManager {
	private int playerMoney = Main.playerMoney;
	private Time worldClock = null;

	private int dayIncome = 0;
	private int dayExpenditure = 0;
	private int weekIncome = 0;
	private int weekExpenditure = 0;
	private int seasonIncome = 0;
	private int seasonExpenditure = 0;
	private int yearIncome = 0;
	private int yearExpenditure = 0;
	public MoneyManager(Time worldClock)	{
		this.worldClock = worldClock;
	}


}
