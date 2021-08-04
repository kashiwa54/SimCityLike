package application;

import javafx.scene.paint.Color;

public enum SiteEnum {
	RESIDENTAL(Color.LIME,50),
	COMMERSIAL(Color.AQUA,50),
	INDUSTRIAL(Color.GOLD,50),
	REMOVE(Color.RED,0);

	private Color color;
	private int cost;

	private SiteEnum(Color color,int cost)	{
		this.color = color;
		this.cost = cost;
	}

	public Color getColor()	{
		return color;
	}
	public int getCost()	{
		return cost;
	}
}
