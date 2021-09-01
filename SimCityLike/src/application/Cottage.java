package application;


public class Cottage extends ResidentalBuilding{
	public Cottage(int x,int y) {
		super(x,y, ResidentalBuildingEnum.COTTAGE.getWidth(),ResidentalBuildingEnum.COTTAGE.getHeight(),ResidentalBuildingEnum.COTTAGE.getCapacity());
		type = ResidentalBuildingEnum.COTTAGE;
	}
	@Override
	public TileObject copy() {
		return new Cottage(this.getX(),this.getY());
	}
	@Override
	public TileObject copy(int x, int y) {
		return new Cottage(x,y);
	}


}
