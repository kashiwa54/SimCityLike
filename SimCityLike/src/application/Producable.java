package application;

import java.util.ArrayList;
import java.util.EnumSet;

public interface Producable {
	public EnumSet<Products> getProductSet();
	public void produce();
	public boolean receiveRequest(Products product,int amount);
	public boolean send(ProductPacket packet,Consumable to);
	public void setClientList(ArrayList<Consumable> list);
	public ArrayList<Consumable> getClientList();
}
