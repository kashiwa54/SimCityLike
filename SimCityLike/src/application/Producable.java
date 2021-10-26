package application;

import java.util.ArrayList;
import java.util.EnumSet;

public interface Producable {
	public EnumSet<Products> getProductSet();
	public void produce(Products product);
	public boolean receiveRequest(Products product,int amount,Consumable c);
	public boolean send(ProductPacket packet);
	public void setClientList(ArrayList<Consumable> list);
	public ArrayList<Consumable> getClientList();
}
