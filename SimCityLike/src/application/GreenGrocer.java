package application;


public class GreenGrocer extends CommercialBuilding{
	public GreenGrocer()	{
		this(null,0,0);
	}
	public GreenGrocer(Map map,int x,int y) {
		super(map,x,y, CommercialBuildingEnum.GREEN_GROCER);
	}
	@Override
	public TileObject copy() {
		return new GreenGrocer(this.getMap(),this.getX(),this.getY());
	}
	@Override
	public TileObject copy(int x, int y) {
		return new GreenGrocer(this.getMap(),x,y);
	}


}
