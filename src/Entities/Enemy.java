package Entities;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import Utilities.Vector2f;
import Utilities.Vector2i;

public class Enemy extends Entity
{
	private float damage;
	private Sound attackSound;
	private Sound hitSound;

	public Enemy(Vector2f movementSpeed, Vector2f pos, Image spriteSheet, Vector2i imgCount, float switchTime) throws SlickException
	{
		super(movementSpeed, pos, spriteSheet, imgCount, switchTime);
		
		attackSound = new Sound("");
		hitSound = new Sound("");
	}

	@Override
	public void update(float dt)
	{
		animation.update(dt);
	}
	
	@Override
	public void render(Graphics g)
	{
		g.drawImage(animation.getCurrentFrame(), pos.x, pos.y);
	}
	
	
	public void playAttackSound()
	{
		attackSound.play();
	}
	
	public void playHitSound()
	{
		hitSound.play();
	}
}
