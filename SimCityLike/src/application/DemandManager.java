package application;

import java.util.ArrayList;

public class DemandManager {
	private int freeResidentalSum = 0;
	private int freeCommercialSum = 0;
	private int freeIndustrialSum = 0;
	private int residentalDemand = 0;
	private int commercialDemand = 0;
	private int industrialDemand = 0;

	private ArrayList<ResidentalBuilding> residentalList = null;
	private ArrayList<CommercialBuilding> commercialList = null;
	private ArrayList<IndustrialBuilding> industrialList = null;
}
