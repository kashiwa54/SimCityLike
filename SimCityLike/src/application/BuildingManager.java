package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BuildingManager {
	private Map fieldMap;
	private DemandManager dm;

	private ArrayList<Residental> rAreaList = new ArrayList<Residental>();
	private ArrayList<Commercial> cAreaList = new ArrayList<Commercial>();
	private ArrayList<Industrial> iAreaList = new ArrayList<Industrial>();

	private ArrayList<Residental> rFreeArea = new ArrayList<Residental>();
	private ArrayList<Commercial> cFreeArea = new ArrayList<Commercial>();
	private ArrayList<Industrial> iFreeArea = new ArrayList<Industrial>();

	private List<ResidentalBuilding> residentals = ResidentalBuilding.residentalList;
	private List<CommercialBuilding> commercials = CommercialBuilding.commercialList;
	private List<IndustrialBuilding> industrials = IndustrialBuilding.industrialList;

	Random rnd = new Random();
	public BuildingManager(Map map,DemandManager dm)	{
		this.fieldMap = map;
		this.dm = dm;
	}
	public void addFreeArea(Site site) {
		if(site instanceof Residental)	{
			rAreaList.add((Residental)site);
			rFreeArea.add((Residental)site);
		}
		if(site instanceof Commercial)	{
			cAreaList.add((Commercial)site);
			cFreeArea.add((Commercial)site);
		}
		if(site instanceof Industrial)	{
			iAreaList.add((Industrial)site);
			iFreeArea.add((Industrial)site);
		}
	}

	public boolean checkFreeArea(Site site)	{
		if((rFreeArea.contains(site))||(cFreeArea.contains(site))||(iFreeArea.contains(site)))	{
			return true;
		}else {
			return false;
		}
	}
	public void removeAreaList(Site site)	{
		rAreaList.remove(site);
		cAreaList.remove(site);
		iAreaList.remove(site);

		removeFreeArea(site);
	}

	public void removeFreeArea(Site site)	{
		rFreeArea.remove(site);
		cFreeArea.remove(site);
		iFreeArea.remove(site);
	}

	public boolean build(Site site)	{
		int buildingID = -1;
		if(site instanceof Residental)	{
			buildingID = rnd.nextInt(ResidentalBuildingEnum.values().length);
		}
		if(site instanceof Commercial)	{
			buildingID = rnd.nextInt(CommercialBuildingEnum.values().length);
		}
		if(site instanceof Industrial)	{
			buildingID = rnd.nextInt(IndustrialBuildingEnum.values().length);
		}
		try	{
			TileObject obj = null;
			if(site instanceof Residental)	{
				ResidentalBuildingEnum rbeArray[] = ResidentalBuildingEnum.values();
				obj = TileObject.getObject(rbeArray[buildingID].getObjectClass());
			}
			if(site instanceof Commercial)	{
				CommercialBuildingEnum cbeArray[] = CommercialBuildingEnum.values();
				obj = TileObject.getObject(cbeArray[buildingID].getObjectClass());
			}
			if(site instanceof Industrial)	{
				IndustrialBuildingEnum ibeArray[] = IndustrialBuildingEnum.values();
				obj = TileObject.getObject(ibeArray[buildingID].getObjectClass());
			}
			obj.setMap(fieldMap);
			fieldMap.place(obj, site.getX(), site.getY());
			return true;
		}catch(Exception e)	{
			e.printStackTrace();
			return false;
		}
	}

	public void randomBuild()	{
		int r = rnd.nextInt(CommonConst.BUILD_MAX_PAR_HOUR);
		int rSize = rFreeArea.size();
		int cSize = cFreeArea.size();
		int iSize = iFreeArea.size();
		int sumSize = rSize + cSize + iSize;

		if(sumSize <= 0)	return;

		int index = 0;

		for(int i = 0;i < r;i++)	{
			int buildingSite = -1;
			if(sumSize != 1)	{
				buildingSite = rnd.nextInt(sumSize);
			}else {
				buildingSite = 1;
			}
			int rate = rnd.nextInt(100);
			if(buildingSite < rSize)	{
				if(rate <= dm.getResidentalDemand())	{
					index = buildingSite;
					build(rFreeArea.get(index));
					rSize--;
					sumSize--;
				}
			}else if(buildingSite < rSize + cSize)	{
				if(rate <= dm.getCommercialDemand())	{
					index = buildingSite - rSize;
					build(cFreeArea.get(index));
					cSize--;
					sumSize--;
				}
			}else if(buildingSite < sumSize){
				if(rate <= dm.getIndustrialDemand())	{
					index = buildingSite - rSize - cSize;
					build(iFreeArea.get(index));
					iSize--;
					sumSize--;
				}
			}
		}
	}

	public void randomCheck()	{
		int loop = rnd.nextInt(CommonConst.BUILDING_CHECK_NUMBER);
		List<ResidentalBuilding> rList = ResidentalBuilding.residentalList;
		List<CommercialBuilding> cList = CommercialBuilding.commercialList;
		List<IndustrialBuilding> iList = IndustrialBuilding.industrialList;

		int rSize = rList.size();
		int cSize = cList.size();
		int iSize = iList.size();
		int sumSize = rSize + cSize + iSize;

		if(sumSize <= 1)	return;

		for(int i = 0;i < loop; i++)	{
			int r = rnd.nextInt(sumSize);
			int index = -1;
			if(r < rSize)	{
				index = r;
				check(rList.get(index));
			}else if(r < rSize + cSize)	{
				index = r - rSize;
				check(cList.get(index));
			}else {
				index = r - rSize - cSize;
				check(iList.get(index));
			}
		}
	}

	public void check(Building b)	{
		if(b instanceof ResidentalBuilding)	{
			ResidentalBuilding r = (ResidentalBuilding)b;
			if(r.getResident().length == 0)	{
				r.addRuinPoint(1);
			}
		}
		if(b instanceof CommercialBuilding)	{
			CommercialBuilding c = (CommercialBuilding)b;
			if((c.getClientList().size() == 0)||(c.getCustomerList().size() == 0))	{
				c.addRuinPoint(1);
			}
		}
		if(b instanceof IndustrialBuilding)	{
			IndustrialBuilding i = (IndustrialBuilding)b;
			if(i.getClientList().size() == 0)	{
				i.addRuinPoint(1);
			}
		}

		if(b.getRuinPoint() >= CommonConst.RUIN_POINT_MAX)	{
			fieldMap.remove(b.getX(), b.getY());
		}
	}
}
