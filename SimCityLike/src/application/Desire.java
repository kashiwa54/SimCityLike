package application;

import java.util.EnumSet;

public enum Desire {
	FOOD("食品","食",25,asSet(Products.AGRICULTURE,Products.FISHERY,Products.STOCK_RAISING,Products.FOOD_PROCESSING)),
	APPAREL("服飾","服",5,asSet(Products.APPAREL)),
	FURNITURE("家具","家",1,asSet(Products.ELECTRONICS)),
	AMUSEMENT("娯楽","楽",10,asSet());

	private String japanese;
	private String jp;
	private int baseDecrease;
	private EnumSet<Products> products;

	private Desire(String japanese,String jp,int decrease,EnumSet<Products> products)	{
		this.japanese = japanese;
		this.jp = jp;
		this.baseDecrease = decrease;
		this.products = products;
	}

	public String getJapanase()	{
		return this.japanese;
	}
	public String getJp()	{
		return this.jp;
	}
	public int getBaseDecrease()	{
		return this.baseDecrease;
	}
	public EnumSet<Products> getProducts()	{
		return products;
	}

	public static EnumSet<Products> asSet(Products... products)	{
		EnumSet<Products> set = EnumSet.noneOf(Products.class);
		for(Products p : products)	{
			set.add(p);
		}
		return set;
	}
}
