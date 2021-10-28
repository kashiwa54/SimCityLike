package application;

import java.util.ArrayList;
import java.util.EnumSet;

public interface Consumable {
	public EnumSet<Products> getConsumeSet();
	public int getProductCapacity();
	public int getStock(Products p);
	public int consume(Products product,int amount);
	public boolean selectingImport(Products product,int amount);
	public boolean request(Producable to,Products product,int amount);
	public boolean receivePacket(ProductPacket packet);
	public void setClientList(ArrayList<Producable> list);
	public ArrayList<Producable> getClientList();
	public void setCustomerList(ArrayList<People> list);
	public ArrayList<People> getCustomerList();
}
