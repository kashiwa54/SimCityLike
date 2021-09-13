package application;


public class SmallShopA extends CommercialBuilding{
	public SmallShopA(Map map,int x,int y) {
		super(map,x,y, CommercialBuildingEnum.SMALLSHOP_A);
	}
	@Override
	public TileObject copy() {
		return new SmallShopA(this.getMap(),this.getX(),this.getY());
	}
	@Override
	public TileObject copy(int x, int y) {
		return new SmallShopA(this.getMap(),x,y);
	}


}
