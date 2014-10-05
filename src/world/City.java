package world;

import game.Item;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import player.Player;
import renderer.Animation;
import renderer.Camera;
import tools.GlobalConstants;
import tools.ImageLoader;
import tools.Log;

/**
 * There are cities on the world. They train units. What units a city can train
 * depends on what buildings the city has. You can construct buildings in a
 * city. On the overworld, a city is represented by a collection of tiles.
 * 
 * Let's assume on the overworld all cities are 3x3.
 * 
 * @author craigthelinguist
 */
public class City {

	// on the overworld, a city is a collection of 3x3 CityTiles.
	public static final int WIDTH = 3;
	private final CityTile[][] tiles;

	// who owns this city
	private Player owner;

	// buildings inside this city
	private Set<Building> buildings;

	// party inside this city
	private Party party;

	// items being stored inside this city
	private LinkedList<Item> items;

	// image representing this city
	private Animation animation;
	private Map<String, Animation> animationNames;

	/**
	 * Create a new City.
	 * 
	 * @param animationNamesArray
	 *            : an array of animation names for this city to use.
	 * @param player
	 *            : the player who owns this city 'Null' means it is neutral.
	 * @param overworldTiles
	 *            : an array of overworldTiles. Assumed that they are physically
	 *            located in the world in a way that makes sense. The first tile
	 *            in overworldTiles must be the topmost.
	 */
	public City(String[] animationNamesArray, Player player,
			CityTile[][] overworldTiles) {

		// set up owner and fields storing details about this city
		owner = player;
		buildings = new HashSet<>();
		party = null;
		items = new LinkedList<>();

		// set up tiles; set tiles so that they have a reference to this.
		tiles = overworldTiles;
		if (overworldTiles.length != WIDTH) Log.print("Error! City must be " + WIDTH + "x" + WIDTH + "tiles!");
		for (int i = 0; i < WIDTH; i++){
			if (overworldTiles[i].length != WIDTH) Log.print("Error! City must be " + WIDTH + "x" + WIDTH + "tiles!");
			for (int j = 0; j < WIDTH; j++){
				tiles[i][j] = overworldTiles[i][j];
				tiles[i][j].setCity(this);
			}
		}
		
		// set up animation names
		animationNames = new HashMap<>();
		for (int i = 0; i < animationNamesArray.length; i++) {
			String name = animationNamesArray[i];
			name = GlobalConstants.CITIES + name;
			Animation anim = ImageLoader.loadAnimation(name);
			if (i == 0)
				animation = anim;
			animationNames.put(name, anim);
		}

	}

	/**
	 * Attempt to add the specified building, or do nothing if it is already in
	 * this city.
	 * 
	 * @param b
	 *            : building to add.
	 * @return: true if the building was added, false otherwise.
	 */
	public boolean addBuilding(Building b) {
		return buildings.add(b);
	}

	/**
	 * Check if the given player owns this city.
	 * 
	 * @param player
	 *            : see if this person owns the city.
	 * @return true if the person owns the city.
	 */
	public boolean ownedBy(Player player) {
		return owner == player;
	}

	/**
	 * Set the city to be owned by the specified player.
	 * 
	 * @param player
	 *            : player who now owns the city.
	 */
	public void setPlayer(Player player) {
		owner = player;
	}

	/**
	 * Draw this city on the given graphics object at the point (x,y)
	 */
	public void draw(Graphics g, int x, int y) {
		g.drawImage(animation.getSprite(), x, y, null);
	}

	public int getImageHeight(){
		return animation.getSprite().getHeight();
	}

	/**
	 * Return the tile that would be visually leftmost from the perspective of the given camera.
	 * @param camera: viewing perspective
	 * @return: the leftmost tile.
	 */
	public CityTile getLeftmostTile(Camera camera){
		int orientation = camera.getOrientation();
		if (orientation == Camera.NORTH) return tiles[0][WIDTH-1];
		else if (orientation == Camera.WEST) return tiles[0][0];
		else if (orientation == Camera.SOUTH) return tiles[WIDTH-1][0];
		else if (orientation == Camera.EAST) return tiles[WIDTH-1][WIDTH-1];
		else throw new RuntimeException("unknown camera orientation: " + orientation);
	}

	public int getImageWidth(){
		return animation.getSprite().getWidth();
	}
	
}
