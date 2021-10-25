package application;

import java.util.ArrayList;
import java.util.EnumSet;

public interface Consumable {
	public EnumSet<Products> getConsumeSet();
	public void consume();
	public boolean request(Producable to,Products product,int amount);
	public void receivePacket(ProductPacket packet);
	public void setClientList(ArrayList<Producable> list);
	public ArrayList<Producable> getClientList();
}
