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


}
