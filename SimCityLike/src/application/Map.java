package application;

import java.util.ArrayList;
import java.util.LinkedList;

public class Map {
	private static final int DISTANCE = CommonConst.NEAR_ROAD_DISTANCE;
	private int width;
	private int height;
	private TileObject[][] tile;

	private PeopleManager pManager = null;
	private ProductManager proManager = null;
	private BuildingManager bManager = null;
	private RoadGraph graph = null;

	private ArrayList<Building> buildingList = new ArrayList<Building>();
	private ArrayList<Habitable> habitableList = new ArrayList<Habitable>();
	private ArrayList<Workable> workableList = new ArrayList<Workable>();

	private boolean changeFlg = false;

	public Map(int x,int y)	{
		this.width = x;
		this.height = y;
		tile = new TileObject[width][height];
		for(int i = 0;i < tile.length;i++)	{
			for(int j = 0;j < tile[i].length;j++)	{
				tile[i][j] = new Space(this,i,j);
			}
		}
		proManager = new ProductManager(this);
		TileObject.setProductManager(proManager);
	}
	public int getWidth()	{
		return this.width;
	}
	public int getHeight()	{
		return this.height;
	}

	public TileObject getTileObject(int x,int y)	{
		if((x < 0)||(y < 0)||(x >= getWidth())||(y >= getHeight()))	{
			return null;
		}else {
			return this.tile[x][y];
		}
	}
	public PeopleManager getPeopleManager()	{
		return this.pManager;
	}
	public ProductManager getProductManager()	{
		return proManager;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Building> getBuildingList()	{
		return (ArrayList<Building>) buildingList.clone();
	}
	@SuppressWarnings("unchecked")
	public ArrayList<Habitable> getHabitableList()	{
		return (ArrayList<Habitable>) habitableList.clone();
	}
	@SuppressWarnings("unchecked")
	public ArrayList<Workable> getWorkableList()	{
		return (ArrayList<Workable>) workableList.clone();
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
					putMap(obj,x,y);
					removeList(mapObj);
					return obj;
				}else {
					return mapObj;
				}
			}
			boolean canOnMap = true;
			for(int i = 0;i < obj.getWidth();i++)	{
				for(int j = 0; j< obj.getHeight();j++)	{
					if(!((tile[x + i][y + j] instanceof Overwritable)&&(isInside(x + i,y + j))))	{
						canOnMap = false;
						break;
					}
				}
			}
//			if (mapObj instanceof Overwritable)	{
//				if(isInside(x + obj.getWidth() - 1,y + obj.getHeight() - 1))	{
//					for(int i = 0;i < obj.getHeight();i++)	{
//						for(int j = 0;j < obj.getWidth();j++)	{
//							if(isInside(x + j,y + i)) {
//								tile[x + j][y + i] = obj;
//								obj.setOnMap(true);
//								//System.out.println("place in " + (x + j) + "," + (y + i));
//							}else {
//								System.out.println((x + j) + "," + (y + i) + " is out of map");
//							}
//						}
//					}
//				}else {
//					return mapObj;
//				}
//				return obj;
//			}
			if(canOnMap)	{
				putMap(obj,x,y);
				removeList(mapObj);
				return obj;
			}else {
				return mapObj;
			}
		}else {
			return null;
		}
	}

	private void putMap(TileObject obj,int x, int y)	{
		for(int i = 0;i < obj.getWidth();i++)	{
			for(int j = 0; j< obj.getHeight();j++)	{
				tile[x + i][y + j] = obj;
			}
		}
		obj.setOnMap(true);
		obj.setPosition(x,y);
		obj.place();
		addList(obj);
		refreshAround(x,y);
		changeFlg = true;
	}

	synchronized public void remove(int x,int y)	{
		int opX = tile[x][y].getX();
		int opY = tile[x][y].getY();
		int w = tile[x][y].getWidth();
		int h = tile[x][y].getHeight();
		for(int i = 0;i < w;i++)	{
			for(int j = 0;j < h;j++)	{
				clear(opX + i,opY + j);
				//System.out.println("remove " + (opX + i) + "," + (opY + j));
			}
		}
	}

	synchronized private void clear(int x,int y)	{
		tile[x][y].remove();
		refreshAround(x,y);
		if(tile[x][y] instanceof Habitable)	{
			Habitable home = (Habitable) tile[x][y];
			home.removeResidentAll();
		}
		tile[x][y].setOnMap(false);
		removeList(tile[x][y]);
		tile[x][y] = new Space(this,x,y);
		tile[x][y].setOnMap(true);
		changeFlg = true;
	}
	public void refreshAround(int x,int y)	{
		int tx = x - DISTANCE;
		int ty = y - DISTANCE;
		int bx = x + DISTANCE;
		int by = y + DISTANCE;

		if(tx < 0) tx = 0;
		if(ty < 0) ty = 0;
		if(bx >= width) bx = getWidth() - 1;
		if(by >= height) by = getHeight() - 1;
		for(int i = tx; i <= bx;i++)	{
			for(int j = ty; j <= by;j++)	{
				if(isInside(i,j))	{
					getTileObject(i,j).refresh();
				}
			}
		}
	}

	public boolean bindPeopleManager(PeopleManager pm)	{
		if(pm == null)	{
			return false;
		}else {
			pManager = pm;
			return true;
		}
	}
	public boolean bindBuildingManager(BuildingManager bm)	{
		if(bm == null)	{
			return false;
		}else {
			bManager = bm;
			return true;
		}
	}

	public RoadGraph createRoadGraph(Road road)	{
		graph = new RoadGraph(road);
		changeFlg = false;
		return graph;
	}


	public LinkedList<GraphNode> getRoute(Road start,Road end)	{
		if((graph == null)||(changeFlg))	{
			createRoadGraph(start);
		}
		return graph.calcPath(start, end);
	}

	public int getRouteCost(Road start,Road end)	{
		if((graph == null)||(changeFlg))	{
			createRoadGraph(start);
		}
		return graph.getRouteCost(start, end);
	}

	private void addList(TileObject obj)	{
		if(obj instanceof Building)	{
			buildingList.add((Building)obj);
			proManager.addBuildingList((Building)obj);
		}
		if(obj instanceof Habitable)	{
			habitableList.add((Habitable)obj);
		}
		if(obj instanceof Workable)	{
			workableList.add((Workable)obj);
		}
		if(obj instanceof Site)	{
			bManager.addFreeArea((Site)obj);
		}
	}
	private void removeList(TileObject obj)	{
		if(obj instanceof Building)	{
			buildingList.remove(obj);
			proManager.removeBuildingList((Building)obj);
		}
		if(obj instanceof Habitable)	{
			habitableList.remove(obj);
		}
		if(obj instanceof Workable)	{
			workableList.remove(obj);
		}
		if(obj instanceof Site)	{
			bManager.removeSite((Site)obj);
		}
	}
}
