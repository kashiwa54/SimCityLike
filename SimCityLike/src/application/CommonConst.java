package application;


public class CommonConst {

	public static final int TILE_WIDTH = 32;
	public static final int TILE_HEIGHT = 32;
	public static final int TILESET_SIZE = 64;
	public static final int SHEAR_ANGLE = 45;
	public static final int TILE_SIZE = 64;
	public static final int CONSOLE_WIDTH = 640;
	public static final int CONSOLE_HEIGHT = 480;
	public static final int WINDOW_MAX_WIDTH = (int) Main.SYSTEM_WINDOW.getMaxX();
	public static final int WINDOW_MAX_HEIGHT = (int) Main.SYSTEM_WINDOW.getMaxY();
	public static final int WINDOW_PREF_WIDTH = 1280;
	public static final int WINDOW_PREF_HEIGHT = 960;
	public static final int MOVE_X_DEFAULT = WINDOW_MAX_WIDTH/2;
	public static final int MOVE_Y_DEFAULT = WINDOW_MAX_HEIGHT/2 - (TILE_HEIGHT * TILE_SIZE)/2;
	public static final double MOVE_RATE = 32;
	public static final double MAX_SIZE_RATE = 300;
	public static final double MIN_SIZE_RATE = 30;
	public static final double BUTTON_SIZE = 64.0;
	public static final double INFO_BAR_SIZE = 32.0;
	public static final double DEMAND_CHART_SIZE = 192.0;

	public static final int DEFAULT_YEAR = 2020;
	public static final Season DEFAULUT_SEASON = Season.SPRING;
	public static final Week DEFAULT_WEEK = Week.MONDAY;
	public static final int DEFAULT_DURATION = 100;
}
