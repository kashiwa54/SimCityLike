package application;

import javafx.scene.paint.Color;

public enum Season {
	SPRING("春",Color.PINK),
	SUMMER("夏",Color.LIGHTBLUE),
	AUTUMN("秋",Color.YELLOW),
	WINTER("冬",Color.LIGHTGRAY);

	private String jp;
	private Color color;
	private Season(String jp,Color color)	{
		this.jp = jp;
		this.color = color;
	}

	public String getJp()	{
		return jp;
	}
	public Color getColor()	{
		return color;
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
