package application;

import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;

public class TimelineProcessingService extends ScheduledService<Boolean>{
	private Time time;
	private Time previousTime;
	private Map worldMap;
	private PeopleManager pm;
	private DemandManager dm;
	private Boolean switchHour = false;
	private Boolean switchNoon = false;
	private Boolean switchDay = false;
	private Boolean switchSeason = false;
	private Boolean switchYear = false;
	public TimelineProcessingService(Time worldTime,Map map)	{
		this.time = worldTime;
		this.worldMap = map;
		this.pm = map.getPeopleManager();
	}
	public void setDemandManager(DemandManager dm)	{
		this.dm = dm;
	}
	@Override
	protected Task<Boolean> createTask() {
		Task<Boolean> task = new Task<Boolean>(){

			@Override
			protected Boolean call() throws Exception {
				previousTime = time.clone();
				time.addSecond(CommonConst.DEFAULT_INCREMENT_SECOND);

				if(previousTime.getHour() != time.getHour())	{
					switchHour = true;
					if(time.getHour() % 12 == 0)	{
						switchNoon = true;
					}else {
					}
					if(previousTime.getWeek() != time.getWeek())	{
						switchDay = true;
						if(previousTime.getSeason() != time.getSeason())	{
							switchSeason = true;
							if(previousTime.getYear() != time.getYear())	{
								switchYear = true;
							}
						}
					}
				}else {
					switchHour = false;
					switchNoon = false;
					switchDay = false;
					switchSeason = false;
					switchYear = false;
				}
				if(switchHour)	{
					pm.migration(dm.getResidentalDemand());
					pm.checkVacantHome();
					pm.allMoveInto();
					if (dm != null) dm.update();
				}
				if(switchNoon)	{
				}
				return true;
			}

		};
		return task;
	}
}
