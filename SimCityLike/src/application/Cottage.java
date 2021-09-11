package application;


public class Cottage extends ResidentalBuilding{
	public Cottage(Map map,int x,int y) {
		super(map,x,y, ResidentalBuildingEnum.COTTAGE);
	}
	@Override
	public TileObject copy() {
		return new Cottage(this.getMap(),this.getX(),this.getY());
	}
	@Override
	public TileObject copy(int x, int y) {
		return new Cottage(this.getMap(),x,y);
	}


}
