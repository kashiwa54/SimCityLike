package application;

public enum Direction {
	NORTH,
	EAST,
	SOUTH,
	WEST;

	public static Direction reverse(Direction d)	{
		switch(d)	{
		case NORTH :
			return SOUTH;
		case EAST :
			return WEST;
		case SOUTH :
			return NORTH;
		case WEST :
			return EAST;
		default:
			return null;
		}
	}
}
