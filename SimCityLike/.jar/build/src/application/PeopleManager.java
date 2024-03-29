package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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

	private ArrayList<People> peopleList = new ArrayList<People>(INISIAL_CAPACITY);
	private ArrayList<People> homelessList = new ArrayList<People>(INISIAL_CAPACITY);
	private List<ResidentalBuilding> residentalList = ResidentalBuilding.residentalList;
	private ArrayList<Habitable> vacantHomeList = null;
	private ArrayList<People> joblessList = new ArrayList<People>(INISIAL_CAPACITY);
	private List<CommercialBuilding> commercialList = CommercialBuilding.commercialList;
	private List<IndustrialBuilding> industrialList = IndustrialBuilding.industrialList;
	private ArrayList<Workable> vacantWorkspaceList = null;

	private double desireAverageCache = 0;
	private Time desireTimeCache = null;

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
		peopleList.add(p);
		homelessList.add(p);
		joblessList.add(p);
		return p;
	}
	public People birthPeople()	{
		Time birthday = worldClock.clone();
		String name = createName();
		People p = new People(this,birthday,worldClock,name);
		peopleList.add(p);
		homelessList.add(p);
		joblessList.add(p);
		return p;
	}
	@SuppressWarnings("unchecked")
	public List<People> getPeopleList()	{
		return (ArrayList<People>)peopleList.clone();
	}
	public List<People> getHomelessList()	{
		homelessList.trimToSize();
		return homelessList;
	}
	public void addHomelessList(People p)	{
		homelessList.add(p);
	}
	public List<People> getJoblessList()	{
		joblessList.trimToSize();
		return joblessList;
	}
	public double getDesireAverage()	{
		if((desireTimeCache == null)||(worldClock.getTime() - desireTimeCache.getTime() < 60))	{
			double ave = 0;
			int cnt = 0;
			for(People p : peopleList)	{
				if(p == null) continue;
				ave += p.getDesireAverage();
				cnt++;
			}
			if(cnt != 0)	{
				ave /= cnt;
			}else {
				ave = CommonConst.DESIRE_MAX;
			}
			desireTimeCache = worldClock.clone();
			desireAverageCache = ave;
			return ave;
		}else {
			return desireAverageCache;
		}

	}
	public void checkVacantHome()	{
		vacantHomeList = new ArrayList<Habitable>(INISIAL_CAPACITY);
		for(ResidentalBuilding rb : residentalList)	{
			if(rb.getFreeCapacity() > 0)	{
				vacantHomeList.add(rb);
			}
		}
	}
	public void checkVacantWorkspace()	{
		vacantWorkspaceList = new ArrayList<Workable>(INISIAL_CAPACITY);
		for(CommercialBuilding cb : commercialList)	{
			if(cb.getFreeWorkspace() > 0)	{
				vacantWorkspaceList.add(cb);
			}
		}
		for(IndustrialBuilding ib : industrialList)	{
			if(ib.getFreeWorkspace() > 0)	{
				vacantWorkspaceList.add(ib);
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
	public void remove(People p)	{
		p.removeHome();
		p.removeWork();
		homelessList.remove(p);
		joblessList.remove(p);
		peopleList.remove(p);
		System.out.println("remove");
	}
	public void migration(int residentalDemand)	{
		double population = getPeopleList().size();
		int increase = (int)((population / 100 * CommonConst.MIGRATION_FACTOR + rnd.nextInt(CommonConst.MIGRATION_EXTRA))
				 * ((double)(100 - residentalDemand) / 100));
		increase = (int)(increase * (getDesireAverage() / 100));
		for(int i = 0;i < increase;i++)	{
			createPeople();
		}
	}
	public void addJoblessList(People p)	{
		joblessList.add(p);
	}

	public boolean employAny(People p)	{
		if(p.getHome() == null) return false;
		Workable work = findNearJob(p.getHome(),CommonConst.WORK_DISTANCE,CommonConst.WORK_AREA_DISTANCE);
		if((work != null)&&(work.addWorker(p)))	{
			p.setWork(work);
			joblessList.remove(p);
			return true;
		}else {
			return false;
		}
	}

	public void jobSeek(int jobSeeker)	{
		checkVacantWorkspace();
		for(int i  = 0; i < jobSeeker; i++)	{
			if(joblessList.size() <= 0)	break;
			int index = rnd.nextInt(joblessList.size());
			employAny(joblessList.get(index));
		}
	}
	public Workable findNearJob(Habitable home,int distance,int areaDistance)	{
		int minX = Math.max(0, home.getX() - areaDistance);
		int maxX = Math.min(CommonConst.TILE_WIDTH, home.getX() + areaDistance);
		int minY = Math.max(0, home.getY() - areaDistance);
		int maxY = Math.min(CommonConst.TILE_WIDTH, home.getY() + areaDistance);
		ArrayList<Workable> nearWorkable = new ArrayList<Workable>();
		for(Workable w : vacantWorkspaceList)	{
			if((w.getX() >= minX)&&(w.getX() <= maxX)&&(w.getY() >= minY)&&(w.getY() <= maxY))	{
				nearWorkable.add(w);
			}
		}
		for(int i = 0;i < CommonConst.CHECK_WORKABLE_NUMBER;i++)	{
			if(nearWorkable.size() <= 0)	break;
			int rndIndex = rnd.nextInt(nearWorkable.size());
			Workable tmpWork = nearWorkable.get(rndIndex);
			if(tmpWork.getFreeWorkspace() > 0)	{
				if(fieldMap.getRouteCost(home.getNearRoad(),tmpWork.getNearRoad()) <= distance){
					return tmpWork;
				}
			}
		}
		return null;
	}

	public void decreaseDesireAll()	{
		for(People p : peopleList)	{
			if(p.getHome() != null)	p.moreDesire();
		}
	}

	public void randomCheck()	{
		int loop = CommonConst.PEOPLE_RETIRE_NUMBER + (int)(peopleList.size() * 0.05);
		for(int i = 0;i < loop;i++)	{
			int r = rnd.nextInt(peopleList.size());
			check(peopleList.get(r));
		}

	}
	public void check(People p)	{
		if(p.getDesireAverage() <= CommonConst.PEOPLE_RETIRE_FACTOR)	{
			p.addRetirePoint(1);
		}
		if(p.getHome() == null)	{
			p.addRetirePoint(1);
		}
		if(p.getWorkspace() == null)	{
			p.addRetirePoint(1);
		}
		if(p.getRetirePoint() >= CommonConst.PEOPLE_RETIRE_MAX)	{
			remove(p);
		}
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
