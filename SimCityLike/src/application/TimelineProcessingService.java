package application;

import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;

public class TimelineProcessingService extends ScheduledService<Boolean>{
	private Time time = new Time(CommonConst.DEFAULT_YEAR,CommonConst.DEFAULUT_SEASON,CommonConst.DEFAULT_WEEK,0,0);

	@Override
	protected Task<Boolean> createTask() {
		Task<Boolean> task = new Task<Boolean>(){

			@Override
			protected Boolean call() throws Exception {
				time.addSecond(0.1);
				return true;
			}

		};
		return task;
	}
	public Time getTime()	{
		return this.time;
	}

}
