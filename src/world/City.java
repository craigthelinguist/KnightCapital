package world;

import java.awt.Graphics;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import player.Player;
import renderer.Animation;
import tools.Log;

/**
 * There are cities on the world. They train units. What units a city can train depends
 * on what buildings the city has. You can construct buildings in a city. On the overworld,
 * a city is represented by a collection of tiles.
 * 
 * Let's assume on the overworld all cities are 3x3.
 * 
 * @author craigthelinguist
 */
public class City {

	// on the overworld, a city is a collection of 3x3 CityTiles.
	public static final int WIDTH = 3;
	private final CityTile[] tiles;
	
	// who owns this city
	private Player owner;
	
	// buildings inside this city
	private Set<Building> buildings;
	
	// image representing this city
	private Animation animation;
	
	public City(Player player, CityTile[] overworldTiles){
		owner = player;
		buildings = new HashSet<>();
		if (overworldTiles.length != WIDTH*WIDTH){
			Log.print("Error! City has " + overworldTiles.length + " tiles but should only have " + WIDTH*WIDTH);
		}
		tiles = overworldTiles;
	}

	/**
	 * Attempt to add the specified building, or do nothing if it is already in this city.
	 * @param b: building to add.
	 * @return: true if the building was added, false otherwise.
	 */
	public boolean addBuilding(Building b){
		return buildings.add(b);
	}
	
	/**
	 * Check if the given player owns this city.
	 * @param player: see if this person owns the city.
	 * @return true if the person owns the city.
	 */
	public boolean ownedBy(Player player){
		return owner == player;
	}
	
	/**
	 * Set the city to be owned by the specified player.
	 * @param player: player who now owns the city.
	 */
	public void setPlayer(Player player){
		owner = player;
	}
	
	/**
	 * Draw this city on the given graphics object at the point (x,y)
	 */
	public void draw(Graphics g, int x, int y){
		g.drawImage(animation.getSprite(),x,y,null);
	}
	
}
