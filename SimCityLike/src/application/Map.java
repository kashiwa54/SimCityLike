package application;

public class Map {
	private int width;
	private int height;
	private TileObject[][] tile;

	private PeopleManager pManager = null;

	public Map(int x,int y)	{
		this.width = x;
		this.height = y;
		tile = new TileObject[width][height];
		for(int i = 0;i < tile.length;i++)	{
			for(int j = 0;j < tile[i].length;j++)	{
				tile[i][j] = new Space(this,i,j);
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

	synchronized public TileObject place(TileObject obj,int x,int y)	{
		if (isInside(x,y))	{
			TileObject mapObj = getTileObject(x,y);
			if ((obj instanceof Way)&&(mapObj instanceof Way))	{
				Way w = (Way) obj;
				for(Direction d : w.getConnect())	{
					((Way) mapObj).connect(w.getConnectMap().get(d));
				}
			}
			if (mapObj instanceof Upgradable)	{
				tile[x][y] = ((Upgradable) mapObj).upgrade(obj);
				if(tile[x][y].equals(obj))	{
					obj.setOnMap(true);
					return obj;
				}else {
					return mapObj;
				}
			}
			if (mapObj instanceof Overwritable)	{
				if(isInside(x + obj.getWidth() - 1,y + obj.getHeight() - 1))	{
					for(int i = 0;i < obj.getHeight();i++)	{
						for(int j = 0;j < obj.getWidth();j++)	{
							if(isInside(x + j,y + i)) {
								tile[x + j][y + i] = obj;
								obj.setOnMap(true);
								//System.out.println("place in " + (x + j) + "," + (y + i));
							}else {
								System.out.println((x + j) + "," + (y + i) + " is out of map");
							}
						}
					}
				}else {
					return mapObj;
				}
				obj.setPosition(x,y);
				obj.place();
				obj.refresh();
				return obj;
			}
			return mapObj;
		}else {
			return null;
		}
	}

	synchronized public void remove(int x,int y)	{
		int opX = tile[x][y].getX();
		int opY = tile[x][y].getY();
		int w = tile[x][y].getWidth();
		int h = tile[x][y].getHeight();
		for(int i = 0;i < w;i++)	{
			for(int j = 0;j < h;j++)	{
				clear(opX + i,opY + j);
				System.out.println("remove " + (opX + i) + "," + (opY + j));
			}
		}
	}

	synchronized private void clear(int x,int y)	{
		tile[x][y].remove();
		if(tile[x][y] instanceof Habitable)	{
			Habitable home = (Habitable) tile[x][y];
			home.removeResidentAll();
		}
		tile[x][y].setOnMap(false);
		tile[x][y] = new Space(this,x,y);
		tile[x][y].setOnMap(true);
	}

	public boolean bindPeopleManager(PeopleManager pm)	{
		if(pm == null)	{
			return false;
		}else {
			pManager = pm;
			return true;
		}
	}
	public PeopleManager getPeopleManager()	{
		return this.pManager;
	}
}
