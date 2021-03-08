package Tilemap;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import Utilities.Animation;
import Utilities.Vector2f;
import Utilities.Vector2i;

public class Tile
{
	private Animation animation;
	private Vector2i pos;
	private int size;
	
	// for flowfield
	private boolean blocked;
	private short cost;
	private short bestCost;
	private Vector2f bestDirection;
	
	private boolean isMine;
	
	public Tile(Animation animation, Vector2i pos, int size, boolean blocked)
	{
		this.animation = animation;
		this.pos = pos;
		this.size = size;
		
		// attributes for flowfield
		this.blocked = blocked;
		this.cost = 1;
		this.bestCost = 1000;
		this.bestDirection = new Vector2f(0, 0);
	
		this.isMine = false;
	}
	

	public void update(float dt)
	{
		animation.update(dt);
	}
	
	
	public void render(Graphics g, boolean showDebugInfo)
	{
		g.drawImage(animation.getCurrentFrame().getScaledCopy(size, size), pos.x * size, pos.y * size);
		
		if(showDebugInfo)
		{
			g.setColor(Color.yellow);
			
			if(bestCost >= 1000)
				g.drawString("-1", pos.x * size, pos.y * size);	
			else
				g.drawString(String.valueOf(bestCost), pos.x * size, pos.y * size);	
		}
	}

	public void resetFlowfieldVars()
	{
		cost = 1;
		bestCost = 1000;
		bestDirection = new Vector2f(0, 0);
	}
	
	
	
	public short getBestCost()
	{
		return bestCost;
	}

	public void setBestCost(short bestCost)
	{
		this.bestCost = bestCost;
	}

	public short getCost()
	{
		return cost;
	}
	
	public void setCost(short cost)
	{
		this.cost = cost;
	}

	public Vector2f getBestDirection()
	{
		return bestDirection;
	}

	public void setBestDirection(Vector2f bestDirection)
	{
		this.bestDirection = bestDirection;
	}

	public Vector2i getPos()
	{
		return pos;
	}

	public void setBlocked()
	{
		this.blocked = true;
	}
	
	public boolean isBlocked()
	{
		return blocked;
	}
	
	public boolean isMine() 
	{
		return isMine;
	}

	public void setMine() 
	{
		this.isMine = true;
	}
	
	
}
