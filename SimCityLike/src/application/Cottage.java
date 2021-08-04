package application;


public class Cottage extends ResidentalBuilding{
	public Cottage(int x,int y) {
		super(x,y, ResidentalBuildingEnum.COTTAGE.getWidth(),ResidentalBuildingEnum.COTTAGE.getHeight(),ResidentalBuildingEnum.COTTAGE.getCapacity());
		type.concat(":cottage");
	}
	@Override
	public boolean place() {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}
	@Override
	public void remove() {
		// TODO 自動生成されたメソッド・スタブ

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
