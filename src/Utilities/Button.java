package Utilities;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;

public class Button
{
	private enum BTN_STATE
	{
		IDLE, HOVER, ACTIVE
	}
	
	private Rectangle rect;
	private String text;
	private Color color;
	private BTN_STATE btnState;
	private boolean fill;

	public Button(float x, float y, float width, float height, String text, Color color, boolean fillEnabled)
	{
		rect = new Rectangle(x, y, width, height);
		this.text = text;
		this.color = color;
		this.btnState = BTN_STATE.IDLE;
		this.fill = fillEnabled;
	}
	
	public boolean isPressed(Input input)
	{
		if(rect.contains(input.getMouseX(), input.getMouseY()))
		{
			this.btnState = BTN_STATE.HOVER;
			
			if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
			{
				this.btnState = BTN_STATE.ACTIVE;
				return true;
			}
		}
		else
		{
			this.btnState = BTN_STATE.IDLE;
			return false;
		}
		
		return false;
	}
	
	public void render(Graphics g)
	{
		if(btnState != BTN_STATE.HOVER)
		{
			g.setColor(color);
			
			if(fill)
				g.fill(rect);
			else
				g.draw(rect);
		}
		else if(btnState == BTN_STATE.HOVER)
		{
			g.setColor(new Color(30, 30, 30, 100));

			if(fill)
				g.fill(rect);
			else
				g.draw(rect);
		}
		
		g.setColor(color);
		g.drawString(text, rect.getX() + 10, rect.getY() + 10);
	}

}
