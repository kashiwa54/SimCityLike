package application;

import java.lang.reflect.InvocationTargetException;


public enum OtherTileEnum implements PlacableEnum{
	DEFAULT("",Space.class),
	REMOVE("",Space.class),
	INFO("",Space.class),
	SPACE("空き地",Space.class);

	private String displayName;
	private Class<? extends TileObject> otherClass;

	private OtherTileEnum(String displayName, Class<? extends TileObject> otherClass)	{
		this.displayName = displayName;
		this.otherClass = otherClass;
	}

	@Override
	public String getDisplayName()	{
		return this.displayName;
	}
	@Override
	public Class<? extends TileObject> getObjectClass() {
		return otherClass;
	}
	@Override
	public TileObject getObject()	{
		try {
			return otherClass.newInstance();
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
			return otherClass.getConstructor(Map.class,int.class,int.class).newInstance(map,x,y);
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
