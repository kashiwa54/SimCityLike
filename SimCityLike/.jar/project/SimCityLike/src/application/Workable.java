package application;

public interface Workable {
	boolean addWorker(People p);
	boolean removeWorker(People p);
	void removeWorkerAll();
	int getFreeWorkspace();
	int getX();
	int getY();
	Road getNearRoad();
}
