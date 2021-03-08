package States;

import java.io.IOException;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import GlobalConstants.GlobalConstants;
import Tilemap.Tilemap;
import Utilities.Animation;
import Utilities.Button;
import Utilities.Vector2i;

public class TestState extends BasicGameState
{
	private Tilemap tilemap;
	private Button exitBtn;
	private Animation world;
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException
	{
		this.exitBtn = new Button(10, 10, 30, 30, "", Color.red, true);
		//this.world = new Animation(new Vector2i(50, 1),new Image("res/Sprites/spinningPlanet.png"), 0.1f, 0);
		
		try
		{
			this.tilemap = new Tilemap(new Image("res/Sprites/World/spritesheet.png"), "res/Levels/TestRegion2/testRegion2.txt", 
					"res/Levels/TestRegion2/testRegion2Objs.txt", new Vector2i(28, 1), GlobalConstants.TILE_SIZE.getValue(), container.getWidth(), 0.8f);	
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}


	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException
	{
		Input input = container.getInput();
		float dt = (float) ((float)delta / 1000.0);
		
		tilemap.update(dt);
		//world.update(dt);

		
		if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON))
		{
			tilemap.printTileDataAt(input.getMouseX(), input.getMouseY());
		}
		
		if(exitBtn.isPressed(container.getInput()))
		{
			game.enterState(StateIDs.MAIN_MENU.getID());
		}
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException
	{
		tilemap.render(g, false);
		//g.drawImage(world.getCurrentFrame().getScaledCopy(4), 100, 100);
		exitBtn.render(g);
	}

	
	@Override
	public int getID()
	{
		return StateIDs.TEST_STATE.getID();
	}

}
