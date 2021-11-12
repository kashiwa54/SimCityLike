package application;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;

public interface Habitable {
	People[] getResident();
	boolean addResident(People p);
	boolean removeResident(People p);
	void removeResidentAll();
	int getFreeCapacity();
	int getX();
	int getY();
	Road getNearRoad();
	void setSupplierListMap(EnumMap<Desire,ArrayList<Consumable>> listMap);
	EnumMap<Desire,ArrayList<Consumable>> getSupplierListMap();
	EnumSet<Desire> getDesireSet();
}
