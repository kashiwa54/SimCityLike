package application;


public class FoodProcessingA extends IndustrialBuilding{
	public FoodProcessingA()	{
		this(null,0,0);
	}
	public FoodProcessingA(Map map,int x,int y) {
		super(map,x,y, IndustrialBuildingEnum.FOOD_PROCESSING_A);
	}
	@Override
	public TileObject copy() {
		return new FoodProcessingA(this.getMap(),this.getX(),this.getY());
	}
	@Override
	public TileObject copy(int x, int y) {
		return new FoodProcessingA(this.getMap(),x,y);
	}


}
