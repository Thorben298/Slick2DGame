

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

import GlobalConstants.GlobalConstants;
import States.TestState;
import States.GameMenu;
import States.MainMenuState;

public class Main extends StateBasedGame 
{

	public Main() 
	{
		super("Hallo World");
	}

	public static void main(String[] args) throws SlickException 
	{
		AppGameContainer container = new AppGameContainer(new Main());
		DisplayMode dspMode = Display.getDesktopDisplayMode();
		container.setDisplayMode(32 * GlobalConstants.TILE_SIZE.getValue(), 32 * GlobalConstants.TILE_SIZE.getValue(), false);
		container.start();
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException 
	{
		addState(new MainMenuState());
		addState(new GameMenu());
		addState(new TestState());
	}

	
}
