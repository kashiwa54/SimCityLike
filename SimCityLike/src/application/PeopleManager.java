package application;

import java.util.ArrayList;
import java.util.List;

public class PeopleManager {
	public static final int INISIAL_CAPACITY = CommonConst.PEOPLE_INISIAL_CAPACITY;
	public static final int INCREMENT_CAPACITY = CommonConst.PEOPLE_INCREMENT_CAPACITY;

	private Time worldClock = null;
	private Map fieldMap = null;

	private ArrayList<People> homelessList = new ArrayList<People>(INISIAL_CAPACITY);
	private List<ResidentalBuilding> residentalList = ResidentalBuilding.residentalList;;

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

	public People createPeople(int age)	{
		Time birthday = worldClock.clone();
		birthday.backYear(age);
		People p = new People(birthday,worldClock);
		homelessList.add(p);
		return p;
	}
	public People birthPeople()	{
		Time birthday = worldClock.clone();
		People p = new People(birthday,worldClock);
		homelessList.add(p);
		return p;
	}

}
