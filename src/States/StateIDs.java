package States;

public enum StateIDs 
{
	MAIN_MENU(0),
	GAME_MENU(1),
	REGION_1(2),
	REGION_2(3),
	TEST_STATE(20);



	private StateIDs(int val)
	{
		this.val = val;
	}
	
	public int getID() { return this.val; }
	
	private int val;
}
