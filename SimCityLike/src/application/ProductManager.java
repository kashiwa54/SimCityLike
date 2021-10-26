package application;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;

public class ProductManager {
	private static final int MAX_TRANSPORT_COST = CommonConst.PRODUCT_MAX_TRANSPORT_COST;

	private Map fieldMap;
	private ArrayList<Producable> producableList = new ArrayList<Producable>();
	private ArrayList<Consumable> consumableList = new ArrayList<Consumable>();
	private EnumMap<Products,ArrayList<Producable>> productMap;
	private EnumMap<Products,ArrayList<Consumable>> consumeMap;

	public ProductManager(Map map) {
		this.fieldMap = map;

		for(Building b : map.getBuildingList())	{
			if(b instanceof Producable)	{
				producableList.add((Producable)b);
			}
			if(b instanceof Consumable)	{
				consumableList.add((Consumable)b);
			}
		}

		productMap = new EnumMap<Products,ArrayList<Producable>>(Products.class) {{
			for(Products p : Products.values())	{
				put(p, new ArrayList<Producable>());
			}
			for(Producable p : producableList)	{
				for(Products product : p.getProductSet())	{
					get(product).add(p);
				}
			}
		}};

		consumeMap = new EnumMap<Products,ArrayList<Consumable>>(Products.class){{
			for(Products p : Products.values())	{
				put(p, new ArrayList<Consumable>());
			}
			for(Consumable c : consumableList)	{
				for(Products product : c.getConsumeSet())	{
					get(product).add(c);
				}
			}
		}};
	}

	public void addBuildingList(Building b)	{
		if(b instanceof Producable)	{
			Producable pro = (Producable)b;
			producableList.add(pro);
			for(Products p : pro.getProductSet())	{
				productMap.get(p).add(pro);
				pro.setClientList(calcClientList(pro));
				for(Consumable con : pro.getClientList())	{
					if(!con.getClientList().contains(pro)) con.getClientList().add(pro);
				}
			}
		}
		if(b instanceof Consumable)	{
			Consumable con = (Consumable)b;
			consumableList.add(con);
			for(Products p : con.getConsumeSet())	{
				consumeMap.get(p).add(con);
				con.setClientList(calcClientList(con));
				for(Producable pro : con.getClientList())	{
					if(!pro.getClientList().contains(con)) pro.getClientList().add(con);
				}
			}
		}
	}

	public void removeBuildingList(Building b)	{
		if(b instanceof Producable)	{
			Producable pro = (Producable)b;
			for(Products p : pro.getProductSet())	{
				productMap.get(p).remove(pro);
			}
		}
		if(b instanceof Consumable)	{
			Consumable con = (Consumable)b;
			for(Products p : con.getConsumeSet())	{
				consumeMap.get(p).remove(con);
			}
		}
	}
	public void produceAll()	{
		for(Producable p : producableList)	{
			for(Products pro : p.getProductSet())	{
				p.produce(pro);
			}
		}
	}
	public void stockingAll()	{
		for(Consumable c : consumableList)	{
			EnumSet<Products> set = c.getConsumeSet();
			try {
				for(Products pro : set)	{
					c.selectingImport(pro, c.getProductCapacity() - c.getStock(pro));
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	private ArrayList<Producable> calcClientList(Consumable c)	{
		ArrayList<Producable> list = new ArrayList<Producable>();
		EnumSet<Products> set = c.getConsumeSet();
		for(Products product : set)	{
			for(Producable p : productMap.get(product))	{
				if((p instanceof TileObject)&&(c instanceof TileObject))	{
					TileObject cObj = (TileObject)c;
					TileObject pObj = (TileObject)p;
					if(fieldMap.getRouteCost(cObj.getNearRoad(),pObj.getNearRoad()) < MAX_TRANSPORT_COST)	{
						if(!list.contains(p)) list.add(p);
					}
				}
			}
		}
		return list;
	}
	private ArrayList<Consumable> calcClientList(Producable p)	{
		ArrayList<Consumable> list = new ArrayList<Consumable>();
		EnumSet<Products> set = p.getProductSet();
		for(Products product : set)	{
			for(Consumable c : consumeMap.get(product))	{
				if((p instanceof TileObject)&&(c instanceof TileObject))	{
					TileObject cObj = (TileObject)c;
					TileObject pObj = (TileObject)p;
					if(fieldMap.getRouteCost(cObj.getNearRoad(),pObj.getNearRoad()) < MAX_TRANSPORT_COST)	{
						if(!list.contains(c)) list.add(c);
					}
				}
			}
		}
		return list;
	}
}
