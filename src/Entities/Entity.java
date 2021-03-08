package Entities;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import Utilities.*;

public abstract class Entity
{
	protected Utilities.Animation animation;
	protected float maxVel;
	protected Vector2f pos;
	protected Vector2f vel;
	
	public Entity(Vector2f movementSpeed, Vector2f pos, Image spriteSheet, Vector2i imgCount, float switchTime)
	{
		this.animation = new Utilities.Animation(imgCount, spriteSheet, switchTime, 0);
		this.maxVel = 2.0f;
		this.pos = pos;
	}
	
	public abstract void update(float dt);
	public abstract void render(Graphics g);
	
	
	protected void limitSpeed()
	{
		float realSpeed = (float)Math.sqrt(vel.x*vel.x + vel.y*vel.y);
		
		if(realSpeed > maxVel)
		{
			vel.x = (vel.x / realSpeed) * maxVel;
			vel.y = (vel.y / realSpeed) * maxVel;
		}
	}
}
