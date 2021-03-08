package Utilities;

import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

public class Animation
{
	private Vector2i currPos;
	private Vector2i imgCount;
	private int currIndex;
	private SpriteSheet spriteSheet;
	private int imgSize;
	private float switchTime;
	private float elapsedTime;
	
	private int startPos;
	private int endPos;
	private boolean oneFrame;
	
	// using this constructor every frame of a spritesheet will be displayed
	public Animation(Vector2i imageCount, Image spriteSheet, float switchTime, int offsetAtEnd)
	{
		this.imgCount = imageCount;
		this.spriteSheet = new SpriteSheet(spriteSheet, imageCount.x, imageCount.y);
		this.switchTime = switchTime;
		this.currPos = new Vector2i(0, 0);
		
		this.imgSize = this.spriteSheet.getWidth() / this.imgCount.x;
		
		this.startPos = 0;
		this.endPos = this.imgCount.x * this.imgCount.y - offsetAtEnd;
		this.currIndex = this.startPos;
		
		this.oneFrame = false;
	}
	
	
	// using this constructor only a small amount of frames (start -> start+numOfFrames) will be displayed
	public Animation(Vector2i imageCount, Image spriteSheet, float switchTime, int startPos, int numOfFrames)
	{
		this.imgCount = imageCount;
		this.spriteSheet = new SpriteSheet(spriteSheet, imageCount.x, imageCount.y);
		this.switchTime = switchTime;
		this.currPos = new Vector2i(0, 0);
		
		this.imgSize = this.spriteSheet.getWidth() / this.imgCount.x;
		
		this.startPos = startPos;
		this.endPos = this.startPos + numOfFrames;
		this.currIndex = this.startPos;
		
		// get x and y in sprite sheet
		currPos.x = currIndex % imgCount.x;
		currPos.y = currIndex / imgCount.x;
		
		if(this.startPos == this.endPos)
		{
			this.oneFrame = true;
		}
	}
	
	public void update(float dt)
	{
		if(!oneFrame)
		{
			elapsedTime += dt;
			
			if(elapsedTime > switchTime)
			{
				currIndex++;
				
				// get x and y in sprite sheet
				currPos.x = currIndex % imgCount.x;
				currPos.y = currIndex / imgCount.x;
				
				// if at end
				if(currIndex >= endPos)
				{
					currIndex = startPos;
					currPos.x = currIndex % imgCount.x;
					currPos.y = currIndex / imgCount.x;
				}
				
				elapsedTime = 0;
			}
		}
		
	}
	
	public Image getCurrentFrame()
	{
		return this.spriteSheet.getSubImage(currPos.x * imgSize, currPos.y * imgSize, imgSize, imgSize);
	}
	
}
