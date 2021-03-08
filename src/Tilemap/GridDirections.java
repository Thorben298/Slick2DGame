package Tilemap;

import Utilities.Vector2i;

public class GridDirections
{
	private Vector2i dir;
	
	private GridDirections(int x, int y)
	{
		dir = new Vector2i(x, y);
	}
	
	public Vector2i getDirection() { return this.dir; }
	
	private final static GridDirections NORTH 		= new GridDirections(0, -1);
	private final static GridDirections NORTH_WEST	= new GridDirections(-1, -1);
	private final static GridDirections NORTH_EAST 	= new GridDirections(1, -1);
	private final static GridDirections WEST 		= new GridDirections(-1, 0);
	private final static GridDirections EAST		= new GridDirections(1, 0);
	private final static GridDirections SOUTH 		= new GridDirections(0, 1);
	private final static GridDirections SOUTH_WEST 	= new GridDirections(-1, 1);
	private final static GridDirections SOUTH_EAST 	= new GridDirections(1, 1);
	
	public final static GridDirections[] cardinalDirections = { NORTH, EAST, SOUTH, WEST };
	public final static GridDirections[] allDirections = { NORTH, NORTH_EAST, EAST, SOUTH_EAST, SOUTH, SOUTH_WEST, WEST, NORTH_WEST };
	
	public static GridDirections[] getCardinalNeighbours() { return cardinalDirections; }
	public static GridDirections[] getAllNeighbours() { return allDirections; }
	
}
