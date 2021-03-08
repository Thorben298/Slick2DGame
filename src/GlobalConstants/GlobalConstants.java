package GlobalConstants;

public enum GlobalConstants
{
	TILE_SIZE(24);
	
	
	public int getValue() { return i; }
	
	private GlobalConstants(int i)
	{
		this.i = i;
	}

	private int i;
}
