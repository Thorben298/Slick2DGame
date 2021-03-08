package Tilemap;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import Utilities.Animation;
import Utilities.Vector2f;
import Utilities.Vector2i;

public class Tilemap
{
	// second layer objs class
	private class ObjectOnMap
	{
		private Vector2i pos;
		private Image img;
		
		public ObjectOnMap(Vector2i pos, Image img, boolean isMine) 
		{
			this.pos = pos;
			this.img = img;
			
			for(int y = pos.y; y < pos.y + this.img.getHeight() / 32; ++y)
			{
				for(int x = pos.x; x < pos.x + this.img.getWidth() / 32; ++x)
				{
					if(isMine)
					{
						tiles.get(coordinatesToIndex(new Vector2i(x, y), tilesInRow)).setBlocked();
						tiles.get(coordinatesToIndex(new Vector2i(x, y), tilesInRow)).setMine();
					}
					else
					{	
						if(tiles.get(coordinatesToIndex(new Vector2i(x, y), tilesInRow)).isBlocked()) { continue; }
						else
						{
							tiles.get(coordinatesToIndex(new Vector2i(x, y), tilesInRow)).setBlocked();
						}
					}
				}
			}
		}
		
		public void render(Graphics g)
		{
			g.drawImage(img.getScaledCopy((img.getWidth() / 32) * tileSize, (img.getHeight() / 32) * tileSize), pos.x * tileSize, pos.y * tileSize);
		}
	}
	
	private ArrayList<Tile> tiles;
	
	private ArrayList<ObjectOnMap> objectsOnMap;
	private Image spriteSheetImg;
	private Vector2i imgCout;
	private int tileSize;
	private int tilesInRow;
	private float animationTime;
	private ArrayList<Integer> blueprint;
	
	private FlowField flowField;
	
	private Vector2i minePos;
	
	public Tilemap(Image spriteSheetImg, String blueprintPath, String objsPath,Vector2i imgCout, int tileSize, int width, float animationTime) throws IOException, SlickException
	{
		this.spriteSheetImg = spriteSheetImg;
		this.imgCout = imgCout;
		this.tileSize = tileSize;
		this.animationTime = animationTime;
		
		this.blueprint = getIntDataFromFile(blueprintPath);
		this.tiles = new ArrayList<Tile>();
		
		
		initTileMapFromBlueprint();
		
		this.objectsOnMap = new ArrayList<Tilemap.ObjectOnMap>();
		this.flowField = new FlowField();
		initObjsOnMap(objsPath);
		
		flowField.createIntegrationField(tiles, tiles.get(coordinatesToIndex(minePos, tileSize)), tileSize);
	}
	
	public void update(float dt)
	{
		for(Tile t : tiles)
		{
			t.update(dt);
		}
	}
	
	public void render(Graphics g, boolean debug)
	{
		for(Tile t : tiles)
		{
			t.render(g, debug);
		}
		
		for(ObjectOnMap obj : objectsOnMap)
		{
			obj.render(g);
		}
	}
	
	public void createFlowField(Vector2i destination)
	{
		flowField.createIntegrationField(tiles, tiles.get(coordinatesToIndex(new Vector2i(destination.x/tileSize, destination.y/tileSize), tilesInRow)), tilesInRow);
	}
	
	
	//
	// Tilemap generation
	//
	//	Number codes:
	//		0 	= Water animation
	//		1 	= water
	//		2 	= curve bottom right animation
	//		3 	= curve bottom right
	//		4 	= curve top right animation
	//		5 	= curve top right
	//		6 	= coast left animation
	//		7 	= coast left
	//		8 	= dot top left animation
	//		9 	= dot top left
	//		10 	= dot top right animation
	//		11 	= dot top right
	//		12 	= coast top animation
	//		13 	= coast top
	//		14 	= curve bottom left animation
	//		15 	= curve bottom left
	//		16 	= curve top left animation
	//		17 	= curve top left
	//		18 	= coast right animation
	// 		19 	= coast right
	//		20 	= dot bottom left animation
	//		21 	= dot bottom left
	//		22 	= dot bottom right animation
	//		23 	= dot bottom right
	//		24 	= coast bottom animation
	//		25	= coast bottom
	//		26  = grass
	//		27	= flowers
	
