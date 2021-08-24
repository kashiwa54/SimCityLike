package application;

public class Time {
	//0時0分からの経過時間
	private int time;
	public Time()	{
		this.time = 0;
	}
	public Time(int time)	{
		this.time = time;
	}
	public Time(int hour,int second)	{
		this.time = (hour * 60) + second;
	}
	//24時間超過した回数、つまり経過日数を返す
	synchronized public static int TrimTime(Time time)	{
		int elapsedDay = 0;
		if((time.getTime() / 60) > 24)	{
			elapsedDay = time.getTime() / (60 * 24);
			time.setTime(time.getTime() % 60 * 24);
			return elapsedDay;
		}else if(time.getTime() < 0)	{
			elapsedDay = time.getTime() / (60 * 24) - 1;
			time.setTime((time.getTime() % 60 * 24) + (60 * 24));
		}
		return 0;
	}
	private void setTime(int time)	{
		this.time = time;
	}
	public void setTime(int hour,int second)	{
		this.time = hour * 60 + second;
		TrimTime(this);
	}
	public void setHour(int hour)	{
		this.time = (hour * 60) + this.time % 60;
		TrimTime(this);
	}
	public void setSecond(int second)	{
		this.time = (this.time / 60) * 60 + second;
		TrimTime(this);
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

	public int add(int second)	{
		this.time += second;
		return TrimTime(this);
	}
	public int add(int hour,int second)	{
		this.time += (hour * 60) + second;
		return TrimTime(this);
	}
	public int back(int second)	{
		this.time -= second;
		return TrimTime(this);
	}
	public int back(int hour,int second)	{
		this.time -= (hour * 60) + second;
		return TrimTime(this);
	}
}
