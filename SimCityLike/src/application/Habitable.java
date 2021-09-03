package application;

public interface Habitable {
	boolean addResident(People p);
	boolean removeResident(People p);
	void removeResidentAll();
	int getFreeCapacity();
}
