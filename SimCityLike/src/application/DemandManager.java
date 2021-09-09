package application;

import java.util.List;

public class DemandManager {
	private int residentalDemand = 0;
	private int commercialDemand = 0;
	private int industrialDemand = 0;

	private int freeResidentalSum = 0;
	private int freeCommercialSum = 0;
	private int freeIndustrialSum = 0;
	private int shoppingDemandSum = 0;
	private int productDemandSum = 0;

	private List<ResidentalBuilding> residentalList = ResidentalBuilding.residentalList;
	private List<CommercialBuilding> commercialList = CommercialBuilding.commercialList;
	private List<IndustrialBuilding> industrialList = IndustrialBuilding.industrialList;
	
	private DemandBarChart<String,Integer> bc = null;
	private PeopleManager pm = null;
	
	public DemandManager(DemandBarChart bc,PeopleManager pm)	{
		this.bc = bc;
		this.pm = pm;
	}

	public int getResidentalDemand()	{
		return this.residentalDemand;
	}
	public int getCommercialDemand()	{
		return this.commercialDemand;
	}
	public int getIndustrialDemand()	{
		return this.industrialDemand;
	}
	
	private void calcFreeCapacitySum()	{
		int rSum = 0;
		int cSum = 0;
		int iSum = 0;
		for(ResidentalBuilding r : residentalList)	{
			rSum += r.getFreeCapacity();
		}
		for(CommercialBuilding c : commercialList)	{
			cSum += c.getFreeWorkspace();
		}
		for(IndustrialBuilding i : industrialList)	{
			iSum += i.getFreeWorkspace();
		}
		freeResidentalSum = rSum;
		freeCommercialSum = cSum;
		freeIndustrialSum = iSum;
	}
}
