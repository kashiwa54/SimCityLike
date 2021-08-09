package application;

public interface PlacableEnum {
	public String getDisplayName();
	public Class<? extends TileObject> getObjectClass();
	public TileObject getObject();
	public TileObject getObject(int x,int y);
}
