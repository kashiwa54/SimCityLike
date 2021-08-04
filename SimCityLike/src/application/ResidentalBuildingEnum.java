package application;

public enum ResidentalBuildingEnum {
	COTTAGE("image/cottage_64tile.png","residental",1,1,100,5,1);
	private String imagePass;
	private String attribute;
	private int width;
	private int height;
	private int cost;
	private int capacity;
	private int level;

	private ResidentalBuildingEnum(String imagePass,String attribute,int width,int height,int cost,int capacity,int level)	{
		this.imagePass = imagePass;
		this.attribute = attribute;
		this.width = width;
		this.height = height;
		this.cost = cost;
		this.capacity = capacity;
		this.level = level;
	}

	public String getImagePass()	{
		return imagePass;
	}
	public String getAttribute()	{
		return attribute;
	}
	public int getWidth()	{
		return width;
	}
	public int getHeight()	{
		return height;
	}
	public int getCost()	{
		return cost;
	}
	public int getCapacity()	{
		return capacity;
	}
	public int getLevel()	{
		return level;
	}
}
