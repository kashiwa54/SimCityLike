package application;


public class Old2Stories extends ResidentalBuilding{
	public Old2Stories()	{
		this(null,0,0);
	}
	public Old2Stories(Map map,int x,int y) {
		super(map,x,y, ResidentalBuildingEnum.OLD_2STORIES);
	}
	@Override
	public TileObject copy() {
		return new Old2Stories(this.getMap(),this.getX(),this.getY());
	}
	@Override
	public TileObject copy(int x, int y) {
		return new Old2Stories(this.getMap(),x,y);
	}


}
