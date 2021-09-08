package application;

public interface PlacableEnum {
	public String getDisplayName();
	public Class<? extends TileObject> getObjectClass();
	public TileObject getObject();
	public TileObject getObject(Map map,int x,int y);
}
