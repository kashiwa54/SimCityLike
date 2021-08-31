package application;

import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;

public class TimelineProcessingService extends ScheduledService<Boolean>{
	private Time time;
	public TimelineProcessingService(Time worldTime)	{
		this.time = worldTime;
	}

	@Override
	protected Task<Boolean> createTask() {
		Task<Boolean> task = new Task<Boolean>(){

			@Override
			protected Boolean call() throws Exception {
				time.addSecond(CommonConst.DEFAULT_INCREMENT_SECOND);
				return true;
			}

		};
		return task;
	}
}
