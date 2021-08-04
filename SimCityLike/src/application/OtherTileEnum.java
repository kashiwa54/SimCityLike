package application;

import java.lang.reflect.InvocationTargetException;


public enum OtherTileEnum implements PlacableEnum{
	DEFAULT(Space.class),
	REMOVE(Space.class),
	SPACE(Space.class);

	private Class<? extends TileObject> otherClass;

	private OtherTileEnum(Class<? extends TileObject> otherClass)	{
		this.otherClass = otherClass;
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
	public TileObject getObject(int x,int y)	{
		try {
			return otherClass.getConstructor(int.class,int.class).newInstance(x,y);
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
