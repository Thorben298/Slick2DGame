package Utilities;

public class Vector2i
{
	public int x, y;
	
	public Vector2i(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void add(Vector2i other)
	{
		this.x += other.x;
		this.y += other.y;
	}
	
	public void sub(Vector2i other)
	{
		this.x -= other.x;
		this.y -= other.y;
	}
	
	public void mult(Vector2i other)
	{
		this.x *= other.x;
		this.y *= other.y;
	}
	
	public void div(Vector2i other)
	{
		this.x /= other.x;
		this.y /= other.y;
	}
}
