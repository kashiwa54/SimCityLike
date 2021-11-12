package application;


public abstract class Wood extends TileObject implements Overwritable{
	public Wood(Map map,int x,int y,WoodEnum we)	{
		super(map,x,y);
		type = we;
		setCanPass(true);
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
	public void maintenance()	{
	}

}
