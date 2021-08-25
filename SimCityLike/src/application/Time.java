package application;

public class Time {
	//0時0分からの経過時間
	private int time;
	private Week week;
	private Season season;
	private int year;
	public Time()	{
		this.time = 0;
	}
	public Time(int time)	{
		this.time = time;
	}
	public Time(int hour,int second)	{
		this(2000,Season.SPRING,Week.SUNDAY,hour,second);
	}
	public Time(int year,Season season,Week week,int hour ,int second)	{
		this.year = year;
		this.season = season;
		this.week = week;
		this.time = (hour * 60) + second;
	}
	synchronized public static void fomatTime(Time time)	{
		int elapsedDay = 0;
		int elapsedSeason = 0;
		int elapsedYear = 0;
		if((time.getTime() / 60) > 24)	{
			elapsedDay = time.getTime() / (60 * 24);
			time.setTime(time.getTime() % 60 * 24);
		}else if(time.getTime() < 0)	{
			elapsedDay = time.getTime() / (60 * 24) - 1;
			time.setTime((time.getTime() % 60 * 24) + (60 * 24));
		}
		elapsedSeason = time.addWeek(elapsedDay);
		elapsedYear = time.addSeason(elapsedSeason);
		time.addYear(elapsedYear);
	}
	private void setTime(int time)	{
		this.time = time;
	}
	public void setTime(int hour,int second)	{
		this.time = hour * 60 + second;
		fomatTime(this);
	}
	public void setHour(int hour)	{
		this.time = (hour * 60) + this.time % 60;
		fomatTime(this);
	}
	public void setSecond(int second)	{
		this.time = (this.time / 60) * 60 + second;
		fomatTime(this);
	}
	public void setYear(int year)	{
		this.year = year;
	}
	public void setSeason(Season season)	{
		this.season = season;
	}
	public void setWeek(Week week)	{
		this.week = week;
	}
	public int getTime()	{
		return this.time;
	}

	public int getHour()	{
		return this.time / 60;
	}
	public int getSecond()	{
		return this.time % 60;
	}
	public int getYear()	{
		return this.year;
	}
	public Season getSeason()	{
		return this.season;
	}
	public Week getWeek()	{
		return this.week;
	}
	public void add(int second)	{
		this.time += second;
		fomatTime(this);
	}
	public void add(int hour,int second)	{
		this.time += (hour * 60) + second;
		fomatTime(this);
	}
	public void back(int second)	{
		this.time -= second;
		fomatTime(this);
	}
	public void back(int hour,int second)	{
		this.time -= (hour * 60) + second;
		fomatTime(this);
	}
	synchronized public int addWeek(int add)	{
		if(add < 0)	{
			return backWeek(Math.abs(add));
		}
		int addSeason = (this.week.getIndex() + add) / 7;
		this.week = Week.indexToWeek((this.week.getIndex() + add) % 7);
		return addSeason;
	}
	synchronized public int backWeek(int back)	{
		if(back < 0)	{
			return addWeek(Math.abs(back));
		}
		int backDay = this.week.getIndex() - back;
		int backSeason = 0;
		if (backDay < 0)	{
			backSeason = (backDay / 7) - 1;
			this.week = Week.indexToWeek((backDay % 7) + 7);
		}else {
			this.week = Week.indexToWeek(backDay);
		}
		return backSeason;
	}
	synchronized public int addSeason(int add)	{
		if (add < 0)	{
			backSeason(Math.abs(add));
		}
		int addYear = 0;
		for(int i = 0;i < add;i++)	{
			this.season = this.season.nextSeason();
			if(this.season == Season.SPRING)	{
				addYear++;
			}
		}
		return addYear;
	}
	synchronized public int backSeason(int back)	{
		if (back < 0)	{
			backSeason(Math.abs(back));
		}
		int backYear = 0;
		for(int i = 0;i < back;i++)	{
			this.season = this.season.previousSeason();
			if(this.season == Season.WINTER)	{
				backYear--;
			}
		}
		return backYear;
	}

	public void addYear(int add)	{
		this.year += add;
	}
	public void backYear(int back)	{
		this.year -= back;
	}
}
