package application;

import java.lang.reflect.InvocationTargetException;

import javafx.scene.paint.Color;

public enum WayEnum implements PlacableEnum{
	ROAD60(Road60.class,Color.GRAY,50);

	private Class<? extends TileObject> wayClass;
	private Color color;
	private int cost;

	private WayEnum(Class<? extends TileObject> siteClass,Color color,int cost)	{
		this.wayClass = siteClass;
		this.color = color;
		this.cost = cost;
	}

	public Color getColor()	{
		return color;
	}
	public int getCost()	{
		return cost;
	}

	@Override
	public Class<? extends TileObject> getObjectClass() {
		return wayClass;
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
