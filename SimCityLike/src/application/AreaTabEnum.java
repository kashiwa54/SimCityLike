package application;

public enum AreaTabEnum implements TabableEnum{
	RESIDENTAL("residental",SpreadType.AREA,"住宅区","",SiteEnum.RESIDENTAL.getCost()),
	COMMERSIAL("commarcial",SpreadType.AREA,"商業区","",SiteEnum.COMMERSIAL.getCost()),
	INDUSTRIAL("industrial",SpreadType.AREA,"工業区","",SiteEnum.INDUSTRIAL.getCost());

	private String type;
	private SpreadType spread;
	private String display;
	private String imagePass;
	private int cost;

	private AreaTabEnum(String type,SpreadType spread,String display,String imagePass,int cost)	{
		this.type = type;
		this.spread = spread;
		this.display = display;
		this.imagePass = imagePass;
		this.cost = cost;
	}

	public String getType()		{
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
