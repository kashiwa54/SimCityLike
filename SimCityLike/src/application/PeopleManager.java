package application;

import java.util.ArrayList;

public class PeopleManager {
	public static final int INISIAL_CAPACITY = CommonConst.PEOPLE_INISIAL_CAPACITY;
	public static final int INCREMENT_CAPACITY = CommonConst.PEOPLE_INCREMENT_CAPACITY;

	private Time worldClock = null;
	private Map fieldMap = null;

	private ArrayList<People> homelessList = new ArrayList<People>(INISIAL_CAPACITY);
	private ArrayList<ResidentalBuilding> residentalList = new ArrayList<ResidentalBuilding>(INISIAL_CAPACITY);

	public PeopleManager(Time worldTime)	{
		this.worldClock = worldTime;
	}

	public boolean bindMap(Map map)	{
		if(map == null)	{
			return false;
		}else {
			this.fieldMap = map;
			if(map.bindPeopleManager(this))	{
				return true;
			}else {
				this.fieldMap = null;
				return false;
			}
		}
	}

}
