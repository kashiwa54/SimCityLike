package application;

public class Map {
	private int width;
	private int height;
	private TileObject[][] tile;

	public Map(int x,int y)	{
		this.width = x;
		this.height = y;
		tile = new TileObject[width][height];
		for(int i = 0;i < tile.length;i++)	{
			for(int j = 0;j < tile[i].length;j++)	{
				tile[i][j] = new Space(i,j);
			}
		}
	}
	public int getWidth()	{
		return this.width;
	}
	public int getHeight()	{
		return this.height;
	}

	public TileObject getTileObject(int x,int y)	{
		if((x < 0)||(y < 0)||(x > getWidth())||(y > getHeight()))	{
			return null;
		}else {
			return this.tile[x][y];
		}
	}

	public boolean isSpace(int x,int y)	{
		if (isInside(x,y))	{
			//return tile[pos.x][pos.y].getLastType().equals(":space");
			return tile[x][y] instanceof Space;
		}else {
			return false;
		}
	}


	public boolean isInside(int x,int y)	{
		if ((x < 0)||(y < 0)||(x >=  this.width)||(y >= this.height))	{
			return false;
		}else {
			return true;
		}
	}

	public boolean place(TileObject obj,int x,int y)	{
		if (isInside(x,y))	{
			if (isSpace(x,y))	{
				for(int i = 0;i < obj.getHeight();i++)	{
					for(int j = 0;j < obj.getWidth();j++)	{
						if(isInside(x + j,y + i)) {
							tile[x + j][y + i] = obj;
							obj.setOnMap(true);
							System.out.println("place in " + (x + j) + "," + (y + i));
						}else {
							System.out.println((x + j) + "," + (y + i) + " is out of map");
						}
					}
				}
				obj.setPosition(x,y);
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}
	}

	public void remove(int x,int y)	{
		int opX = tile[x][y].getX();
		int opY = tile[x][y].getY();
		int w = tile[x][y].getWidth();
		int h = tile[x][y].getHeight();
		for(int i = 0;i < h;i++)	{
			for(int j = 0;j < w;j++)	{
				clear(opX + j,opY + i);
				System.out.println("remove " + (opX + j) + "," + (opY + i));
			}
		}
	}

	private void clear(int x,int y)	{
		tile[x][y].setOnMap(false);
		tile[x][y] = new Space(x,y);
		tile[x][y].setOnMap(true);
	}
}
