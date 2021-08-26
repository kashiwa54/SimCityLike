package application;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.paint.Color;

public enum Season {
	SPRING("春",0,Color.DEEPPINK),
	SUMMER("夏",1,Color.BLUE),
	AUTUMN("秋",2,Color.DARKORANGE),
	WINTER("冬",3,Color.STEELBLUE);

	private String jp;
	private int index;
	private Color color;

	private static Map<Integer,Season> seasonMap = new HashMap<Integer,Season>(){{
		for(Season s : Season.values())	 put(s.index,s);
	}};
	private Season(String jp,int index,Color color)	{
		this.jp = jp;
		this.index = index;
		this.color = color;
	}

	public String getJp()	{
		return jp;
	}
	public int getIndex()	{
		return index;
	}
	public Color getColor()	{
		return color;
	}
	public static Season indexToSeason(int index)	{
		if (index < 0)	{
			index = -index + 4;
		}
		return seasonMap.get(index % 4);
	}
	public static Season nextSeason(Season season)	{
		switch(season)	{
		case SPRING :
			return SUMMER;
		case SUMMER :
			return AUTUMN;
		case AUTUMN :
			return WINTER;
		case WINTER :
			return SPRING;
		}
		return null;
	}

	public static Season previousSeason(Season season)	{
		switch(season)	{
		case SPRING :
			return WINTER;
		case SUMMER :
			return SPRING;
		case AUTUMN :
			return SUMMER;
		case WINTER :
			return AUTUMN;
		}
		return null;
	}

	public Season nextSeason()	{
		return nextSeason(this);
	}
	public Season previousSeason()	{
		return previousSeason(this);
	}
}
