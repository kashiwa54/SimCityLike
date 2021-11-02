package application;

import java.util.ArrayList;
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
			int buildingSite = rnd.nextInt(sumSize);
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
			}else{
				if(rate <= dm.getIndustrialDemand())	{
					index = buildingSite - rSize - cSize;
					build(iFreeArea.get(index));
					iSize--;
					sumSize--;
				}
			}
		}
	}

}
