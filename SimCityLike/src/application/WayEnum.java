package application;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public enum WayEnum implements PlacableEnum{
	ROAD60(Road60.class,"src/image/road60set.png","道路(60km制限)",50,2);

	private Class<? extends TileObject> wayClass;
	private String imageSetPath;
	private String displayName;
	private int cost;
	private int level;

	private static final Map<Class<? extends TileObject>,WayEnum> classToEnum = new HashMap<Class<? extends TileObject>,WayEnum>()	{

	/**
		 *
		 */
		private static final long serialVersionUID = 8314162711755640833L;

	{
		for(WayEnum e : WayEnum.values()) put(e.getObjectClass(),e);
	}};

	private WayEnum(Class<? extends TileObject> siteClass,String imageSetPath,String displayName,int cost,int level)	{
		this.wayClass = siteClass;
		this.imageSetPath = imageSetPath;
		this.displayName = displayName;
		this.cost = cost;
		this.level = level;
	}

	public String getImageSetPath()	{
		return this.imageSetPath;
	}
	@Override
	public String getDisplayName()	{
		return this.displayName;
	}
	public int getCost()	{
		return this.cost;
	}
	public int getLevel()	{
		return this.level;
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
	public TileObject getObject(application.Map map,int x,int y)	{
		try {
			return wayClass.getConstructor(application.Map.class,int.class,int.class).newInstance(map,x,y);
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
	public boolean isGreater(int level)	{
		if(level > this.level)	{
			return true;
		}else {
			return false;
		}
	}
	public static WayEnum classToEnum(Class<? extends TileObject> T)	{
		return classToEnum.get(T);
	}

}
