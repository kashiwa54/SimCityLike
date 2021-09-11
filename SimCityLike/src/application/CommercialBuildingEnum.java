package application;

import java.lang.reflect.InvocationTargetException;
public enum CommercialBuildingEnum implements PlacableEnum{
	SMALLSHOP_A(Cottage.class,"src/image/shopA.png","個人商店A",1,1,100,2,50,5,5,1);
	private Class<? extends TileObject> buildingClass;
	private String imagePath;
	private String displayName;
	private int width;
	private int height;
	private int cost;
	private int workspace;
	private int productCapacity;
	private int consumption;
	private int customerCapacity;
	private int level;

	private CommercialBuildingEnum(Class<? extends TileObject> buildingClass,String imagePath,String displayName,
			int width,int height,int cost,int workspace,int productCapacity,int consumption,int customerCapacity,int level)	{
		this.buildingClass = buildingClass;
		this.imagePath = imagePath;
		this.displayName = displayName;
		this.width = width;
		this.height = height;
		this.cost = cost;
		this.workspace = workspace;
		this.productCapacity = productCapacity;
		this.consumption = consumption;
		this.customerCapacity = customerCapacity;
		this.level = level;
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
	public int getWorkspace()	{
		return workspace;
	}
	public int getproductCapacity()	{
		return productCapacity;
	}
	public int getConsumption()	{
		return consumption;
	}
	public int getCustomerCapacity()	{
		return customerCapacity;
	}
	public int getLevel()	{
		return level;
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
