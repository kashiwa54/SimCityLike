package application;

import java.lang.reflect.InvocationTargetException;
import java.util.EnumSet;
public enum IndustrialBuildingEnum implements PlacableEnum{
	RICEFIELD(RiceField.class,"image/ricefield.png","水田",1,1,100,5,5000,250,5,asSet(Products.AGRICULTURE),1000,2000,1,1),
	FOOD_PROCESSING_A(FoodProcessingA.class,"image/foodProcessingA.png","食品加工工場A",2,2,500,20,10000,1000,5,
			asSet(Products.FOOD_PROCESSING),1500,5000,2,2);
	private Class<? extends TileObject> buildingClass;
	private String imagePath;
	private String displayName;
	private int width;
	private int height;
	private int cost;
	private int workspace;
	private int productCapacity;
	private int production;
	private int customerCapacity;
	private EnumSet<Products> product;
	private int salary;
	private int maintenanceCost;
	private int level;
	private int graphicSize;

	private IndustrialBuildingEnum(Class<? extends TileObject> buildingClass,String imagePath,String displayName,
			int width,int height,int cost,int workspace,int productCapacity,int production,int customerCapacity,
			EnumSet<Products> product,int salary,int maintenanceCost,int level,int graphicSize)	{
		this.buildingClass = buildingClass;
		this.imagePath = imagePath;
		this.displayName = displayName;
		this.width = width;
		this.height = height;
		this.cost = cost;
		this.workspace = workspace;
		this.productCapacity = productCapacity;
		this.production = production;
		this.customerCapacity = customerCapacity;
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
	public int getProduction()	{
		return production;
	}
	public int getCustomerCapacity()	{
		return customerCapacity;
	}
	public EnumSet<Products> getProductSet()	{
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
