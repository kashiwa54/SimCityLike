package application;

import java.lang.reflect.InvocationTargetException;
public enum ResidentalBuildingEnum implements PlacableEnum{
	COTTAGE(Cottage.class,"src/image/cottage.png","小さな小屋",1,1,100,5,1000,1,1),
	OLD_2STORIES(Old2Stories.class,"src/image/2storyhouse.png","２階建ての家",1,1,200,12,2000,2,1);
	private Class<? extends TileObject> buildingClass;
	private String imagePath;
	private String displayName;
	private int width;
	private int height;
	private int cost;
	private int capacity;
	private int maintenanceCost;
	private int level;
	private int graphicSize;

	private ResidentalBuildingEnum(Class<? extends TileObject> buildingClass,String imagePath,String displayName,int width,int height,int cost,
			int capacity,int maintenanceCost,int level,int graphicSize)	{
		this.buildingClass = buildingClass;
		this.imagePath = imagePath;
		this.displayName = displayName;
		this.width = width;
		this.height = height;
		this.cost = cost;
		this.capacity = capacity;
		this.maintenanceCost = maintenanceCost;
		this.level = level;
		this.graphicSize = graphicSize;
	}
	public Class<? extends TileObject> getObjectClass()	{
		return buildingClass;
	}

	public String getImagePath()	{
		return imagePath;
	}
	@Override
	public String getDisplayName()	{
		return this.displayName;
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
	public int getMaintenanceCost()	{
		return maintenanceCost;
	}
	public int getLevel()	{
		return level;
	}
	public int getGraphicSize()	{
		return graphicSize;
	}
	public TileObject getObject()	{
		try {
			return buildingClass.newInstance();
		} catch (InstantiationException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return null;
	}
	public TileObject getObject(Map map,int x,int y)	{
		try {
			return buildingClass.getConstructor(Map.class,int.class,int.class).newInstance(map,x,y);
		} catch (NoSuchMethodException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return null;
	}
}
