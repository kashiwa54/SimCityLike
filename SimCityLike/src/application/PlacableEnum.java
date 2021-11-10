package application;

public interface PlacableEnum {
	public String getDisplayName();
	public int getGraphicSize();			//64*64px = 1,128*128px = 2
	public Class<? extends TileObject> getObjectClass();
	public int getMaintenanceCost();
	public TileObject getObject();
	public TileObject getObject(Map map,int x,int y);
}
