package application;

public enum AreaTabEnum implements TabableEnum{
	RESIDENTAL(SiteEnum.RESIDENTAL,SpreadType.AREA,"住宅区","",SiteEnum.RESIDENTAL.getCost()),
	COMMERSIAL(SiteEnum.COMMERCIAL,SpreadType.AREA,"商業区","",SiteEnum.COMMERCIAL.getCost()),
	INDUSTRIAL(SiteEnum.INDUSTRIAL,SpreadType.AREA,"工業区","",SiteEnum.INDUSTRIAL.getCost());

	private PlacableEnum type;
	private SpreadType spread;
	private String display;
	private String imagePass;
	private int cost;

	private AreaTabEnum(PlacableEnum type,SpreadType spread,String display,String imagePass,int cost)	{
		this.type = type;
		this.spread = spread;
		this.display = display;
		this.imagePass = imagePass;
		this.cost = cost;
	}

	public PlacableEnum getType()		{
		return type;
	}
	public SpreadType getSpread() {
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
