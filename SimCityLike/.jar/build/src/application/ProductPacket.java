package application;

import java.util.LinkedList;

public class ProductPacket {
	private Products product;
	private int amount;
	private Producable send;
	private Consumable to;

	private LinkedList<GraphNode> route;

	static private Map fieldMap;

	public ProductPacket(Products product,int amount,Producable send,Consumable to)	{
		this.product = product;
		this.amount = amount;
		this.send = send;
		this.to = to;
	}

	static void setMap(Map map)	{
		fieldMap = map;
	}

	public Products getProduct()	{
		return this.product;
	}

	public int getAmount()	{
		return this.amount;
	}

	public Producable getSender()	{
		return this.send;
	}

	public Consumable getReceiver()	{
		return this.to;
	}
}