	private void initTileMapFromBlueprint() throws SlickException
	{		
		tilesInRow = blueprint.get(0);
		blueprint.remove(0);
		
		for(int i = 0; i < blueprint.size(); ++i)
		{
			switch(blueprint.get(i))
			{
					// water tile animation = 0
				case 0:
					tiles.add(new Tile(new Animation(imgCout, spriteSheetImg, animationTime, 0, 1), indexToCoordinates(i, tilesInRow), tileSize, true));
					break;
					// water tile animation = 1
				case 1:
					tiles.add(new Tile(new Animation(imgCout, spriteSheetImg, animationTime, 1, 0), indexToCoordinates(i, tilesInRow), tileSize, true));
					break;
					// curve bottom right animation
				case 2:
					tiles.add(new Tile(new Animation(imgCout, spriteSheetImg, animationTime, 2, 1), indexToCoordinates(i, tilesInRow), tileSize, true));
					break;
					// curve bottom right
				case 3:
					tiles.add(new Tile(new Animation(imgCout, spriteSheetImg, animationTime, 3, 0), indexToCoordinates(i, tilesInRow), tileSize, true));
					break;
					// curve top right animation
				case 4:
					tiles.add(new Tile(new Animation(imgCout, spriteSheetImg, animationTime, 4, 1), indexToCoordinates(i, tilesInRow), tileSize, true));
					break;
					// curve top right
				case 5:
					tiles.add(new Tile(new Animation(imgCout, spriteSheetImg, animationTime, 5, 0), indexToCoordinates(i, tilesInRow), tileSize, true));
					break;
					// coast left animation
				case 6:
					tiles.add(new Tile(new Animation(imgCout, spriteSheetImg, animationTime, 6, 1), indexToCoordinates(i, tilesInRow), tileSize, true));
					break;
					// coast left
				case 7:
					tiles.add(new Tile(new Animation(imgCout, spriteSheetImg, animationTime, 7, 0), indexToCoordinates(i, tilesInRow), tileSize, true));
					break;
					// dot top left animation
				case 8:
					tiles.add(new Tile(new Animation(imgCout, spriteSheetImg, animationTime, 8, 1), indexToCoordinates(i, tilesInRow), tileSize, true));
					break;
					// dot top left
				case 9:
					tiles.add(new Tile(new Animation(imgCout, spriteSheetImg, animationTime, 9, 0), indexToCoordinates(i, tilesInRow), tileSize, true));
					break;
					// dot top right animation
				case 10:
					tiles.add(new Tile(new Animation(imgCout, spriteSheetImg, animationTime, 10, 1), indexToCoordinates(i, tilesInRow), tileSize, true));
					break;
					// dot top right
				case 11:
					tiles.add(new Tile(new Animation(imgCout, spriteSheetImg, animationTime, 11, 0), indexToCoordinates(i, tilesInRow), tileSize, true));
					break;
					// coast top animation
				case 12:
					tiles.add(new Tile(new Animation(imgCout, spriteSheetImg, animationTime, 12, 1), indexToCoordinates(i, tilesInRow), tileSize, true));
					break;
					// coast top
				case 13:
					tiles.add(new Tile(new Animation(imgCout, spriteSheetImg, animationTime, 13, 0), indexToCoordinates(i, tilesInRow), tileSize, true));
					break;
					// curve bottom left animation
				case 14:
					tiles.add(new Tile(new Animation(imgCout, spriteSheetImg, animationTime, 14, 1), indexToCoordinates(i, tilesInRow), tileSize, true));
					break;
					// curve bottom left
				case 15:
					tiles.add(new Tile(new Animation(imgCout, spriteSheetImg, animationTime, 15, 0), indexToCoordinates(i, tilesInRow), tileSize, true));
					break;
					// curve top left animation
				case 16:
					tiles.add(new Tile(new Animation(imgCout, spriteSheetImg, animationTime, 16, 1), indexToCoordinates(i, tilesInRow), tileSize, true));
					break;
					// curve top left
				case 17:
					tiles.add(new Tile(new Animation(imgCout, spriteSheetImg, animationTime, 17, 0), indexToCoordinates(i, tilesInRow), tileSize, true));
					break;
					// coast right animation
				case 18:
					tiles.add(new Tile(new Animation(imgCout, spriteSheetImg, animationTime, 18, 1), indexToCoordinates(i, tilesInRow), tileSize, true));
					break;
					// coast right
				case 19:
					tiles.add(new Tile(new Animation(imgCout, spriteSheetImg, animationTime, 19, 0), indexToCoordinates(i, tilesInRow), tileSize, true));
					break;
					// dot bottom left animation
				case 20:
					tiles.add(new Tile(new Animation(imgCout, spriteSheetImg, animationTime, 20, 1), indexToCoordinates(i, tilesInRow), tileSize, true));
					break;
					// dot bottom left
				case 21:
					tiles.add(new Tile(new Animation(imgCout, spriteSheetImg, animationTime, 21, 0), indexToCoordinates(i, tilesInRow), tileSize, true));
					break;
					// dot bottom right animation
				case 22:
					tiles.add(new Tile(new Animation(imgCout, spriteSheetImg, animationTime, 22, 1), indexToCoordinates(i, tilesInRow), tileSize, true));
					break;
					// dot bottom right
				case 23:
					tiles.add(new Tile(new Animation(imgCout, spriteSheetImg, animationTime, 23, 0), indexToCoordinates(i, tilesInRow), tileSize, true));
					break;
					// coast bottom animation
				case 24:
					tiles.add(new Tile(new Animation(imgCout, spriteSheetImg, animationTime, 24, 1), indexToCoordinates(i, tilesInRow), tileSize, true));
					break;
					// coast bottom
				case 25:
					tiles.add(new Tile(new Animation(imgCout, spriteSheetImg, animationTime, 25, 0), indexToCoordinates(i, tilesInRow), tileSize, true));
					break;
					// grass
				case 26:
					tiles.add(new Tile(new Animation(imgCout, spriteSheetImg, animationTime, 26, 0), indexToCoordinates(i, tilesInRow), tileSize, false));
					break;
					// flowers
				case 27:
					tiles.add(new Tile(new Animation(imgCout, spriteSheetImg, animationTime, 27, 0), indexToCoordinates(i, tilesInRow), tileSize, false));
					break;
			}
			
		}
	}
	
