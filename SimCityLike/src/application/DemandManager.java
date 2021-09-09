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
	
	private int homelessNumber = 0;
	private int population = 0;
	private double shoppingDemandAverage = 0;

	private List<ResidentalBuilding> residentalList = ResidentalBuilding.residentalList;
	private List<CommercialBuilding> commercialList = CommercialBuilding.commercialList;
	private List<IndustrialBuilding> industrialList = IndustrialBuilding.industrialList;
	
	private DemandBarChart<String,Number> bc = null;
	private PeopleManager pm = null;
	
	public DemandManager(DemandBarChart<String, Number> bc,PeopleManager pm)	{
		this.bc = bc;
		this.pm = pm;
	}
	public boolean update()	{
		boolean success = false;
		calcDemand();
		setDemandToChart(residentalDemand,commercialDemand,industrialDemand);
		success = true;
		return success;
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
	private void calcPeopleTally()	{
		population = pm.getPeopleList().size();
		homelessNumber = pm.getHomelessList().size();
		int shoppingSum = 0;
		for(People p : pm.getPeopleList())	{
			shoppingSum += p.getShoppingDemand();
		}
		if (population != 0)	{
			shoppingDemandAverage = shoppingSum / population;
		}else {
			shoppingDemandAverage = 0;
		}
	}
	private void calcDemand()	{
		calcPeopleTally();
		if(population == 0)	residentalDemand = 0;
		residentalDemand = (int) ((homelessNumber / population) * CommonConst.RESIDENTAL_DEMAND_FACTOR) * 100;
		if(residentalDemand > 100) residentalDemand = 100;
		
		commercialDemand = (int) (shoppingDemandAverage * CommonConst.COMMERCIAL_DEMAND_FACTOR);
		if(commercialDemand > 100) commercialDemand = 100;
		
		int underStockShopSum = 0;
		for(CommercialBuilding c : commercialList)	{
			double stockRate = c.getStock() / c.getProductCapacity() * 100;
			if(stockRate <= CommonConst.INDUSTRIAL_DEMAND_FACTOR)	{
				underStockShopSum++;
			}
		}
		industrialDemand = underStockShopSum / commercialList.size() * 2;
	}
	private void setDemandToChart(int rValue,int cValue,int iValue)	{
		bc.getData().get(0).getData().get(0).setYValue(rValue);
		bc.getData().get(1).getData().get(0).setYValue(cValue);
		bc.getData().get(2).getData().get(0).setYValue(iValue);
	}
}
