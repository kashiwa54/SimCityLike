package application;

public enum SpecialTabs implements TabableEnum{
	COTTAGE("cottage",SpreadType.DOT,"小屋",ResidentalBuildingEnum.COTTAGE.getImagePass(),ResidentalBuildingEnum.COTTAGE.getCost());

	private String type;
	private SpreadType spread;
	private String display;
	private String imagePass;
	private int cost;

	private SpecialTabs(String type,SpreadType spread,String display,String imagePass,int cost)	{
		this.type = type;
		this.spread = spread;
		this.display = display;
		this.imagePass = imagePass;
		this.cost = cost;
	}

	public String getType()		{
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
