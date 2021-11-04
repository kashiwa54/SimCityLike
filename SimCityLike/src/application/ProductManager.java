package application;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Random;

public class ProductManager {
	private static final int MAX_TRANSPORT_COST = CommonConst.PRODUCT_MAX_TRANSPORT_COST;

	private Map fieldMap;
	private ArrayList<Habitable> habitableList = new ArrayList<Habitable>();
	private ArrayList<Producable> producableList = new ArrayList<Producable>();
	private ArrayList<Consumable> consumableList = new ArrayList<Consumable>();
	private EnumMap<Products,ArrayList<Producable>> productListMap;
	private EnumMap<Products,ArrayList<Consumable>> consumeListMap;
	private EnumMap<Desire,ArrayList<Habitable>> habitableDesireMap;
	private Random rnd = new Random();

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

		habitableDesireMap = new EnumMap<Desire,ArrayList<Habitable>>(Desire.class)	{{
			for(Desire d : Desire.values())	{
				put(d,new ArrayList<Habitable>());
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
						if(p == null) continue;
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
		if(b instanceof Habitable)	{
			Habitable hab = (Habitable)b;
			for(Desire d : hab.getDesireSet())	{
				habitableDesireMap.get(d).remove(hab);

				for(Consumable c : hab.getSupplierListMap().get(d))	{
					for(People p : hab.getResident())	{
						c.getCustomerList().remove(p);
						p.getSupplierList(d).remove(c);
					}
				}
				hab.getSupplierListMap().get(d).clear();
			}
		}
		if(b instanceof Producable)	{
			Producable pro = (Producable)b;
			for(Products p : pro.getProductSet())	{
				productListMap.get(p).remove(pro);

				for(Consumable c : pro.getClientList())	{
					c.getClientList().remove(pro);
				}
				pro.getClientList().clear();
			}
		}
		if(b instanceof Consumable)	{
			Consumable con = (Consumable)b;
			for(Products p : con.getConsumeSet())	{
				consumeListMap.get(p).remove(con);

				for(Producable pro : con.getClientList())	{
					pro.getClientList().remove(con);
				}
				con.getCustomerList().clear();
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
						if(list.contains(p)) continue;
						list.add(p);
						ArrayList<Consumable> plist = p.getClientList();
						if((plist != null)&&(!plist.contains(c)))	{
							plist.add(c);
						}
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
						if(list.contains(c)) continue;
						list.add(c);
						ArrayList<Producable> clist = c.getClientList();
						if((clist != null)&&(!clist.contains(p)))	{
							clist.add(p);
						}
					}
				}
			}
		}
		return list;
	}
	public EnumMap<Desire,ArrayList<Consumable>> calcCustomerList(Habitable h)	{
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
							if(listMap.get(d).contains(c)) continue;
							listMap.get(d).add(c);
							ArrayList<People> clist = c.getCustomerList();
							if(clist != null)	{
								for(People p : h.getResident())	{
									if((p != null)&&(!clist.contains(p)))	{
										clist.add(p);
									}
								}
							}
						}
					}
				}
			}
		}

		return listMap;
	}

	public void recalcList()	{
		int loop = CommonConst.RECALC_NUMBER + (int)(producableList.size() * 0.05);
		if(producableList.size() > 1)	{
			for(int i = 0; i < loop;i++)	{
				int r = rnd.nextInt(producableList.size());
				producableList.get(r).setClientList(calcClientList(producableList.get(r)));
			}
		}
		loop = CommonConst.RECALC_NUMBER + (int)(consumableList.size() * 0.05);
		if(consumableList.size() > 1)	{
			for(int i = 0; i < loop;i++)	{
				int r = rnd.nextInt(consumableList.size());
				consumableList.get(r).setClientList(calcClientList(consumableList.get(r)));
			}
		}
		loop = CommonConst.RECALC_NUMBER + (int)(habitableList.size() * 0.05);
		if(habitableList.size() > 1)	{
			for(int i = 0; i < loop;i++)	{
				int r = rnd.nextInt(habitableList.size());
				habitableList.get(r).setSupplierListMap(calcCustomerList(habitableList.get(r)));

			}
		}
	}
}
