package application;

public class Time {
	//0時0分からの経過時間
	private double second;
	private int time;
	private Week week;
	private Season season;
	private int year;
	public Time()	{
		this(0);
	}
	public Time(int time)	{
		this(0,time);
	}
	public Time(int hour,int minute)	{
		this(2000,Season.SPRING,Week.SUNDAY,hour,minute);
	}
	public Time(int year,Season season,Week week,int hour ,int minute)	{
		this.year = year;
		this.season = season;
		this.week = week;
		this.time = (hour * 60) + minute;
		formatTime(this);
	}
	synchronized public static void formatTime(Time time)	{
		int increaseDay = 0;
		int increaseSeason = 0;
		int increaseYear = 0;
		if((int)(time.getSecond() / 60) > 0)	{
			time.setTime(time.getTime() + (int)(time.getSecond() / 60));
			time.setSecond(time.getSecond() % 60);
		}
		if((time.getTime() / 60) >= 24)	{
			increaseDay = time.getTime() / (60 * 24);
			time.setTime(time.getTime() % 60 * 24);
		}else if(time.getTime() < 0)	{
			increaseDay = time.getTime() / (60 * 24) - 1;
			time.setTime((time.getTime() % 60 * 24) + (60 * 24));
		}
		increaseSeason = time.addWeek(increaseDay);
		increaseYear = time.addSeason(increaseSeason);
		time.addYear(increaseYear);
	}
	private void setTime(int time)	{
		this.time = time;
	}
	public void setTime(int hour,int minute)	{
		this.time = hour * 60 + minute;
		formatTime(this);
	}
	public void setHour(int hour)	{
		this.time = (hour * 60) + this.time % 60;
		formatTime(this);
	}
	public void setMinute(int minute)	{
		this.time = (this.time / 60) * 60 + minute;
		formatTime(this);
	}
	public void setSecond(double second)	{
		this.second = second;
		formatTime(this);
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
	public int getMinute()	{
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
	public double getSecond()	{
		return this.second;
	}
	public void add(int minute)	{
		this.time += minute;
		formatTime(this);
	}
	public void add(int hour,int minute)	{
		this.time += (hour * 60) + minute;
		formatTime(this);
	}
	public void back(int minute)	{
		this.time -= minute;
		formatTime(this);
	}
	public void back(int hour,int minute)	{
		this.time -= (hour * 60) + minute;
		formatTime(this);
	}
	public void addSecond(int add)	{
		this.second += add;
		formatTime(this);
	}
	public void backSecond(int back)	{
		this.second -= back;
		formatTime(this);
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