	private void initObjsOnMap(String path) throws IOException, SlickException
	{
		ArrayList<Integer> objsData = getIntDataFromFile(path);
		
		while(objsData.size() > 0)
		{
			switch(objsData.get(2))
			{
				// tree
				case 0:
					objectsOnMap.add(new ObjectOnMap(new Vector2i(objsData.get(0), objsData.get(1)), new Image("res/Sprites/World/Objects/tree.png"), false));
					break;
				// stone
				case 1:
					objectsOnMap.add(new ObjectOnMap(new Vector2i(objsData.get(0), objsData.get(1)), new Image("res/Sprites/World/Objects/stone.png"), false));
					break;
				// mine
				case 2:
					objectsOnMap.add(new ObjectOnMap(new Vector2i(objsData.get(0), objsData.get(1)), new Image("res/Sprites/World/Objects/mine.png"), true));
					minePos = new Vector2i(objsData.get(0), objsData.get(1));
					break;
			}
			
			objsData.remove(0);
			objsData.remove(0);
			objsData.remove(0);
		}
	}
	
	public Vector2f getAccelerationFromTile(float x, float y)
	{
		return tiles.get(coordinatesToIndex(new Vector2i((int)x, (int)y), tilesInRow)).getBestDirection();
	}
	
	
	private ArrayList<Integer> getIntDataFromFile(String path) throws IOException
	{
		List<String> data = Files.readAllLines(Paths.get(path));
		ArrayList<Integer> intData = new ArrayList<Integer>();
		
		// format data into single Strings of numbers
		for(String lines : data)
		{
			String[] lineData = lines.split(",");
			
			for(String s : lineData)
			{
				intData.add(Integer.parseInt(s.trim()));
			}
		}
		
		return intData;
	}
	
	//
	// DEBUG
	//
	public void printTileDataAt(int x, int y)
	{
		Tile t = tiles.get(coordinatesToIndex(new Vector2i(x/tilesInRow, y/tilesInRow), tilesInRow));
		System.out.println(t.getBestDirection().x + " " + t.getBestDirection().y);
	}
	//
	//
	//
	
	
	public static Vector2i indexToCoordinates(int index, int tilesInRow)
	{
		if(index >= 0)
		{
			return new Vector2i(index % tilesInRow, index / tilesInRow);
		}
		else
			return null;
	}
	
	public static int coordinatesToIndex(Vector2i coords, int tilesInRow)
	{
		if(coords.x > 0 && coords.x < tilesInRow && coords.y > 0 && coords.y < tilesInRow)
		{
			return coords.x + (coords.y * tilesInRow);
		}
		else
			return 0;
	}
	
}
