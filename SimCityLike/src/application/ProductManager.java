package application;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;

public class ProductManager {
	private static final int MAX_TRANSPORT_COST = CommonConst.PRODUCT_MAX_TRANSPORT_COST;

	private Map fieldMap;
	private ArrayList<Habitable> habitableList = new ArrayList<Habitable>();
	private ArrayList<Producable> producableList = new ArrayList<Producable>();
	private ArrayList<Consumable> consumableList = new ArrayList<Consumable>();
	private EnumMap<Products,ArrayList<Producable>> productListMap;
	private EnumMap<Products,ArrayList<Consumable>> consumeListMap;
	private EnumMap<Desire,ArrayList<Habitable>> habitableDesireMap;

	public ProductManager(Map map) {
		this.fieldMap = map;

		for(Building b : map.getBuildingList())	{
			addBuildingList(b);
		}

		productListMap = new EnumMap<Products,ArrayList<Producable>>(Products.class) {{
			for(Products p : Products.values())	{
				put(p, new ArrayList<Producable>());
			}
			for(Producable p : producableList)	{
				for(Products product : p.getProductSet())	{
					get(product).add(p);
				}
			}
		}};

		consumeListMap = new EnumMap<Products,ArrayList<Consumable>>(Products.class){{
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
		if(b instanceof Habitable)	{
			Habitable hab = (Habitable)b;
			habitableList.add(hab);
			for(Desire d : hab.getDesireSet())	{
				habitableDesireMap.get(d).add(hab);
			}
			EnumMap<Desire,ArrayList<Consumable>> listMap = calcCustomerList(hab);
			hab.setSupplierListMap(listMap);
			for(Desire d : listMap.keySet())	{
				for(Consumable con : listMap.get(d)) {
					for(People p : hab.getResident())	{
						if(!con.getCustomerList().contains(p)) con.getCustomerList().add(p);
					}
				}
			}
		}
		if(b instanceof Producable)	{
			Producable pro = (Producable)b;
			producableList.add(pro);
			for(Products p : pro.getProductSet())	{
				productListMap.get(p).add(pro);
			}
			pro.setClientList(calcClientList(pro));
			for(Consumable con : pro.getClientList())	{
				if(!con.getClientList().contains(pro)) con.getClientList().add(pro);
			}
		}
		if(b instanceof Consumable)	{
			Consumable con = (Consumable)b;
			consumableList.add(con);
			for(Products p : con.getConsumeSet())	{
				consumeListMap.get(p).add(con);
			}
			con.setClientList(calcClientList(con));
			for(Producable pro : con.getClientList())	{
				if(!pro.getClientList().contains(con)) pro.getClientList().add(con);
			}

		}
	}

	public void removeBuildingList(Building b)	{
		if(b instanceof Producable)	{
			Producable pro = (Producable)b;
			for(Products p : pro.getProductSet())	{
				productListMap.get(p).remove(pro);
			}
		}
		if(b instanceof Consumable)	{
			Consumable con = (Consumable)b;
			for(Products p : con.getConsumeSet())	{
				consumeListMap.get(p).remove(con);
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
			for(Products pro : set)	{
				c.selectingImport(pro, c.getProductCapacity() - c.getStock(pro));
			}
		}
	}

	private ArrayList<Producable> calcClientList(Consumable c)	{
		ArrayList<Producable> list = new ArrayList<Producable>();
		EnumSet<Products> set = c.getConsumeSet();
		for(Products product : set)	{
			for(Producable p : productListMap.get(product))	{
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
			for(Consumable c : consumeListMap.get(product))	{
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
	private EnumMap<Desire,ArrayList<Consumable>> calcCustomerList(Habitable h)	{
		EnumSet<Desire> set = h.getDesireSet();
		EnumMap<Desire,ArrayList<Consumable>> listMap = new EnumMap<Desire,ArrayList<Consumable>>(Desire.class)	{{
				for(Desire d : set)	{
					put(d,new ArrayList<Consumable>());
				}
		}};
		for(Desire d : set)	{
			for(Products product : d.getProducts())	{
				for(Consumable c : consumeListMap.get(product))	{
					if((h instanceof TileObject)&&(c instanceof TileObject))	{
						TileObject cObj = (TileObject)c;
						TileObject hObj = (TileObject)h;
						if(fieldMap.getRouteCost(cObj.getNearRoad(),hObj.getNearRoad()) < MAX_TRANSPORT_COST)	{
							if(!listMap.get(d).contains(c)) listMap.get(d).add(c);
						}
					}
				}
			}
		}

		return listMap;
	}
}
