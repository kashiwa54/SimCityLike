package application;

import java.lang.reflect.InvocationTargetException;
import java.util.EnumSet;
public enum CommercialBuildingEnum implements PlacableEnum{
	SMALLSHOP_A(SmallShopA.class,"src/image/smallshopA1.png","個人商店A",1,1,100,2,500,1000,
			asSet(Products.AGRICULTURE,Products.FOOD_PROCESSING),1);
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
	private int level;

	private CommercialBuildingEnum(Class<? extends TileObject> buildingClass,String imagePath,String displayName,
			int width,int height,int cost,int workspace,int productCapacity,int maxConsumption,
			EnumSet<Products> product,int level)	{
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
		return maxConsumption;
	}
	public EnumSet<Products> getConsumeSet()	{
		return product;
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

	private static EnumSet<Products> asSet(Products...products)	{
		EnumSet<Products> set = EnumSet.noneOf(Products.class);
		for(Products p : products)	{
			set.add(p);
		}
		return set;
	}
}
