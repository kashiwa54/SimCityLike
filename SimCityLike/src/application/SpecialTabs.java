package application;

public enum SpecialTabs implements TabableEnum{
	COTTAGE(ResidentalBuildingEnum.COTTAGE,SpreadType.DOT,"小屋","image/cottage.png",ResidentalBuildingEnum.COTTAGE.getCost()),
	SMALLSHOP_A(CommercialBuildingEnum.SMALLSHOP_A,SpreadType.DOT,"個人商店","image/smallshopA1.png",CommercialBuildingEnum.SMALLSHOP_A.getCost()),
	RICEFIELD(IndustrialBuildingEnum.RICEFIELD,SpreadType.DOT,"水田","image/ricefield.png",IndustrialBuildingEnum.RICEFIELD.getCost()),
	FOOD_PROCESSING_A(IndustrialBuildingEnum.FOOD_PROCESSING_A,SpreadType.DOT,"食品工場A","image/foodProcessingA.png",IndustrialBuildingEnum.FOOD_PROCESSING_A.getCost());


	private PlacableEnum type;
	private SpreadType spread;
	private String display;
	private String imagePass;
	private int cost;

	private SpecialTabs(PlacableEnum type,SpreadType spread,String display,String imagePass,int cost)	{
		this.type = type;
		this.spread = spread;
		this.display = display;
		this.imagePass = imagePass;
		this.cost = cost;
	}

	public PlacableEnum getType()		{
		return type;
	}
	public SpreadType getSpread()	{
		return spread;
	}
	public String getDisplay()	{
		return display;
	}
	public String getImagePath() {
		return imagePass;
	}
	public int getCost()	{
		return cost;
	}
}
