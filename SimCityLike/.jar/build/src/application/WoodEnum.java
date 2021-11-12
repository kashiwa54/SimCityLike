package application;

import java.lang.reflect.InvocationTargetException;


public enum WoodEnum implements PlacableEnum{
	WOOD_A("木","src/image/woodA.png",WoodA.class,1),
	WOOD_B("木","src/image/woodB.png",WoodB.class,1),
	WOOD_C("木","src/image/woodC.png",WoodC.class,1),
	WOODS_A("林","src/image/woodsA.png",WoodsA.class,1),
	WOODS_B("林","src/image/woodsB.png",WoodsB.class,1),
	WOODS_C("林","src/image/woodsC.png",WoodsB.class,1),
	WOODS_ABC("林","src/image/woodsABC.png",WoodsABC.class,1);

	private String displayName;
	private String imagePath;
	private Class<? extends TileObject> woodClass;
	private int graphicSize;

	private WoodEnum(String displayName,String imagePath, Class<? extends TileObject> woodClass,int graphicSize)	{
		this.displayName = displayName;
		this.imagePath = imagePath;
		this.woodClass = woodClass;
		this.graphicSize = graphicSize;
	}

	@Override
	public String getDisplayName()	{
		return this.displayName;
	}
	public int getMaintenanceCost()	{
		return 0;
	}
	public String getImagePath()	{
		return imagePath;
	}
	public int getGraphicSize()	{
		return graphicSize;
	}
	@Override
	public Class<? extends TileObject> getObjectClass() {
		return woodClass;
	}
	@Override
	public TileObject getObject()	{
		try {
			return woodClass.newInstance();
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
			return woodClass.getConstructor(Map.class,int.class,int.class).newInstance(map,x,y);
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
