package application;

import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;

public class TimelineProcessingService extends ScheduledService<Boolean>{
	private Time time;
	private Time previousTime;
	private Map worldMap;
	private PeopleManager pm;
	private DemandManager dm;
	private BuildingManager bm;
	private ProductManager proMng;
	private Boolean switchHour = false;
	private Boolean switchNoon = false;
	private Boolean switchDay = false;
	private Boolean switchSeason = false;
	private Boolean switchYear = false;
	private TimeFlow timeFlow;
	public TimelineProcessingService(Time worldTime,Map map)	{
		this.time = worldTime;
		this.worldMap = map;
		this.pm = map.getPeopleManager();
		this.proMng = map.getProductManager();
	}
	public void setManagers(DemandManager dm,BuildingManager bm)	{
		this.dm = dm;
		this.bm = bm;
	}
	@Override
	protected Task<Boolean> createTask() {
		Task<Boolean> task = new Task<Boolean>(){

			@Override
			protected Boolean call() throws Exception {
				previousTime = time.clone();
				timeFlow = Main.getTimeFlow();
				double incrementSecond = CommonConst.DEFAULT_INCREMENT_SECOND;
				switch(timeFlow)	{
				case HIGH:
					incrementSecond *= 10;
					break;
				case MID:
					incrementSecond *= 3;
					break;
				case LOW:
					break;
				case PAUSE:
					incrementSecond = 0;
					break;
				default:
					break;

				}
				time.addSecond(incrementSecond);

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
					try	{
						bm.randomBuild();
						pm.decreaseDesireAll();
						pm.migration(dm.getResidentalDemand());
						pm.checkVacantHome();
						pm.allMoveInto();

						int jobSeeker = (pm.getJoblessList().size() / 2) + 1;
						pm.jobSeek(jobSeeker);

						proMng.produceAll();

						if((time.getHour() % 4 == 0)&&(dm != null))	dm.update();
					}catch(Exception e)	{
						e.printStackTrace();
					}

				}
				if(switchNoon)	{
					proMng.recalcList();
					bm.randomCheck();
					pm.randomCheck();
				}
				if(switchDay)	{
					proMng.stockingAll();
					proMng.resetTodayStock();
				}
				return true;
			}

		};
		return task;
	}
}
