package application;

public interface Habitable {
	boolean addResident(People p);
	boolean removeResident(People p);
	int getFreeCapacity();
}
