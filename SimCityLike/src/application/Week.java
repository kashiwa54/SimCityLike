package application;

import java.util.HashMap;
import java.util.Map;

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
	private static Map<Integer,Week> weekMap = new HashMap<Integer,Week>(){{
		for(Week w : Week.values())	 put(w.index,w);
	}};

	private Week(int index,String japanese,String jp,Color color)	{
		this.index = index;
		this.japanese = japanese;
		this.jp = jp;
		this.color = color;

	}

	public static Week indexToWeek(int index)	{
		if (index < 0)	{
			index = -index + 7;
		}
		return weekMap.get(index);
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
