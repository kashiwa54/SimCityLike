package application;

public enum AreaTabEnum implements TabableEnum{
	RESIDENTAL(SiteEnum.RESIDENTAL,SpreadType.AREA,"住宅区","image/residental_icon.png",SiteEnum.RESIDENTAL.getCost()),
	COMMERSIAL(SiteEnum.COMMERCIAL,SpreadType.AREA,"商業区","image/commercial_icon.png",SiteEnum.COMMERCIAL.getCost()),
	INDUSTRIAL(SiteEnum.INDUSTRIAL,SpreadType.AREA,"工業区","image/industrial_icon.png",SiteEnum.INDUSTRIAL.getCost());

	private PlacableEnum type;
	private SpreadType spread;
	private String display;
	private String imagePath;
	private int cost;

	private AreaTabEnum(PlacableEnum type,SpreadType spread,String display,String imagePath,int cost)	{
		this.type = type;
		this.spread = spread;
		this.display = display;
		this.imagePath = imagePath;
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
		return imagePath;
	}
	public int getCost()	{
		return cost;
	}
}
