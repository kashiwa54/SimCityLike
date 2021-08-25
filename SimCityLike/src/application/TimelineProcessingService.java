package application;

import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;

public class TimelineProcessingService extends ScheduledService<Boolean>{
	private int year = CommonConst.DEFAULT_YEAR;
	private Season season = CommonConst.DEFAULUT_SEASON;
	private Week week = CommonConst.DEFAULT_WEEK;
	private Time time = new Time(0);

	@Override
	protected Task<Boolean> createTask() {
		Task<Boolean> task = new Task<Boolean>(){

			@Override
			protected Boolean call() throws Exception {
				time.add(1);
				System.out.println(year + "年" + season.getJp() + week.getJp() + time.getHour() + ":" + time.getSecond());
				return true;
			}

		};
		return task;
	}
	public void setYear(int y)	{
		this.year = y;
	}
	public void setSeason(Season s)		{
		this.season = s;
	}
	public void setWeek(Week w)		{
		this.week = w;
	}
	public void setTime(Time t)		{
		this.time = t;
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
	public Time getTime()	{
		return this.time;
	}

}
