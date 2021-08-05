package application;

import java.util.EnumSet;

public enum DirectionForImage {
	NONE,
	N,
	E,
	S,
	W,
	NE,
	NS,
	NW,
	ES,
	EW,
	SW,
	NES,
	NEW,
	NSW,
	ESW,
	NESW,
	NORTHEAST,
	SOUTHEAST,
	SOUTHWEST,
	NORTHWEST;

	private DirectionForImage()	{
	}

	public DirectionForImage setToDirectionForImage(EnumSet<Direction> set)	{
		if(set.contains(Direction.NORTH))	{
			if(set.contains(Direction.EAST))	{
				if(set.contains(Direction.SOUTH))	{
					if(set.contains(Direction.WEST))	{
						return DirectionForImage.NESW;
					}else {
						return DirectionForImage.NES;
					}
				}else {
					if(set.contains(Direction.WEST))	{
						return DirectionForImage.NEW;
					}else {
						return DirectionForImage.NE;
					}
				}
			}else {
				if(set.contains(Direction.SOUTH))	{
					if(set.contains(Direction.WEST))	{
						return DirectionForImage.NSW;
					}else {
						return DirectionForImage.NS;
					}
				}else {
					if(set.contains(Direction.WEST))	{
						return DirectionForImage.NW;
					}else {
						return DirectionForImage.N;
					}
				}
			}
		}else {
			if(set.contains(Direction.EAST))	{
				if(set.contains(Direction.SOUTH))	{
					if(set.contains(Direction.WEST))	{
						return DirectionForImage.ESW;
					}else {
						return DirectionForImage.ES;
					}
				}else {
					if(set.contains(Direction.WEST))	{
						return DirectionForImage.EW;
					}else {
						return DirectionForImage.E;
					}
				}
			}else {
				if(set.contains(Direction.SOUTH))	{
					if(set.contains(Direction.WEST))	{
						return DirectionForImage.SW;
					}else {
						return DirectionForImage.S;
					}
				}else {
					if(set.contains(Direction.WEST))	{
						return DirectionForImage.W;
					}else {
						return DirectionForImage.NONE;
					}
				}
			}
		}
	}
}
