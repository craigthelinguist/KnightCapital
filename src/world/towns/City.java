package world.towns;

import game.items.Item;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import javax.swing.ImageIcon;

import player.Player;
import renderer.Animation;
import renderer.AnimationMap;
import renderer.Camera;
import tools.Constants;
import tools.ImageLoader;
import tools.Log;
import world.icons.Party;
import world.tiles.CityTile;

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
	private Party garrison;

	// party staying in this city
	private Party visitors;

	// items being stored inside this city
	private LinkedList<Item> items;

	// image representing this city
	private AnimationMap animations;

	public City(String name, String filepath, Player player, CityTile[][] overworldTiles){

		// set up owner and fields storing details about this city
		owner = player;
		buildings = new HashSet<>();
		visitors = garrison = null;
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

		// set up images for this city
		animations = new AnimationMap();
		this.animations.addDirectedImages(Constants.CITIES, "basic", "red");
		animations.setImage("north");
		String portrait_filepath = Constants.PORTRAITS + "city_" + filepath;
		animations.addImage("portrait", portrait_filepath, ImageLoader.load(portrait_filepath));
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
		g.drawImage(animations.getImage(), x, y, null);
	}

	public int getImageHeight(){
		return animations.getImage().getHeight();
	}

	public int getImageWidth(){
		return animations.getImage().getWidth();
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

	public void setAnimationName(String name){
		animations.setImage(name);
	}

	public String getAnimationName(){
		return animations.getName();
	}


	public Party getVisitors() {
		return visitors;
	}

	public Party getGarrison(){
		return garrison;
	}


	public void setGarrison(Party party) {
		this.garrison = party;
	}


	public void setVisitors(Party party) {
		this.visitors = party;
	}


	public BufferedImage getPortrait() {
		return animations.getPortrait();
	}

	public Player getOwner() {
		return this.owner;
	}

}
