package application;


public class RiceField extends IndustrialBuilding{
	public RiceField()	{
		this(null,0,0);
	}
	public RiceField(Map map,int x,int y) {
		super(map,x,y, IndustrialBuildingEnum.RICEFIELD);
	}
	@Override
	public TileObject copy() {
		return new RiceField(this.getMap(),this.getX(),this.getY());
	}
	@Override
	public TileObject copy(int x, int y) {
		return new RiceField(this.getMap(),x,y);
	}


}
