package application;

public enum RoadTabEnum implements TabableEnum{
	ROAD60("road60",SpreadType.LINE,"道路(60km)","",60),
	ROAD80("road80",SpreadType.LINE,"道路(80km)","",80),
	HIGHWAY100("highway100",SpreadType.LINE,"高速道路(100km)","",100),
	HIGHWAY120("highway120",SpreadType.LINE,"高速道路(120km)","",120);

	private String type;
	private SpreadType spread;
	private String display;
	private String imagePass;
	private int cost;

	private RoadTabEnum(String type,SpreadType spread,String display,String imagePass,int cost)	{
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
