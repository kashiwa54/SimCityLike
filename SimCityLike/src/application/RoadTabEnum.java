package application;

public enum RoadTabEnum implements TabableEnum{
	ROAD60(WayEnum.ROAD60,SpreadType.LINE,"道路(60km)","",60),
	ROAD80(WayEnum.ROAD60,SpreadType.LINE,"道路(80km)","",80),
	HIGHWAY100(WayEnum.ROAD60,SpreadType.LINE,"高速道路(100km)","",100),
	HIGHWAY120(WayEnum.ROAD60,SpreadType.LINE,"高速道路(120km)","",120);

	private PlacableEnum type;
	private SpreadType spread;
	private String display;
	private String imagePass;
	private int cost;

	private RoadTabEnum(PlacableEnum type,SpreadType spread,String display,String imagePass,int cost)	{
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
