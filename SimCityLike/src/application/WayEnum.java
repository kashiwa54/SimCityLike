package application;

import java.lang.reflect.InvocationTargetException;

public enum WayEnum implements PlacableEnum{
	ROAD60(Road60.class,"src/image/road60set.png",50);

	private Class<? extends TileObject> wayClass;
	private String imageSetPath;
	private int cost;

	private WayEnum(Class<? extends TileObject> siteClass,String imageSetPath,int cost)	{
		this.wayClass = siteClass;
		this.imageSetPath = imageSetPath;
		this.cost = cost;
	}

	public String getImageSetPath()	{
		return this.imageSetPath;
	}
	public int getCost()	{
		return this.cost;
	}

	@Override
	public Class<? extends TileObject> getObjectClass() {
		return this.wayClass;
	}
	@Override
	public TileObject getObject()	{
		try {
			return wayClass.newInstance();
		} catch (InstantiationException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public TileObject getObject(int x,int y)	{
		try {
			return wayClass.getConstructor(int.class,int.class).newInstance(x,y);
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
