package application;

import java.lang.reflect.InvocationTargetException;

import javafx.scene.paint.Color;

public enum SiteEnum implements PlacableEnum{
	RESIDENTAL(Residental.class,Color.LIME,50,"住宅区"),
	COMMERCIAL(Commercial.class,Color.AQUA,50,"商業区"),
	INDUSTRIAL(Industrial.class,Color.GOLD,50,"工業区");

	private Class<? extends TileObject> siteClass;
	private Color color;
	private int cost;
	private String displayName;

	private SiteEnum(Class<? extends TileObject> siteClass,Color color,int cost,String displayName)	{
		this.siteClass = siteClass;
		this.color = color;
		this.cost = cost;
		this.displayName = displayName;
	}

	public Color getColor()	{
		return color;
	}
	public int getCost()	{
		return cost;
	}
	@Override
	public String getDisplayName()	{
		return this.displayName;
	}

	@Override
	public Class<? extends TileObject> getObjectClass() {
		return siteClass;
	}
	@Override
	public TileObject getObject()	{
		try {
			return siteClass.newInstance();
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
	public TileObject getObject(Map map,int x,int y)	{
		try {
			return siteClass.getConstructor(Map.class,int.class,int.class).newInstance(map,x,y);
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
