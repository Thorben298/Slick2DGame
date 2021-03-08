package Tilemap;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import Utilities.Vector2f;
import Utilities.Vector2i;


public class FlowField
{
	public FlowField()
	{
	}
	
	public void createIntegrationField(ArrayList<Tile> tiles, Tile destinationTile, int tilesInRow)
	{
		resetField(tiles);
		
		if(!destinationTile.isBlocked())
		{
			destinationTile.setBestCost((short)0);
			destinationTile.setCost((short)0);
			
			Queue<Tile> tilesToCheck = new LinkedList<Tile>();
			tilesToCheck.add(destinationTile);
			
			while(tilesToCheck.size() > 0)
			{
				Tile currTile = tilesToCheck.poll();
				ArrayList<Tile> neighbours = getNeighboursTiles(tiles, GridDirections.cardinalDirections, currTile.getPos(), tilesInRow);
				
				for(Tile neighbour : neighbours)
				{
					if(neighbour.isBlocked()) { continue; }
					if(neighbour.getCost() + currTile.getBestCost() < neighbour.getBestCost())
					{
						neighbour.setBestCost((short) (neighbour.getCost() + currTile.getBestCost()));
						tilesToCheck.add(neighbour);
					}
				}
			}
			
			createVectorsOnFlowField(tiles, tilesInRow);
		}
	}
	
	public void createVectorsOnFlowField(ArrayList<Tile> tiles, int tilesInRow)
	{
		for(Tile t : tiles)
		{
			if(t.isBlocked()) { continue; }
			
			float bestCost = 1000;
			
			ArrayList<Tile> neighbours = getNeighboursTiles(tiles, GridDirections.allDirections, t.getPos(), tilesInRow);
			
			for(Tile neighbour : neighbours)
			{
				if(neighbour.getBestCost() < bestCost)
				{
					t.setBestDirection(createVector(t.getPos(), neighbour.getPos()));
					bestCost = neighbour.getBestCost();
				}
			}
		}
	}
	
	private void resetField(ArrayList<Tile> tiles)
	{
		for(Tile t : tiles)
		{
			t.resetFlowfieldVars();
		}
	}
	
	private ArrayList<Tile> getNeighboursTiles(ArrayList<Tile> tiles, GridDirections[] dirs, Vector2i originalPos, int tilesInRow)
	{
		ArrayList<Tile> neighbours = new ArrayList<Tile>();
		
		for(int i = 0; i < dirs.length; ++i)
		{
			Tile t = getNeighbourTile(tiles, originalPos, dirs[i].getDirection(), tilesInRow);
			
			if(t != null)
			{
				neighbours.add(t);
			}
		}
		
		return neighbours;
	}
	
	private Tile getNeighbourTile(ArrayList<Tile> tiles, Vector2i originalPos, Vector2i dir, int tilesInRow)
	{
		Vector2i otherPos = new Vector2i(originalPos.x, originalPos.y);
		otherPos.x += dir.x;
		otherPos.y += dir.y;
		
		if(otherPos.x < 0 || otherPos.x > tilesInRow || otherPos.y < 0 || otherPos.y > tilesInRow)
			return null;
		
		return tiles.get(Tilemap.coordinatesToIndex(otherPos, tilesInRow));
	}
	
	private Vector2f createVector(Vector2i from, Vector2i to)
	{
		return new Vector2f(to.x - from.x, to.y - from.y);
	}
	
	private float getDistance(Vector2i pos1, Vector2i pos2)
	{
		int diffX = Math.abs(pos2.x - pos1.x);
		int diffY = Math.abs(pos2.y - pos1.y);
		
		return (float) Math.sqrt(diffX*diffX + diffY*diffY);
	}
	
	
}
