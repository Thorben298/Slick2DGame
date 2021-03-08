package States;

import java.io.IOException;
import java.util.*;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import GlobalConstants.GlobalConstants;
import Tilemap.Tilemap;
import Utilities.*;

public class MainMenuState extends BasicGameState 
{
	private HashMap<String, Button> buttons;
	private Input input;
	private Tilemap background;
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException 
	{
		input = container.getInput();
		buttons = new HashMap<String, Button>();
		buttons.put("GameMenu", new Button(container.getWidth()/2 - container.getHeight()/5, container.getHeight()/2 + container.getHeight()/4, 150, 50, "Start Game", Color.white, false));
		buttons.put("Exit", new Button(container.getWidth()/2 + 35, container.getHeight()/2 + container.getHeight()/4, 150, 50, "Exit Game", Color.red, false));
		buttons.put("Test", new Button(container.getWidth()/2 - 80, container.getHeight()/2 + container.getHeight()/3, 150, 50, "Test", Color.cyan, false));
		
		try 
		{
			this.background = new Tilemap(new Image("res/Sprites/World/spritesheet.png"), "res/Levels/TestRegion/testRegion.txt", "res/Levels/TestRegion2/testRegion2Objs.txt", new Vector2i(28, 1), GlobalConstants.TILE_SIZE.getValue(), container.getWidth(), 0.8f);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException 
	{
		background.render(g, false);
		g.setColor(Color.white);
		g.drawString("Suuper cool Game", container.getWidth()/2-80, container.getHeight()/2);
		for (Button b : buttons.values())
		{
			b.render(g);
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException
	{
		float dt = (float) (delta / 1000.0);
		
		background.update(dt);
		
		if(buttons.get("GameMenu").isPressed(input))
		{
			game.enterState(StateIDs.GAME_MENU.getID());
		}
		if(buttons.get("Exit").isPressed(input))
		{
			container.exit();
		}
		if(buttons.get("Test").isPressed(input))
		{
			game.enterState(StateIDs.TEST_STATE.getID());
		}
	}
	

	@Override
	public int getID() 
	{
		return StateIDs.MAIN_MENU.getID();
	}

}
