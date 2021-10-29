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
	public static final double DEMAND_AXIS_MAX = 100.0;

	public static final double RESIDENTAL_DEMAND_FACTOR = 2.0;
	public static final double COMMERCIAL_DEMAND_FACTOR = 2.0;
	public static final double INDUSTRIAL_DEMAND_FACTOR = 1.0;

	public static final double MIGRATION_FACTOR = 5.0;
	public static final int MIGRATION_EXTRA = 5;

	public static final int NEAR_ROAD_DISTANCE = 3;

	public static final int PEOPLE_INISIAL_CAPACITY = 50;
	public static final int PEOPLE_INCREMENT_CAPACITY = 50;
	public static final int PEOPLE_MAX_AGE = 80;
	public static final int PEOPLE_MIN_AGE = 15;
	public static final int BUILDING_INISIAL_CAPACITY = 50;
	public static final int MYOUJI_INISIAL_CAPACITY = 100;

	public static final int DEFAULT_YEAR = 2020;
	public static final Season DEFAULUT_SEASON = Season.SPRING;
	public static final Week DEFAULT_WEEK = Week.MONDAY;
	public static final int DEFAULT_DURATION = 100;
	public static final double DEFAULT_INCREMENT_SECOND = 0.1;

	public static final String MYOUJI_FILE_NAME = "src/application/myouji.txt";

	public static final int WORK_DISTANCE = 10;
	public static final int WORK_AREA_DISTANCE = 8;
	public static final int CHECK_WORKABLE_NUMBER = 10;

	public static final int GRAPH_CASHE_SIZE = 255;

	public static final int PRODUCT_MAX_TRANSPORT_COST = 24;

	public static final double PRODUCT_REQUEST_FACTOR = 1 / 2;
	public static final int CLIENT_REQUEST_MAX_NUMBER = 15;

	public static final double DESIRE_MAX = 100.0;
}
