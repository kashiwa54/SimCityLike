package application;

import java.util.EnumMap;

import javafx.scene.paint.Color;

public enum Week {
	SUNDAY(0,"日曜日","日",Color.RED),
	MONDAY(1,"月曜日","月",Color.BLACK),
	TUESDAY(2,"火曜日","火",Color.BLACK),
	WEDNESDAY(3,"水曜日","水",Color.BLACK),
	THURSDAY(4,"木曜日","木",Color.BLACK),
	FRIDAY(5,"金曜日","金",Color.BLACK),
	SATURDAY(6,"土曜日","土",Color.BLUE);

	private int index;
	private String japanese;
	private String jp;
	private Color color;
	private EnumMap<Week,Integer> weekMap = new EnumMap<Week,Integer>(Week.class){{
		for(Week w : Week.values())	{
			weekMap.put(w, w.index);
		}
	}};
	
	private Week(int index,String japanese,String jp,Color color)	{
		this.index = index;
		this.japanese = japanese;
		this.jp = jp;
		this.color = color;
	}

	public int weekToIndex()	{
		return weekMap.get(this);
	}
	public int getIndex()	{
		return this.index;
	}
	public String getJapanese()	{
		return this.japanese;
	}
	public String getJp()	{
		return this.jp;
	}
	public Color getColor()	{
		return this.color;
	}
}
