package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class PeopleManager {
	public static final int INISIAL_CAPACITY = CommonConst.PEOPLE_INISIAL_CAPACITY;
	public static final int INCREMENT_CAPACITY = CommonConst.PEOPLE_INCREMENT_CAPACITY;
	public static final int MAX_AGE = CommonConst.PEOPLE_MAX_AGE;
	public static final int MIN_AGE = CommonConst.PEOPLE_MIN_AGE;

	private Time worldClock = null;
	private Map fieldMap = null;

	private ArrayList<String> myoujiList = new ArrayList<String>(CommonConst.MYOUJI_INISIAL_CAPACITY);;

	private ArrayList<People> homelessList = new ArrayList<People>(INISIAL_CAPACITY);
	private List<ResidentalBuilding> residentalList = ResidentalBuilding.residentalList;
	private ArrayList<Habitable> vacantHomeList = null;
	private ArrayList<People> joblessList = new ArrayList<People>(INISIAL_CAPACITY);
	private List<CommercialBuilding> commercialList = CommercialBuilding.commercialList;
	private List<IndustrialBuilding> industrialList = IndustrialBuilding.industrialList;
	private ArrayList<Workable> vacantWorkspaceList = null;

	Random rnd = new Random();

	public PeopleManager(Time worldTime)	{
		this.worldClock = worldTime;
		readNameFile();
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
	public People createPeople()	{
		int age = rnd.nextInt(MAX_AGE - MIN_AGE + 1) + MIN_AGE;
		return createPeople(age);
	}
	public People createPeople(int age)	{
		Time birthday = worldClock.clone();
		birthday.backYear(age);
		String name = createName();
		People p = new People(this,birthday,worldClock,name);
		homelessList.add(p);
		return p;
	}
	public People birthPeople()	{
		Time birthday = worldClock.clone();
		String name = createName();
		People p = new People(this,birthday,worldClock,name);
		homelessList.add(p);
		return p;
	}
	public List<People> getPeopleList()	{
		@SuppressWarnings("unchecked")
		List<People> list = (ArrayList<People>) homelessList.clone();
		for(ResidentalBuilding r : residentalList)	{
			list.addAll(Arrays.asList(r.getResident()));
		}
		return list;

	}
	public void addHomelessList(People p)	{
		homelessList.add(p);
	}
	public void checkVacantHome()	{
		vacantHomeList = new ArrayList<Habitable>(INISIAL_CAPACITY);
		for(ResidentalBuilding rb : residentalList)	{
			if(rb.getFreeCapacity() > 0)	{
				vacantHomeList.add(rb);
			}
		}
	}
	public boolean moveIntoAny(People p)	{
		Random rnd = new Random();
		int size = vacantHomeList.size();
		if(size <= 0)	{
			return false;
		}
		Habitable home = vacantHomeList.get(rnd.nextInt(size));
		if(home.addResident(p))	{
			homelessList.remove(p);
			if(home.getFreeCapacity() <= 0)	{
				vacantHomeList.remove(home);
			}
			return true;
		}else {
			return false;
		}
	}
	public void allMoveInto() {
		@SuppressWarnings("unchecked")
		ArrayList<People> tmpList = ((ArrayList<People>)homelessList.clone());
		for(People p : tmpList) {
			if(!moveIntoAny(p))	{
				break;
			}
		}
	}

	public void addJoblessList(People p)	{
		joblessList.add(p);
	}
	private void readNameFile()	{
		BufferedReader myouji = null;
		try {
			myouji = new BufferedReader(new FileReader(CommonConst.MYOUJI_FILE_NAME));
			String buffer = null;
			while((buffer = myouji.readLine()) != null)	{
				myoujiList.add(buffer);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				myouji.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	private String createName()	{
		String name;
		name = myoujiList.get(rnd.nextInt(myoujiList.size()));

		return name;
	}

}
