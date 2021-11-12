package application;

import java.lang.reflect.InvocationTargetException;
import java.util.EnumSet;
public enum CommercialBuildingEnum implements PlacableEnum{
	SMALLSHOP_A(SmallShopA.class,"image/smallshopA2.png","個人商店A",1,1,100,2,500,500,
			asSet(Products.AGRICULTURE,Products.FOOD_PROCESSING),1500,5000,1,1),
	GREEN_GROCER(GreenGrocer.class,"image/greengrocer.png","八百屋",1,1,150,3,500,750,
			asSet(Products.AGRICULTURE),1200,5000,1,1);
	private Class<? extends TileObject> buildingClass;
	private String imagePath;
	private String displayName;
	private int width;
	private int height;
	private int cost;
	private int workspace;
	private int productCapacity;
	private int maxConsumption;
	private EnumSet<Products> product;
	private int salary;
	private int maintenanceCost;
	private int level;
	private int graphicSize;


	private CommercialBuildingEnum(Class<? extends TileObject> buildingClass,String imagePath,String displayName,
			int width,int height,int cost,int workspace,int productCapacity,int maxConsumption,
			EnumSet<Products> product,int salary,int maintenanceCost,int level,int graphicSize)	{
		this.buildingClass = buildingClass;
		this.imagePath = imagePath;
		this.displayName = displayName;
		this.width = width;
		this.height = height;
		this.cost = cost;
		this.workspace = workspace;
		this.productCapacity = productCapacity;
		this.maxConsumption = maxConsumption;
		this.product = product;
		this.salary = salary;
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
	public int getWorkspace()	{
		return workspace;
	}
	public int getproductCapacity()	{
		return productCapacity;
	}
	public int getConsumption()	{
		return maxConsumption;
	}
	public EnumSet<Products> getConsumeSet()	{
		return product;
	}
	public int getSalary()	{
		return salary;
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

	private static EnumSet<Products> asSet(Products...products)	{
		EnumSet<Products> set = EnumSet.noneOf(Products.class);
		for(Products p : products)	{
			set.add(p);
		}
		return set;
	}
}
